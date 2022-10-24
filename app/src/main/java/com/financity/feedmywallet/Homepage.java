package com.financity.feedmywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.financity.feedmywallet.WalletCard.Wallet;
import com.financity.feedmywallet.WalletCard.WalletAdapter;
import com.google.android.material.navigation.NavigationBarView;

public class Homepage extends AppCompatActivity {

    public static Wallet wallet;
    RecyclerView cardWallet;
    WalletAdapter walletAdapter;
    NavigationBarView navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        
//        walletAdapter = new WalletAdapter(wallet);
//
//        cardWallet = (RecyclerView) findViewById(R.id.rvCardWallet);
//
//        cardWallet.setAdapter(walletAdapter);
//        cardWallet.setLayoutManager(new LinearLayoutManager(Homepage.this, LinearLayoutManager.VERTICAL, false));

        navigationBar = (NavigationBarView) findViewById(R.id.navigationBar);
        navigationBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.page_1:
                    return true;
                case R.id.page_2:
                    return true;
                case R.id.page_3:
                    return true;
                case R.id.page_4:
                    return true;
            };
            return false;
        });
    }
}