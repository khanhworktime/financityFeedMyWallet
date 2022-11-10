package com.financity.feedmywallet.fragment;


import static com.financity.feedmywallet.App.wallets;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.financity.feedmywallet.App;
import com.financity.feedmywallet.R;
import com.financity.feedmywallet.transaction.TransactionAdapter;
import com.financity.feedmywallet.transaction.TransactionBottomSheet;
import com.financity.feedmywallet.wallet.Wallet;
import com.financity.feedmywallet.wallet.WalletAdapter;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomepageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomepageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public HomepageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomepageFragment.
     */

    RecyclerView cardWallet, rvTrans;
    WalletAdapter walletAdapter;
    TextView txWalletCurrency;
    MaterialCardView addNewTransView;

    FirebaseDatabase mDatabase;


    // TODO: Rename and change types and number of parameters
    public static HomepageFragment newInstance(String param1, String param2) {
        HomepageFragment fragment = new HomepageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);

        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference getWallets = mDatabase.getReference("users_data")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .child("wallets");

        cardWallet =  view.findViewById(R.id.rvCardWallet);

        getWallets.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Wallet> tempWallet = new ArrayList<Wallet>();
                snapshot.getChildren().forEach(child -> {
                    tempWallet.add(child.getValue(Wallet.class));
                });

                wallets = tempWallet;

                txWalletCurrency.setText(wallets.get(0).getCurrency());
                walletAdapter = new WalletAdapter(wallets);
                walletAdapter.notifyDataSetChanged();
                cardWallet.setAdapter(walletAdapter);
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

        rvTrans = view.findViewById(R.id.rvTrans);
        TransactionAdapter transactionAdapter = new TransactionAdapter();
        rvTrans.setAdapter(transactionAdapter);
        transactionAdapter.notifyDataSetChanged();
        rvTrans.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return view;
    }
}