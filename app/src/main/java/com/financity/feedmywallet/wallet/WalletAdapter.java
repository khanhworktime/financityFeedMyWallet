package com.financity.feedmywallet.wallet;

import static com.financity.feedmywallet.App.transactions;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.financity.feedmywallet.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletVH> {
    interface Listener{
        void onItemClicked(View v, int position);
        void onItemFocused(View v, int position);
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

        holder.setListener(new Listener() {
            @Override
            public void onItemClicked(View v, int position) {
                Snackbar.make(v, "Clicked", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onItemFocused(View v, int position) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.getMenu().add("Xóa");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Xóa")) {
                            DatabaseReference deleteWallet = FirebaseDatabase.getInstance().getReference("users_data")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("wallets").child(wallets.get(position).getId());

                            DatabaseReference deleteWalletTrans = FirebaseDatabase.getInstance().getReference("users_data")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("transactions");

                            transactions.forEach(trans -> {
                                if (trans.getWallet().getId().equals(wallets.get(position).getId())) deleteWalletTrans.child(trans.getId()).removeValue();
                            });

                            deleteWallet.removeValue();
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    static class WalletVH extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        final TextView txWalletName;
        final TextView txBalance;
        MaterialCardView walletCard;
        Listener listener;

        public WalletVH(@NonNull View itemView) {
            super(itemView);
            txWalletName = (TextView) itemView.findViewById(R.id.txWalletName);
            txBalance = (TextView) itemView.findViewById(R.id.txBalance);
            walletCard = itemView.findViewById(R.id.cardWallet);
        }

        public void setListener(Listener listener){
            this.listener = listener;
            walletCard.setOnClickListener(this);
            walletCard.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClicked(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onItemFocused(v, getAdapterPosition());
            return true;
        }
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }
}
