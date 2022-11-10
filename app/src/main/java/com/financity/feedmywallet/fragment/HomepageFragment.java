package com.financity.feedmywallet.fragment;


import static com.financity.feedmywallet.App.wallets;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.financity.feedmywallet.R;
import com.financity.feedmywallet.transaction.TransactionAdapter;
import com.financity.feedmywallet.transaction.TransactionBottomSheet;
import com.financity.feedmywallet.walletCard.WalletAdapter;
import com.google.android.material.card.MaterialCardView;

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

        txWalletCurrency = view.findViewById(R.id.txWalletCurrency);
        addNewTransView = view.findViewById(R.id.addNewTransView);

        txWalletCurrency.setText(wallets.get(0).getCurrency());
        walletAdapter = new WalletAdapter(wallets.get(0));

        cardWallet =  view.findViewById(R.id.rvCardWallet);

        cardWallet.setAdapter(walletAdapter);
        cardWallet.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

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