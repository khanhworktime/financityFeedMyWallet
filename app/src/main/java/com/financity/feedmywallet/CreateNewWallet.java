package com.financity.feedmywallet;


import static com.financity.feedmywallet.Homepage.wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class CreateNewWallet extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] currencyUnits = {"Vietnamese Dong ₫", "US Dollar $", "Euro €", "British Pound £", "Japanese Yen ¥", "Chinese Yuan Renminbi ¥", "South Korean Won ₩"};
    Button btn_create;
    AutoCompleteTextView currencyUnit;
    TextInputEditText txName, txInitBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_wallet);

        currencyUnit = findViewById(R.id.txCurrencyUnit);
        txName = findViewById(R.id.txName);
        txInitBalance = findViewById(R.id.txInitBalance);

        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(this,R.layout.list_item, currencyUnits);
        currencyUnit.setAdapter(currencyAdapter);

        btn_create = findViewById(R.id.btn_create);

        btn_create.setOnClickListener(view -> {
            wallet.setName(txName.getText().toString());
            wallet.setBalance(Integer.parseInt(txInitBalance.getText().toString()));
            Intent i = new Intent(getApplicationContext(), Homepage.class);
            startActivity(i);
            finish();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}