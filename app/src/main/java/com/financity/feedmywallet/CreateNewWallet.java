package com.financity.feedmywallet;

import static com.financity.feedmywallet.App.wallets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.financity.feedmywallet.category.Category;
import com.financity.feedmywallet.transaction.Transaction;
import com.financity.feedmywallet.utils.DateFormater;
import com.financity.feedmywallet.wallet.Wallet;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.UUID;

public class CreateNewWallet extends AppCompatActivity implements AdapterView.OnItemClickListener {

    final String[] currencyUnits = {"Vietnamese Dong ₫", "US Dollar $", "Euro €", "British Pound £", "Japanese Yen ¥", "Chinese Yuan Renminbi ¥", "South Korean Won ₩"};
    final String[] currencyLables = {"VND", "USD", "EUR", "GBP", "YEN", "CNY", "WON"};
    Button btn_create;
    AutoCompleteTextView currencyUnit;
    TextInputEditText txName, txInitBalance;
    final Wallet newWallet = new Wallet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_wallet);

        currencyUnit = findViewById(R.id.txWalletFilter);
        txName = findViewById(R.id.txName);
        txInitBalance = findViewById(R.id.txInitBalance);

        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(this,R.layout.list_item, currencyUnits);
        currencyUnit.setAdapter(currencyAdapter);
        currencyUnit.setOnItemClickListener(this);

        btn_create = findViewById(R.id.btn_create);


        btn_create.setOnClickListener(view -> {

            newWallet.setName(txName.getText().toString());
            newWallet.setBalance(Float.parseFloat(txInitBalance.getText().toString()));

            DatabaseReference setWallet = FirebaseDatabase.getInstance().getReference("users_data")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("wallets").child(String.valueOf(wallets.size()));
            setWallet.setValue(newWallet);

            DatabaseReference setFirstTrans = FirebaseDatabase.getInstance().getReference("users_data")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("transactions").child(String.valueOf(App.transactions.size()));

            setFirstTrans.setValue(new Transaction("Khởi tạo ví", Transaction.TRANSACTION_TYPE_INCOME, Category.CATEGORY_INITWALLET, newWallet.getBalance(), newWallet, DateFormater.defaultFormater.format(new Date()), "Khởi tạo ví", UUID.randomUUID().toString()));

            Intent i = new Intent(getApplicationContext(), App.class);
            startActivity(i);
            finish();
        });
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        // fetch the user selected value
        String item = currencyLables[position];
        newWallet.setCurrency(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}