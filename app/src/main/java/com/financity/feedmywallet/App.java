package com.financity.feedmywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.financity.feedmywallet.WalletCard.Wallet;
import com.financity.feedmywallet.fragment.EmptyBudgetFragment;
import com.financity.feedmywallet.fragment.HomepageFragment;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class App extends AppCompatActivity {

    public static Wallet wallet = new Wallet();
    public static ArrayList<Budget> budgets = new ArrayList<>();
    NavigationBarView navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        HomepageFragment homepageFragment = new HomepageFragment();
        EmptyBudgetFragment emptyBudgetFragment = new EmptyBudgetFragment();

        FragmentManager fm = getSupportFragmentManager();

        AtomicReference<FragmentTransaction> ft = new AtomicReference<>(fm.beginTransaction());
        ft.get().add(R.id.pageFrame, homepageFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.get().commit();
        navigationBar = findViewById(R.id.navigationBar);
        navigationBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.page_1:
                    ft.set(fm.beginTransaction());
                    ft.get().replace(R.id.pageFrame, homepageFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.get().commit();
                    return true;
                case R.id.page_2:
                    if (budgets.isEmpty()){
                        ft.set(fm.beginTransaction());
                        ft.get().replace(R.id.pageFrame, emptyBudgetFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.get().commit();
                    }
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