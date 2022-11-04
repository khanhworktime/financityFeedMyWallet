package com.financity.feedmywallet.WalletCard;

import android.icu.number.NumberFormatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.financity.feedmywallet.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletVH> {
    interface Listener{
        void onItemClicked(Wallet wallet);
    }

    Wallet wallet;
    ArrayList<Wallet> wallets;

    public WalletAdapter(Wallet wallet) {
        this.wallet = wallet;
        wallets = new ArrayList<>();
        wallets.add(wallet);
    }

    @NonNull
    @Override
    public WalletVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_card, parent, false);
        return new WalletVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletVH holder, int position) {
        String name = wallets.get(position).getName();
        String currency = wallets.get(position).getCurrency();
        String walletBalance = currency.concat(" " + wallets.get(position).getBalance().toString());

        holder.txWalletName.setText(name);
        holder.txBalance.setText(walletBalance);
    }

    static class WalletVH extends RecyclerView.ViewHolder{
        TextView txWalletName, txBalance;

        public WalletVH(@NonNull View itemView) {
            super(itemView);
            txWalletName = (TextView) itemView.findViewById(R.id.txWalletName);
            txBalance = (TextView) itemView.findViewById(R.id.txBalance);
        }
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }
}
