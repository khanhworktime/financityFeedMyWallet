package com.financity.feedmywallet.WalletCard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.financity.feedmywallet.R;
import com.google.android.material.textfield.TextInputEditText;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletVH> {
    interface Listener{
        void onItemClicked(Wallet wallet);
    }

    Wallet wallet;

    public WalletAdapter(Wallet wallet) {
        this.wallet = wallet;
    }

    @NonNull
    @Override
    public WalletVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_card, parent, false);
        return new WalletVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletVH holder, int position) {
        holder.txWalletName.setText(wallet.getName());
        holder.txBalance.setText(wallet.getBalance());
    }

    class WalletVH extends RecyclerView.ViewHolder{
        TextInputEditText txWalletName, txBalance;

        public WalletVH(@NonNull View itemView) {
            super(itemView);
            txWalletName = itemView.findViewById(R.id.txWalletName);
            txBalance = itemView.findViewById(R.id.txBalance);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
