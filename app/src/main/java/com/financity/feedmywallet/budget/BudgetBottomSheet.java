package com.financity.feedmywallet.budget;

import static com.financity.feedmywallet.App.budgets;
import static com.financity.feedmywallet.App.categories;
import static com.financity.feedmywallet.App.wallets;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import com.financity.feedmywallet.utils.DateFormater;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class BudgetBottomSheet extends BottomSheetDialogFragment {


    boolean editMode;
    Budget selectedBudget;
    public BudgetBottomSheet() {
        editMode = false;
    }

    public BudgetBottomSheet(Budget selectedBudget, boolean editMode) {
        this.selectedBudget = selectedBudget;
        this.editMode = editMode;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void hideBottomSheet(){
        this.dismiss();
    }

    @SuppressLint("NonConstantResourceId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_add_new_budget, container, false);

        TextInputEditText inpBudgetName = view.findViewById(R.id.inpBudgetName);
        TextInputEditText inpBudgetAmount = view.findViewById(R.id.inpBudgetAmount);
        TextInputEditText inpBudgetStartDate = view.findViewById(R.id.inpBudgetStartDate);
        TextInputEditText inpBudgetEndDate = view.findViewById(R.id.inpBudgetEndDate);
        TextInputEditText inpBudgetNote = view.findViewById(R.id.inpBudgetNote);
        AutoCompleteTextView inpWallet = view.findViewById(R.id.inpBudgetWallet);
        AutoCompleteTextView inpCategory = view.findViewById(R.id.inpBudgetCategory);
        MaterialToolbar topAppBar = view.findViewById(R.id.topAppBar);

        Budget budgetEdit = new Budget();
        budgetEdit.setId(UUID.randomUUID().toString());
        if (editMode){
            budgetEdit = selectedBudget;
            inpBudgetName.setText(budgetEdit.getName());
            inpBudgetAmount.setText(String.format(Locale.getDefault(), "%.2f", budgetEdit.getAmount()));
            inpBudgetStartDate.setText(budgetEdit.getStartDate());
            inpBudgetEndDate.setText(budgetEdit.getEndDate());
            inpBudgetNote.setText(budgetEdit.getNote());
            inpWallet.setText(budgetEdit.getWallet().getName());
            inpCategory.setText(budgetEdit.getCategory().getValue());
        }

        Budget finalBudgetEdit = budgetEdit;

        if (!editMode){
            topAppBar.inflateMenu(R.menu.add_new_menu);
        } else {
            topAppBar.inflateMenu(R.menu.edit_menu);
        }

        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.mItemAdd || item.getItemId() == R.id.mSave){
                try {
                    if (inpBudgetName.getText().length() == 0) {
                        throw new RuntimeException("Chưa nhập tên Budget");
                    }
                    if (inpBudgetAmount.getText().length() == 0) {
                        throw new RuntimeException("Chưa nhập số tiền cho Budget");
                    }
                    if (inpWallet.getText().length() == 0) {
                        throw new RuntimeException("Chưa chọn ví");
                    }
                    if (inpCategory.getText().length() == 0) {
                        throw new RuntimeException("Chưa chọn category");
                    }
                    if (inpBudgetStartDate.getText().length() == 0) {
                        throw new RuntimeException("Chưa nhập ngày bắt đầu");
                    }
                    if (inpBudgetEndDate.getText().length() == 0) {
                        throw new RuntimeException("Chưa nhập ngày kết thúc");
                    }

                    Calendar startDate = Calendar.getInstance();
                    startDate.setTime(DateFormater.dateOnlyFormater.parse(String.valueOf(inpBudgetStartDate.getText())));
                    Calendar endDate = Calendar.getInstance();
                    endDate.setTime(DateFormater.dateOnlyFormater.parse(String.valueOf(inpBudgetEndDate.getText())));

                    if (startDate.getTimeInMillis() >= endDate.getTimeInMillis()){
                        throw new RuntimeException("Ngày kết thúc phải sau ngày bắt đầu");
                    }
                    finalBudgetEdit.setAmount(
                            Float.parseFloat(String.valueOf(inpBudgetAmount.getText()))
                    );
                    finalBudgetEdit.setName(String.valueOf(inpBudgetName.getText()));
                    finalBudgetEdit.setStartDate(String.valueOf(inpBudgetStartDate.getText()));
                    finalBudgetEdit.setEndDate(String.valueOf(inpBudgetEndDate.getText()));
                    finalBudgetEdit.setNote(String.valueOf(inpBudgetNote.getText()));

                    if (item.getItemId() == R.id.mItemAdd) finalBudgetEdit.setState("init");
                    Date startDateD = DateFormater.dateOnlyFormater.parse(finalBudgetEdit.getStartDate());
                    Date endDateD = DateFormater.dateOnlyFormater.parse(finalBudgetEdit.getEndDate());

                    if(startDateD.getTime() <= new Date().getTime()){
                        finalBudgetEdit.setState("running");
                    }
                    if(endDateD.getTime() <= new Date().getTime()){
                        finalBudgetEdit.setState("finished");
                    }
                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("users_data")
                            .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                            .child("budgets").child(finalBudgetEdit.getId());

                    mRef.setValue(finalBudgetEdit);
                    dismiss();
                    } catch (RuntimeException e) {
                    Snackbar.make(view ,e.getMessage(), Snackbar.LENGTH_SHORT).setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show();
                } catch (ParseException e) {
                        e.printStackTrace();
                    }
                return item.getItemId() == R.id.mItemAdd;
            }
            if (item.getItemId() == R.id.mDelete){
                MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Xác nhận xóa Budget ?")
                        .setMessage("Xóa luôn á nha!")
                        .setNeutralButton("Không xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("users_data")
                                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                        .child("budgets").child(finalBudgetEdit.getId());

                                mRef.removeValue();
                                dismiss();
                            }
                        });
                dialogBuilder.show();

                return item.getItemId() == R.id.mItemAdd;
            }
            return false;
        });

        inpBudgetStartDate.setOnClickListener(v -> {
            MaterialDatePicker.Builder<Long> datePickerBuilder = MaterialDatePicker.Builder.datePicker();
            MaterialDatePicker<Long> datePicker = datePickerBuilder
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .setPositiveButtonText("OK")
                    .setNegativeButtonText("Hủy")
                    .build();

            datePicker.addOnPositiveButtonClickListener(selection -> inpBudgetStartDate.setText(datePicker.getHeaderText()));

            datePicker.show(getChildFragmentManager(), "DatePickerForStartDate");
        });

        inpBudgetEndDate.setOnClickListener(v -> {
            MaterialDatePicker.Builder<Long> datePickerBuilder = MaterialDatePicker.Builder.datePicker();
            MaterialDatePicker<Long> datePicker = datePickerBuilder.setTitleText("Ngày kết thúc")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .setPositiveButtonText("OK")
                    .setNegativeButtonText("Hủy")
                    .build();

            datePicker.addOnPositiveButtonClickListener(selection -> inpBudgetEndDate.setText(datePicker.getHeaderText()));

            datePicker.show(getChildFragmentManager(), "DatePickerForStartDate");
        });

        String[] walletNames = new String[wallets.size()];
        String[] outcomeNames = new String[App.categories.getOutcomeCategories().size()];

        for (int i = 0 ; i < wallets.size() ; i++) {
            walletNames[i] = wallets.get(i).getName();
        }
        for (int i = 0 ; i < outcomeNames.length ; i++) {
            outcomeNames[i] = App.categories.getOutcomeCategories().get(i).getValue();
        }

        Budget finalBudgetEdit1 = budgetEdit;

        ArrayAdapter<String> walletsAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item, walletNames);
        inpWallet.setAdapter(walletsAdapter);
        inpWallet.setOnItemClickListener((parent, view1, position, id) -> finalBudgetEdit1.setWallet(wallets.get(position)));

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item, outcomeNames);
        inpCategory.setAdapter(categoriesAdapter);
        inpCategory.setOnItemClickListener((parent, view1, position, id) -> finalBudgetEdit1.setCategory(categories.getOutcomeCategories().get(position)));

        return view;
    }
}
