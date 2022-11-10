package com.financity.feedmywallet.transaction;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.financity.feedmywallet.App;
import com.financity.feedmywallet.R;
import com.google.android.material.card.MaterialCardView;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionVH>{

    public TransactionAdapter() {
    }

    @NonNull
    @Override
    public TransactionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_row, parent, false);
        return new TransactionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionVH holder, int position) {
        Transaction transaction = App.transactions.get(position);

        if (App.transactions.size() != 0) {
            holder.layoutEmpty.setVisibility(View.GONE);
            holder.cardTrans.setVisibility(View.VISIBLE);
            holder.txTransName.setText(transaction.getName());
            holder.txTransAmount.setText(String.valueOf(transaction.getAmount()));
            holder.txTransCategory.setText(transaction.getCategory().getValue());
            holder.txTransNote.setText(transaction.getNote());
            holder.txTransDate.setText(transaction.getDate());
            holder.txTransCurrency.setText(transaction.getWallet().getCurrency());

            if(transaction.getType().equals(Transaction.TRANSACTION_TYPE_INCOME)){
                holder.cardTrans.setCardBackgroundColor(Color.parseColor("#318D56"));
                holder.imgType.setImageResource(R.drawable.ic_in);
            }
            else {
                holder.cardTrans.setCardBackgroundColor(Color.parseColor("#304781"));
                holder.imgType.setImageResource(R.drawable.ic_out);
            }
        }
        else {
            holder.layoutEmpty.setVisibility(View.VISIBLE);
            holder.cardTrans.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return App.transactions.size();
    }

    class TransactionVH extends RecyclerView.ViewHolder{

        TextView txTransName, txTransNote, txTransDate, txTransCategory, txTransAmount, txTransCurrency;
        ImageView imgType;
        MaterialCardView cardTrans;
        ConstraintLayout layoutEmpty;
        public TransactionVH(@NonNull View itemView) {
            super(itemView);
            layoutEmpty = itemView.findViewById(R.id.layoutEmpty);
            imgType = itemView.findViewById(R.id.imgType);
            cardTrans = (MaterialCardView) itemView.findViewById(R.id.cardTrans);
            txTransName = (TextView) itemView.findViewById(R.id.txTransName);
            txTransAmount = (TextView) itemView.findViewById(R.id.txTransAmount);
            txTransCategory = (TextView) itemView.findViewById(R.id.txTransCategory);
            txTransDate = (TextView) itemView.findViewById(R.id.txTransDate);
            txTransCurrency = (TextView) itemView.findViewById(R.id.txTransCurrency);
            txTransNote = (TextView) itemView.findViewById(R.id.txTransNote);
        }
    }

}
