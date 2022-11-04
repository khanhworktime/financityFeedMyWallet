package com.financity.feedmywallet.budget;

import static com.financity.feedmywallet.App.budgets;
import static com.financity.feedmywallet.App.wallets;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.financity.feedmywallet.R;
import com.financity.feedmywallet.fragment.BudgetFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BudgetBottomSheet extends BottomSheetDialogFragment {

    UpdateData updateData;

    int selectedPosition;
    public BudgetBottomSheet(UpdateData updateData) {
        this.updateData = updateData;
        selectedPosition = -1;
    }

    public BudgetBottomSheet(int position, UpdateData updateData) {
        selectedPosition = position;
        this.updateData = updateData;
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
        AutoCompleteTextView inpWallet = view.findViewById(R.id.inpWallet);
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
        }

        Budget finalBudgetEdit = budgetEdit;
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
                if (dateFormat.parse(String.valueOf(now)) == dateFormat.parse(finalBudgetEdit.getStartDate())) {
                    finalBudgetEdit.setStarted(true);
                }
                else finalBudgetEdit.setStarted(false);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            budgets.add(finalBudgetEdit);
            updateData.setUpdateData();
            dismiss();
            return item.getItemId() == R.id.mItemAddBudget;
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

        for (int i = 0 ; i < wallets.size() ; i++) {
            walletNames[i] = wallets.get(i).getName();
        }

        ArrayAdapter<String> walletsAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item, walletNames);
        inpWallet.setAdapter(walletsAdapter);
        Budget finalBudgetEdit1 = budgetEdit;
        inpWallet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                finalBudgetEdit1.setWallet(wallets.get(position));
            }
        });

        return view;
    }

    public interface UpdateData{
        void setUpdateData();
    }
}
