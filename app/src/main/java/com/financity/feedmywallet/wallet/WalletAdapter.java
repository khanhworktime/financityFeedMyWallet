package com.financity.feedmywallet.wallet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.financity.feedmywallet.R;

import java.util.ArrayList;
import java.util.Locale;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletVH> {
    interface Listener{
        void onItemClicked(Wallet wallet);
    }

    final ArrayList<Wallet> wallets;

    public WalletAdapter(ArrayList<Wallet> wallets) {
        this.wallets = wallets;
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
        String walletBalance = currency.concat(" " + String.format(Locale.getDefault(), "%,.2f", wallets.get(position).getBalance()));

        holder.txWalletName.setText(name);
        holder.txBalance.setText(walletBalance);
    }

    static class WalletVH extends RecyclerView.ViewHolder{
        final TextView txWalletName;
        final TextView txBalance;

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
