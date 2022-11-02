package com.financity.feedmywallet.budget;

import static com.financity.feedmywallet.App.budgets;
import static com.financity.feedmywallet.App.wallet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.financity.feedmywallet.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

public class BudgetBottomSheet extends BottomSheetDialogFragment {


    public BudgetBottomSheet() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NonConstantResourceId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_add_new_budget, container, false);

        TextInputEditText inpBudgetName = view.findViewById(R.id.inpBudgetName);
        TextInputEditText inpBudgetAmount = view.findViewById(R.id.inpBudgetAmount);
        TextInputLayout inpBudgetStartDateBox = view.findViewById(R.id.inpBudgetStartDateBox);
        TextInputLayout inpBudgetEndDateBox = view.findViewById(R.id.inpBudgetEndDateBox);
        TextInputEditText inpBudgetNote = view.findViewById(R.id.inpBudgetNote);

        MaterialToolbar topAppBar = view.findViewById(R.id.topAppBar);

//        Top bar add button
        topAppBar.setOnMenuItemClickListener(item -> {
            //                budgets.add(new Budget(inpBudgetAmount, inpBudgetName.toString(), inpBudgetNote.toString(), false, wallet, Date.parse(inpBudgetStartDate.toString())))
            return item.getItemId() == R.id.mItemAddBudget;
        });

//        inpBudgetStartDate and inpBudgetEndDate handlers

        inpBudgetStartDateBox.setOnClickListener(v -> {
            MaterialDatePicker.Builder<Long> datePickerBuilder = MaterialDatePicker.Builder.datePicker();
            MaterialDatePicker<Long> datePicker = datePickerBuilder.setTitleText("Ngày bắt đầu")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .setPositiveButtonText("OK")
                    .setNegativeButtonText("Hủy")
                    .build();

            datePicker.show(getParentFragmentManager(), "DatePickerForStartDate");
        });

        return view;
    }
}
