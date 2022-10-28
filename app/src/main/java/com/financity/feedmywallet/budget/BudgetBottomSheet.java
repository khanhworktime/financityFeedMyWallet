package com.financity.feedmywallet.budget;

import static com.financity.feedmywallet.App.budgets;
import static com.financity.feedmywallet.App.wallet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.financity.feedmywallet.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

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
        TextInputEditText inpBudgetStartDate = view.findViewById(R.id.inpBudgetStartDate);
        TextInputEditText inpBudgetEndDate = view.findViewById(R.id.inpBudgetEndDate);
        TextInputEditText inpBudgetNote = view.findViewById(R.id.inpBudgetNote);

        MaterialToolbar topAppBar = view.findViewById(R.id.topAppBar);

        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.mItemAddBudget) {
//                budgets.add(new Budget(inpBudgetAmount, inpBudgetName.toString(), inpBudgetNote.toString(), false, wallet, Date.parse(inpBudgetStartDate.toString())))
                return true;
            }
            return false;
        });

        return view;
    }
}
