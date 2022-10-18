package com.financity.feedmywallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateNewWallet extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String currencyUnits[] = {"Vietnamese Dong ₫", "US Dollar $", "Euro €", "British Pound £", "Japanese Yen ¥", "Chinese Yuan Renminbi ¥", "South Korean Won ₩"};
    Button btn_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createnewwallet);

        Spinner spin = findViewById(R.id.spin);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter cr = new ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, currencyUnits);
        cr.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);

        spin.setAdapter(cr);

        btn_create = findViewById(R.id.btn_create);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Homepage.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(), currencyUnits[i], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}