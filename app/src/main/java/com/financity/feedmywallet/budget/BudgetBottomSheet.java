package com.financity.feedmywallet.budget;

import static com.financity.feedmywallet.App.budgets;
import static com.financity.feedmywallet.App.categories;
import static com.financity.feedmywallet.App.wallets;

import android.annotation.SuppressLint;
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
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class BudgetBottomSheet extends BottomSheetDialogFragment {


    final int selectedPosition;
    public BudgetBottomSheet() {
        selectedPosition = -1;
    }

    public BudgetBottomSheet(int position) {
        selectedPosition = position;
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
        if (selectedPosition != -1){
            budgetEdit = budgets.get(selectedPosition);
            inpBudgetName.setText(budgetEdit.getName());
            inpBudgetAmount.setText(String.valueOf(budgetEdit.getAmount()));
            inpBudgetStartDate.setText(budgetEdit.getStartDate());
            inpBudgetEndDate.setText(budgetEdit.getEndDate());
            inpBudgetNote.setText(budgetEdit.getNote());
            inpWallet.setText(budgetEdit.getWallet().getName());
            inpCategory.setText(budgetEdit.getCategory().getValue());
        }

        Budget finalBudgetEdit = budgetEdit;
        Budget finalBudgetEdit2 = budgetEdit;
        topAppBar.setOnMenuItemClickListener(item -> {
            finalBudgetEdit.setAmount(
                    Integer.parseInt(String.valueOf(inpBudgetAmount.getText()))
            );
            finalBudgetEdit.setName(String.valueOf(inpBudgetName.getText()));
            finalBudgetEdit.setStartDate(String.valueOf(inpBudgetStartDate.getText()));
            finalBudgetEdit.setEndDate(String.valueOf(inpBudgetEndDate.getText()));
            finalBudgetEdit.setNote(String.valueOf(inpBudgetNote.getText()));

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            Date now = new Date();
            try {
                finalBudgetEdit.setStarted(dateFormat.parse(String.valueOf(now)) == dateFormat.parse(finalBudgetEdit.getStartDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("users_data")
                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                    .child("budgets").child(String.valueOf(budgets.size()));

            mRef.setValue(finalBudgetEdit2);
            dismiss();
            return item.getItemId() == R.id.mItemAdd;
        });

        inpBudgetStartDate.setOnClickListener(v -> {
            MaterialDatePicker.Builder<Long> datePickerBuilder = MaterialDatePicker.Builder.datePicker();
            MaterialDatePicker<Long> datePicker = datePickerBuilder.setTitleText("Ngày bắt đầu")
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
