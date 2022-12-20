package com.financity.feedmywallet.fragment;


import static com.financity.feedmywallet.App.totalBalance;
import static com.financity.feedmywallet.App.totalTransactions;
import static com.financity.feedmywallet.App.transactions;
import static com.financity.feedmywallet.App.wallets;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.financity.feedmywallet.AllTransactions;
import com.financity.feedmywallet.App;
import com.financity.feedmywallet.CreateNewWallet;
import com.financity.feedmywallet.R;
import com.financity.feedmywallet.category.Category;
import com.financity.feedmywallet.transaction.Transaction;
import com.financity.feedmywallet.transaction.TransactionAdapter;
import com.financity.feedmywallet.transaction.TransactionBottomSheet;
import com.financity.feedmywallet.wallet.Wallet;
import com.financity.feedmywallet.wallet.WalletAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class HomepageFragment extends Fragment{

    public HomepageFragment() {
        // Required empty public constructor
    }

    RecyclerView cardWallet, rvTrans;
    WalletAdapter walletAdapter;
    TransactionAdapter transactionAdapter;
    TextView txWalletCurrency, txEmptyTrans, txAllTrans, txPercentageOutcome, txPercentageIncome, txReportMonthOutcome, txReportMonthIncome, txTotalBalance;
    MaterialCardView addNewTransView;
    LinearProgressIndicator prgIncome, prgOutcome;
    MaterialButton btnAddWallet;
    FirebaseDatabase mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);

        if (wallets.isEmpty()) {
            Intent intent = new Intent(requireActivity(), CreateNewWallet.class);
            startActivity(intent);
            requireActivity().finishAffinity();
            return null;
        }

        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference getWallets = mDatabase.getReference("users_data")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .child("wallets");

        cardWallet =  view.findViewById(R.id.rvCardWallet);

        txTotalBalance = view.findViewById(R.id.txTotalBalance);

        getWallets.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Wallet> tempWallet = new ArrayList<Wallet>();
                AtomicReference<Float> tempBalance = new AtomicReference<>(0F);
                snapshot.getChildren().forEach(child -> {
                    tempWallet.add(child.getValue(Wallet.class));
                    tempBalance.updateAndGet(v -> v + child.getValue(Wallet.class).getBalance());
                });

                wallets = tempWallet;
                if (wallets.size() == 0) {
                    Intent i = new Intent(getContext(), App.class);
                    startActivity(i);
                    getActivity().finish();
                }

                totalBalance = tempBalance.get();
                txTotalBalance.setText(String.format(Locale.getDefault(), "%,.2f", totalBalance));

//                @TODO default VND
                txWalletCurrency.setText("VND");
                walletAdapter = new WalletAdapter(wallets);
                walletAdapter.notifyDataSetChanged();
                cardWallet.setAdapter(walletAdapter);
                cardWallet.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                        if (parent.getChildCount() != 0)
                            outRect.right = 32;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cardWallet.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        txWalletCurrency = view.findViewById(R.id.txWalletCurrency);
        addNewTransView = view.findViewById(R.id.addNewTransView);

        addNewTransView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionBottomSheet addBudgetBottomSheet = new TransactionBottomSheet();
                addBudgetBottomSheet.show(HomepageFragment.this.getParentFragmentManager(), "Add new transaction");
            }
        });

        DatabaseReference getTrans = FirebaseDatabase.getInstance().getReference("users_data")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("transactions");

        rvTrans = view.findViewById(R.id.rvTrans);
        txEmptyTrans = view.findViewById(R.id.txEmptyTrans);

        prgIncome = view.findViewById(R.id.prgIncome);
        prgIncome.setIndeterminate(true);
        prgOutcome = view.findViewById(R.id.prgOutcome);
        prgOutcome.setIndeterminate(true);

        txPercentageIncome = view.findViewById(R.id.txPercentageIncome);
        txPercentageOutcome = view.findViewById(R.id.txPercentageOutcome);

        txReportMonthIncome = view.findViewById(R.id.txReportMonthIncome);
        txReportMonthOutcome = view.findViewById(R.id.txReportMonthOutcome);

        getTrans.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Transaction> tempTrans = new ArrayList<Transaction>();
                ArrayList<Transaction> tempToday = new ArrayList<Transaction>();

                ArrayList<Transaction> incomeTemp = new ArrayList<Transaction>();
                ArrayList<Transaction> outcomeTemp = new ArrayList<Transaction>();

                AtomicReference<Float> tempTransValue = new AtomicReference<>(0F);
                snapshot.getChildren().forEach(child -> {
                    SimpleDateFormat formaterDate = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                    Transaction transaction = child.getValue(Transaction.class);
                    tempTrans.add(child.getValue(Transaction.class));

                    tempTransValue.updateAndGet(v -> v + transaction.getAmount());

                    try {
                        Date transDate = formaterDate.parse(transaction.getDate());

                        Calendar transD = Calendar.getInstance();
                        transD.setTimeInMillis(transDate.getTime());

                        Calendar today = Calendar.getInstance();
                        today.setTime(new Date());

                        if (transD.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) && transD.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
                            tempToday.add(transaction);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (!transaction.getCategory().getValue().equals(Category.CATEGORY_INITWALLET.getValue()) && transaction.getType().equals(Transaction.TRANSACTION_TYPE_INCOME))
                        incomeTemp.add(child.getValue(Transaction.class));
                    if (!transaction.getCategory().getValue().equals(Category.CATEGORY_INITWALLET.getValue()) && transaction.getType().equals(Transaction.TRANSACTION_TYPE_OUTCOME))
                        outcomeTemp.add(child.getValue(Transaction.class));
                });

                App.totalTransactions = tempTransValue.get();

                App.incomeTransactions = incomeTemp;
                App.outcomeTransactions = outcomeTemp;

                transactions = tempTrans;
                transactionAdapter = new TransactionAdapter(tempToday, getParentFragmentManager());
                rvTrans.setAdapter(transactionAdapter);
                transactionAdapter.notifyDataSetChanged();

                if(transactionAdapter.getTransactions().size() > 0) {
                    rvTrans.setVisibility(View.VISIBLE);
                    txEmptyTrans.setVisibility(View.GONE);
                } else {
                    rvTrans.setVisibility(View.GONE);
                    txEmptyTrans.setVisibility(View.VISIBLE);
                }

//                Income and Outcome filter

                AtomicReference<Float> totalIncome = new AtomicReference<>(0F);
                AtomicReference<Float> totalOutcome = new AtomicReference<>(0F);

                App.incomeTransactions.forEach(transaction -> {
                    totalIncome.set(totalIncome.get() + transaction.getAmount());
                });
                App.outcomeTransactions.forEach(transaction->{
                    totalOutcome.set(totalOutcome.get() + transaction.getAmount());
                });

                int incomeProgress =(int) Math.round(Math.ceil(totalIncome.get() / (totalIncome.get() + totalOutcome.get()) * 100));
                int outcomeProgress =(int) Math.round(Math.ceil(totalOutcome.get() / (totalIncome.get() + totalOutcome.get()) *100));
                prgIncome.setProgressCompat( incomeProgress, true);
                prgOutcome.setProgressCompat( outcomeProgress, true);

                txPercentageIncome.setText(String.format(Locale.getDefault(), "%d%%", incomeProgress));
                txPercentageOutcome.setText(String.format(Locale.getDefault(), "%d%%", outcomeProgress));

                txReportMonthIncome.setText(String.format(Locale.getDefault(), "+%,.2f", totalIncome.get()));
                txReportMonthOutcome.setText(String.format(Locale.getDefault(), "-%,.2f", totalOutcome.get()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        rvTrans.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        txAllTrans = view.findViewById(R.id.txAllTrans);
        txAllTrans.setOnClickListener(view1 -> {
            Intent i = new Intent(requireActivity(), AllTransactions.class);
            startActivity(i);
        });

        btnAddWallet = view.findViewById(R.id.btnAddWallet);
        btnAddWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), CreateNewWallet.class));
            }
        });

        return view;
    }
}