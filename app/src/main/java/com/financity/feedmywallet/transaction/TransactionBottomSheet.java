package com.financity.feedmywallet.transaction;

import android.os.Bundle;
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
import com.financity.feedmywallet.utils.DateFormater;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionBottomSheet extends BottomSheetDialogFragment {



    public TransactionBottomSheet() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextInputEditText inpTransName, inpTransAmount, inpTransDate, inpTransNote;
        AutoCompleteTextView inpTransWallet, inpTransType, inpTransCategory;
        View view = inflater.inflate(R.layout.bottom_sheet_add_new_transaction, container, false);

        Transaction transactionEdit = new Transaction();

//        Init UI
        inpTransName = view.findViewById(R.id.inpTransName);
        inpTransAmount = view.findViewById(R.id.inpTransAmount);
        inpTransDate = view.findViewById(R.id.inpTransDate);
        inpTransNote = view.findViewById(R.id.inpTransNote);
        inpTransWallet = view.findViewById(R.id.inpTransWallet);
        inpTransType = view.findViewById(R.id.inpTransType);
        inpTransCategory = view.findViewById(R.id.inpTransCategory);

        inpTransDate.setText(DateFormater.defaultFormater.format(new Date()));

//        ArrayAdapter
        ArrayAdapter<String> TransTypeAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.list_item, Transaction.TRANSACTION_TYPE);
        ArrayAdapter<String> TransCategoryAdapter;
        ArrayList<String> transCategoryList = new ArrayList<String>(Arrays.asList(Category.CATEGORIES_INCOME));
        TransCategoryAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.list_item, transCategoryList);

        inpTransType.setAdapter(TransTypeAdapter);
        TransTypeAdapter.notifyDataSetChanged();
        inpTransType.setOnItemClickListener((parent, view1, position, id) -> {
            transactionEdit.setType(TransTypeAdapter.getItem(position));
            if (Objects.equals(transactionEdit.type, Transaction.TRANSACTION_TYPE_INCOME))
            {
                transCategoryList.clear();
                transCategoryList.addAll(Arrays.asList(Category.CATEGORIES_INCOME));
            }
            if (Objects.equals(transactionEdit.type, Transaction.TRANSACTION_TYPE_OUTCOME))
            {
                transCategoryList.clear();
                transCategoryList.addAll(Arrays.asList(Category.CATEGORIES_OUTCOME));
            }

        });

        inpTransCategory.setAdapter(TransCategoryAdapter);
        inpTransCategory.setOnItemClickListener((parent, view1, position, id) -> {
            transactionEdit.setCategory(new Category(TransCategoryAdapter.getItem(position), inpTransType.getText().toString()));
        });

        String[] walletNames = new String[App.wallets.size()];
        for(int i=0; i<walletNames.length; i++) {
            walletNames[i] = App.wallets.get(i).getName();
        }

        AtomicInteger walletIndex = new AtomicInteger();
        ArrayAdapter<String> TransWalletAdapter = new ArrayAdapter<>(this.getContext(), R.layout.list_item, walletNames);
        inpTransWallet.setAdapter(TransWalletAdapter);
        inpTransWallet.setOnItemClickListener((parent, view1, position, id)->{
            transactionEdit.setWallet(App.wallets.get(position));
            walletIndex.set(position);
        });

        inpTransDate.setOnClickListener(v -> {
            MaterialDatePicker.Builder<Long> datePickerBuilder = MaterialDatePicker.Builder.datePicker();
            MaterialDatePicker<Long> datePicker = datePickerBuilder.setTitleText("Ngày giao dịch")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .setPositiveButtonText("OK")
                    .setNegativeButtonText("Hủy")
                    .build();

            datePicker.addOnPositiveButtonClickListener(selection -> inpTransDate.setText(datePicker.getHeaderText()));

            datePicker.show(getChildFragmentManager(), "DatePickerForStartDate");
        });

//        Submit
        MaterialToolbar topAppBar = view.findViewById(R.id.topAppBar);
        topAppBar.setOnMenuItemClickListener(item -> {
            transactionEdit.setAmount(Float.parseFloat(Objects.requireNonNull(inpTransAmount.getText()).toString()));
            transactionEdit.setNote(String.valueOf(inpTransNote.getText()));
            transactionEdit.setName(String.valueOf(inpTransName.getText()));
            transactionEdit.setDate(String.valueOf(inpTransDate.getText()));

            App.transactions.add(transactionEdit);
            DatabaseReference setTrans = FirebaseDatabase.getInstance().getReference("users_data")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("transactions").child(String.valueOf(App.transactions.size()));

            setTrans.setValue(transactionEdit);
            DatabaseReference setWallet = FirebaseDatabase.getInstance().getReference("users_data")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("wallets").child(String.valueOf(walletIndex.get())).child("balance");
            Float balance = transactionEdit.getWallet().getBalance();

            balance = (transactionEdit.getType().equals(Transaction.TRANSACTION_TYPE_INCOME)) ?
                    balance + transactionEdit.getAmount() : balance - transactionEdit.getAmount();

            setWallet.setValue(balance);
            dismiss();
            return item.getItemId() == R.id.mItemAdd;
        });
        return view;
    }



}
