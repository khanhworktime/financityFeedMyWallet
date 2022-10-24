package com.financity.feedmywallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
1

public class Homepage extends AppCompatActivity {

    public static ArrayList<Wallet> wallets = new ArrayList<Wallet>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


    }
}