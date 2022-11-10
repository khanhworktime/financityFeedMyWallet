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
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

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

        ArrayAdapter<String> TransWalletAdapter = new ArrayAdapter<>(this.getContext(), R.layout.list_item, walletNames);
        inpTransWallet.setAdapter(TransWalletAdapter);

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
            dismiss();
            return true;
        });
        return view;
    }



}
