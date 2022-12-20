package com.financity.feedmywallet.transaction;

import static android.content.ContentValues.TAG;

import static com.financity.feedmywallet.App.budgets;
import static com.financity.feedmywallet.App.startedBudget;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.financity.feedmywallet.App;
import com.financity.feedmywallet.R;
import com.financity.feedmywallet.category.Category;
import com.financity.feedmywallet.category.IncomeCategory;
import com.financity.feedmywallet.utils.DateFormater;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionBottomSheet extends BottomSheetDialogFragment {
    Transaction transactionEdit;
    boolean isCreate;
    public TransactionBottomSheet() {
        isCreate = true;
    }

    public TransactionBottomSheet(Transaction transaction){
        this.transactionEdit = transaction;
        isCreate = false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    TextInputEditText inpTransName, inpTransAmount, inpTransDate, inpTransNote;
    AutoCompleteTextView inpTransWallet, inpTransType, inpTransCategory;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_add_new_transaction, container, false);

//        Init UI
        inpTransName = view.findViewById(R.id.inpTransName);
        inpTransAmount = view.findViewById(R.id.inpTransAmount);
        inpTransDate = view.findViewById(R.id.inpTransDate);
        inpTransNote = view.findViewById(R.id.inpTransNote);
        inpTransWallet = view.findViewById(R.id.inpTransWallet);
        inpTransType = view.findViewById(R.id.inpTransType);
        inpTransCategory = view.findViewById(R.id.inpTransCategory);

        inpTransDate.setText(DateFormater.defaultFormater.format(new Date()));
        inpTransType.setText(Category.CATEGORIES_TYPE[0]);
        inpTransCategory.setText(Category.CATEGORIES_INCOME[3]);

        AtomicInteger walletIndex = new AtomicInteger();
        String[] walletNames =  new String[App.wallets.size()];
        for (int i = 0; i < walletNames.length; i++) {
            walletNames[i] = App.wallets.get(i).getName();
        }

        ArrayAdapter<String> TransWalletAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.list_item, walletNames);
        inpTransWallet.setAdapter(TransWalletAdapter);
        inpTransWallet.setOnItemClickListener((parent, view1, position, id) -> {
            transactionEdit.setWallet(App.wallets.get(position));
            walletIndex.set(position);
        });

        inpTransWallet.setText(App.wallets.get(0).getName());

        MaterialToolbar topAppBar = view.findViewById(R.id.topAppBar);
        if (isCreate) {
            transactionEdit = new Transaction();
            transactionEdit.setType(Category.CATEGORIES_TYPE[0]);
            transactionEdit.setCategory(new Category("Extra income" , "Income"));
            transactionEdit.setWallet(App.wallets.get(0));

            inpTransCategory.setText(transactionEdit.getType());
            transactionEdit.setId(UUID.randomUUID().toString());

//            Add new transaction
            topAppBar.inflateMenu(R.menu.add_new_menu);
            topAppBar.setOnMenuItemClickListener(item -> {
                if (inpTransAmount.getText().toString().isEmpty()) {
                    Snackbar.make(view, "Chưa nhập số tiền !", Snackbar.LENGTH_SHORT).setAnchorView(container).show();
                    return false;
                }

                if (inpTransType.getText().toString().isEmpty()) {
                    Snackbar.make(view, "Chưa chọn loại giao dịch !", Snackbar.LENGTH_SHORT).setAnchorView(container).show();
                    return false;
                }
                transactionEdit.setAmount(Float.parseFloat(Objects.requireNonNull(inpTransAmount.getText()).toString()));
                transactionEdit.setNote(String.valueOf(inpTransNote.getText()));
                transactionEdit.setName(String.valueOf(inpTransName.getText()));
                transactionEdit.setDate(String.valueOf(inpTransDate.getText()));

                DatabaseReference setTrans = FirebaseDatabase.getInstance().getReference("users_data")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("transactions").child(transactionEdit.getId());

                setTrans.setValue(transactionEdit);
                DatabaseReference setWallet = FirebaseDatabase.getInstance().getReference("users_data")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("wallets").child(String.valueOf(transactionEdit.getWallet().getId())).child("balance");
                Float balance = transactionEdit.getWallet().getBalance();

                balance = (transactionEdit.getType().equals(Transaction.TRANSACTION_TYPE_INCOME)) ?
                        balance + transactionEdit.getAmount() : balance - transactionEdit.getAmount();

                setWallet.setValue(balance);

//                Check if budget is activated
                startedBudget.forEach(budget -> {
                    if (budget.getWallet().getId().equals(transactionEdit.getId()) && budget.getCategory().getValue().equals(transactionEdit.getCategory().getValue())){
                        Snackbar.make(view, "Ping", Snackbar.LENGTH_SHORT).show();
                        DatabaseReference updateBudget = FirebaseDatabase.getInstance().getReference("users_data")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("budgets").child(budget.getId()).child("used");
                        updateBudget.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Float used = snapshot.getValue(Float.class);
                                if (used <= 0F) used = 0F;
                                used += transactionEdit.getAmount();
                                snapshot.getRef().setValue(used);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });

                dismiss();
                return item.getItemId() == R.id.mItemAdd;
            });
        } else {
            inpTransName.setText(transactionEdit.getName());
            inpTransAmount.setText(String.format(Locale.getDefault(), "%.2f", transactionEdit.getAmount()));
            inpTransDate.setText(transactionEdit.getDate());
            inpTransNote.setText(transactionEdit.getNote());
            inpTransWallet.setText(transactionEdit.getWallet().getName());
            inpTransType.setText(transactionEdit.getType());
            inpTransCategory.setText(transactionEdit.getCategory().getValue());

//            Edit a transaction

            if(transactionEdit.getId().equals("0")) topAppBar.inflateMenu(R.menu.check_menu);
            else topAppBar.inflateMenu(R.menu.edit_menu);
            topAppBar.setOnMenuItemClickListener(item -> {

                if(item.getItemId() == R.id.mSave){
                    if (inpTransAmount.getText().toString().isEmpty()) {
                        Snackbar.make(view, "Chưa nhập số tiền !", Snackbar.LENGTH_SHORT).setAnchorView(container).show();
                        return false;
                    }
                    transactionEdit.setAmount(Float.parseFloat(Objects.requireNonNull(inpTransAmount.getText()).toString()));
                    transactionEdit.setNote(String.valueOf(inpTransNote.getText()));
                    transactionEdit.setName(String.valueOf(inpTransName.getText()));
                    transactionEdit.setDate(String.valueOf(inpTransDate.getText()));

                    DatabaseReference setTrans = FirebaseDatabase.getInstance().getReference("users_data")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("transactions").child(transactionEdit.getId());

                    setTrans.setValue(transactionEdit);
                    DatabaseReference setWallet = FirebaseDatabase.getInstance().getReference("users_data")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("wallets").child(String.valueOf(transactionEdit.getWallet().getId())).child("balance");
                    Float balance = transactionEdit.getWallet().getBalance();

                    balance = (transactionEdit.getType().equals(Transaction.TRANSACTION_TYPE_INCOME)) ?
                            balance + transactionEdit.getAmount() : balance - transactionEdit.getAmount();

                    setWallet.setValue(balance);
                    dismiss();
                    return item.getItemId() == R.id.mSave;
                }
                if(item.getItemId() == R.id.mDelete){
                    transactionEdit.setAmount(Float.parseFloat(Objects.requireNonNull(inpTransAmount.getText()).toString()));
                    transactionEdit.setNote(String.valueOf(inpTransNote.getText()));
                    transactionEdit.setName(String.valueOf(inpTransName.getText()));
                    transactionEdit.setDate(String.valueOf(inpTransDate.getText()));

                    DatabaseReference setTrans = FirebaseDatabase.getInstance().getReference("users_data")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("transactions").child(transactionEdit.getId());

                    DatabaseReference setWallet = FirebaseDatabase.getInstance().getReference("users_data")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("wallets").child(String.valueOf(App.wallets.get(walletIndex.get()).getId())).child("balance");
                    Float balance = transactionEdit.getWallet().getBalance();

                    balance = (transactionEdit.getType().equals(Transaction.TRANSACTION_TYPE_INCOME)) ?
                            balance - transactionEdit.getAmount() : balance + transactionEdit.getAmount();

                    setWallet.setValue(balance);
                    setTrans.removeValue();

                    dismiss();
                    return item.getItemId() == R.id.mDelete;
                }
                return false;
            });

        }

//        ArrayAdapter
        ArrayAdapter<String> TransTypeAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.list_item, Transaction.TRANSACTION_TYPE);

        ArrayList<String> transCategoryList = new ArrayList<String>(Arrays.asList(Category.CATEGORIES_INCOME));
        inpTransCategory.setAdapter(new ArrayAdapter<String>(this.getContext(), R.layout.list_item, transCategoryList));
        inpTransType.setAdapter(TransTypeAdapter);
        TransTypeAdapter.notifyDataSetChanged();
        inpTransType.setOnItemClickListener((parent, view1, position, id) -> {
            transactionEdit.setType(TransTypeAdapter.getItem(position));
            if (Objects.equals(transactionEdit.type, Transaction.TRANSACTION_TYPE_INCOME)) {
                transCategoryList.clear();
                Collections.addAll(transCategoryList, Category.CATEGORIES_INCOME);
                inpTransCategory.setAdapter(new ArrayAdapter<String>(this.getContext(), R.layout.list_item, transCategoryList));
                inpTransCategory.setText("");
            }
            if (Objects.equals(transactionEdit.type, Transaction.TRANSACTION_TYPE_OUTCOME)) {
                transCategoryList.clear();
                transCategoryList.addAll(Arrays.asList(Category.CATEGORIES_OUTCOME));
                inpTransCategory.setAdapter(new ArrayAdapter<String>(this.getContext(), R.layout.list_item, transCategoryList));
                inpTransCategory.setText("");
            }
        });

        inpTransCategory.setText(transactionEdit.getCategory().getValue());

        inpTransCategory.setOnItemClickListener((parent, view1, position, id) -> {
            transactionEdit.setCategory(new Category(inpTransCategory.getAdapter().getItem(position).toString(), inpTransType.getText().toString()));
        });
        inpTransDate.setOnClickListener(v -> {
            MaterialDatePicker.Builder<Long> datePickerBuilder = MaterialDatePicker.Builder.datePicker();
            MaterialDatePicker<Long> datePicker = datePickerBuilder.setTitleText("Ngày giao dịch")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .setPositiveButtonText("OK")
                    .setNegativeButtonText("Hủy")
                    .build();

            datePicker.addOnPositiveButtonClickListener(selection -> {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.setTimeInMillis(datePicker.getSelection());
                MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                        .setMinute(calendar.get(Calendar.MINUTE))
                        .setTitleText("Chọn giờ giao dịch")
                        .build();

                timePicker.show(getChildFragmentManager(), "TimePickerForStartDate");

                timePicker.addOnPositiveButtonClickListener(timeSelector -> {

                    calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                    calendar.set(Calendar.MINUTE, timePicker.getMinute());
                    inpTransDate.setText(DateFormater.defaultFormater.format(calendar.getTime()));
                });
            });

            datePicker.show(getChildFragmentManager(), "DatePickerForStartDate");
        });

        return view;
    }
}
