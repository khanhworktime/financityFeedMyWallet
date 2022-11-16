package com.financity.feedmywallet;

import static com.financity.feedmywallet.App.wallets;
import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.financity.feedmywallet.transaction.Transaction;
import com.financity.feedmywallet.transaction.TransactionBottomSheet;
import com.financity.feedmywallet.transaction.section.TransactionSection;
import com.financity.feedmywallet.transaction.section.TransactionSectionAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AllTransactions extends AppCompatActivity {

    MaterialToolbar topAppBar;
    RecyclerView rvTransSections;
    TransactionSectionAdapter transactionSectionAdapter;
    MaterialAutoCompleteTextView txWalletFilter;

    ArrayList<TransactionSection> sections;
    String walletFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_transactions);
        sections = new ArrayList<>();

        topAppBar = (MaterialToolbar) findViewById(R.id.topAppBar);
        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                TransactionBottomSheet addBudgetBottomSheet = new TransactionBottomSheet();
                addBudgetBottomSheet.show(getSupportFragmentManager(), "Add new Transaction");
                return item.getItemId() == R.id.mItemAdd;
            }
        });
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayList<String> walletsName = new ArrayList<String>();
        walletsName.add("Tất cả ví");
        for (int i = 0 ; i < wallets.size(); i++){
            walletsName.add(wallets.get(i).getName());
        }
        walletFilter = walletsName.get(0);

        ArrayAdapter<String> walletsAdapter = new ArrayAdapter<>(this, R.layout.list_item, walletsName);

        txWalletFilter = findViewById(R.id.txWalletFilter);
        txWalletFilter.setAdapter(walletsAdapter);
        txWalletFilter.setOnItemClickListener((parent, view1, position, id)->{
            walletFilter = walletsName.get(position);
        });


        DatabaseReference getTransactions = FirebaseDatabase.getInstance().getReference("users_data")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("transactions");

        getTransactions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<TransactionSection> tempSections = new ArrayList<TransactionSection>();

                SimpleDateFormat dateFormater = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                TransactionSection todaySection = new TransactionSection();
                TransactionSection elseSection = new TransactionSection();

                todaySection.setTransDate(dateFormater.format(new Date()));

                ArrayList<Transaction> todayTrans = new ArrayList<Transaction>();
                ArrayList<Transaction> elseTrans = new ArrayList<Transaction>();

                snapshot.getChildren().forEach(child -> {
                    Transaction tempTrans = child.getValue(Transaction.class);
                    if (tempTrans.getDate().equals(todaySection.getTransDate())) {
                        todayTrans.add(tempTrans);
                    } else {
                        elseTrans.add(tempTrans);
                    }
                });

                todaySection.setTransactions(todayTrans);
                todaySection.setName("Giao dịch hôm nay");

                elseSection.setTransactions(elseTrans);
                elseSection.setName("Giao dịch trước đây");

                tempSections.add(todaySection);
                tempSections.add(elseSection);
                sections = tempSections;

                rvTransSections = findViewById(R.id.rvTransSections);
                transactionSectionAdapter = new TransactionSectionAdapter(sections, getSupportFragmentManager());

                rvTransSections.setAdapter(transactionSectionAdapter);
                rvTransSections.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                transactionSectionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}