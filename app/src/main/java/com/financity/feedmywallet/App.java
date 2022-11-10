package com.financity.feedmywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.financity.feedmywallet.category.Categories;
import com.financity.feedmywallet.category.Category;
import com.financity.feedmywallet.transaction.Transaction;
import com.financity.feedmywallet.walletCard.Wallet;
import com.financity.feedmywallet.budget.Budget;
import com.financity.feedmywallet.fragment.BudgetFragment;
import com.financity.feedmywallet.fragment.HomepageFragment;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class App extends AppCompatActivity{

    public static final ArrayList<Budget> budgets = new ArrayList<>();
    public static final ArrayList<Wallet> wallets = new ArrayList<>();
    public static final Categories categories = new Categories();
    public static final ArrayList<Transaction> transactions = new ArrayList<>();
    NavigationBarView navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        HomepageFragment homepageFragment = new HomepageFragment();
        BudgetFragment budgetFragment = new BudgetFragment();

        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.pageFrame, homepageFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

        navigationBar = findViewById(R.id.navigationBar);
        navigationBar.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.page_1:
                    getSupportFragmentManager().beginTransaction().replace(R.id.pageFrame, homepageFragment, "HomepageFragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                    return true;
                case R.id.page_2:
                    getSupportFragmentManager().beginTransaction().replace(R.id.pageFrame, budgetFragment, "BudgetFragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                    return true;
                case R.id.page_3:
                    return true;
                case R.id.page_4:
                    return true;
            }
            return false;
        });
    }
}