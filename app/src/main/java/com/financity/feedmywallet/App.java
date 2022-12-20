package com.financity.feedmywallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.financity.feedmywallet.category.Categories;
import com.financity.feedmywallet.fragment.SettingFragment;
import com.financity.feedmywallet.transaction.Transaction;
import com.financity.feedmywallet.wallet.Wallet;
import com.financity.feedmywallet.budget.Budget;
import com.financity.feedmywallet.fragment.BudgetFragment;
import com.financity.feedmywallet.fragment.HomepageFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class App extends AppCompatActivity{

    public static ArrayList<Budget> budgets = new ArrayList<>();
    public static ArrayList<Wallet> wallets = new ArrayList<>();
    public static Categories categories = new Categories();
    public static ArrayList<Transaction> transactions = new ArrayList<>();
    public static ArrayList<Transaction> incomeTransactions = new ArrayList<>();
    public static ArrayList<Transaction> outcomeTransactions = new ArrayList<>();
    public static Float totalBalance = 0F;
    public static Float totalTransactions = 0F;

    public boolean FIRST_LOAD = true;
    public static NavigationBarView navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        HomepageFragment homepageFragment = new HomepageFragment();
        BudgetFragment budgetFragment = new BudgetFragment();
        SettingFragment settingFragment = new SettingFragment();

        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();


        DatabaseReference user_data = FirebaseDatabase.getInstance().getReference("users_data")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        DatabaseReference getWallets = user_data.child("wallets");
        DatabaseReference getBudgets = user_data.child("budgets");
        DatabaseReference getTransactions = user_data.child("transactions");

        getWallets.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Wallet> temp = new ArrayList<Wallet>();
                snapshot.getChildren().forEach(child -> {
                    temp.add(child.getValue(Wallet.class));
                    totalBalance += child.getValue(Wallet.class).getBalance();
                });

                wallets = temp;
                if (FIRST_LOAD)
                    ft.add(R.id.pageFrame, homepageFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                FIRST_LOAD = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        getBudgets.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Budget> temp = new ArrayList<>();
                snapshot.getChildren().forEach(child -> {
                    temp.add(child.getValue(Budget.class));
                });

                budgets = temp;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        getTransactions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Transaction> temp = new ArrayList<>();
                snapshot.getChildren().forEach(child -> {
                    temp.add(child.getValue(Transaction.class));
                });
                transactions = temp;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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
                    getSupportFragmentManager().beginTransaction().replace(R.id.pageFrame, settingFragment, "SettingFragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                    return true;
            }
            return false;
        });
    }
}