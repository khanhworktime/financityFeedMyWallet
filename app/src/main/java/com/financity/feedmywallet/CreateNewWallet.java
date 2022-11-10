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

import com.financity.feedmywallet.wallet.Wallet;
import com.google.android.material.textfield.TextInputEditText;

public class CreateNewWallet extends AppCompatActivity implements AdapterView.OnItemClickListener {

    final String[] currencyUnits = {"Vietnamese Dong ₫", "US Dollar $", "Euro €", "British Pound £", "Japanese Yen ¥", "Chinese Yuan Renminbi ¥", "South Korean Won ₩"};
    final String[] currencyLables = {"₫", "$", "€", "£", "¥", "¥", "₩"};
    Button btn_create;
    AutoCompleteTextView currencyUnit;
    TextInputEditText txName, txInitBalance;
    final Wallet newWallet = new Wallet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_wallet);

        currencyUnit = findViewById(R.id.txCurrencyUnit);
        txName = findViewById(R.id.txName);
        txInitBalance = findViewById(R.id.txInitBalance);

        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(this,R.layout.list_item, currencyUnits);
        currencyUnit.setAdapter(currencyAdapter);
        currencyUnit.setOnItemClickListener(this);

        btn_create = findViewById(R.id.btn_create);


        btn_create.setOnClickListener(view -> {

            newWallet.setName(txName.getText().toString());
            newWallet.setBalance(Integer.parseInt(txInitBalance.getText().toString()));

            wallets.add(newWallet);

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