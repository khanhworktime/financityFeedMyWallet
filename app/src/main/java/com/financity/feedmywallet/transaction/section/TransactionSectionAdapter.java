package com.financity.feedmywallet.transaction.section;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.financity.feedmywallet.R;
import com.financity.feedmywallet.transaction.TransactionAdapter;

import java.util.ArrayList;

public class TransactionSectionAdapter extends RecyclerView.Adapter<TransactionSectionAdapter.TransactionSectionVH> {

    ArrayList<TransactionSection> sections;
    FragmentManager fm;



    public TransactionSectionAdapter(ArrayList<TransactionSection> sections, FragmentManager fm) {
        this.sections = sections;
        this.fm = fm;
    }

    @NonNull
    @Override
    public TransactionSectionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_section_row, parent, false);
        return new TransactionSectionVH(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull TransactionSectionVH holder, int position) {
        TransactionSection section = sections.get(position);

        TransactionAdapter transactionAdapter = new TransactionAdapter(sections.get(position).getTransactions(), fm);

        holder.txTransSectionName.setText(section.getName());
        holder.rvTransSection.setAdapter(transactionAdapter);
        holder.rvTransSection.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.VERTICAL, false));
        transactionAdapter.notifyDataSetChanged();

        if (section.getTransactions().size() > 0) {
            holder.layoutTransSection.setVisibility(View.VISIBLE);
            holder.txEmptyTransSection.setVisibility(View.GONE);
        } else {
            holder.layoutTransSection.setVisibility(View.GONE);
            holder.txEmptyTransSection.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    class TransactionSectionVH extends RecyclerView.ViewHolder{
        TextView txTransSectionName, txEmptyTransSection;
        RecyclerView rvTransSection;
        ConstraintLayout layoutTransSection;

        public TransactionSectionVH(@NonNull View itemView) {
            super(itemView);
            txTransSectionName = itemView.findViewById(R.id.txTransSectionName);
            rvTransSection = itemView.findViewById(R.id.rvTransSection);
            txEmptyTransSection = itemView.findViewById(R.id.txEmptyTransSection);
            layoutTransSection = itemView.findViewById(R.id.layoutTransSection);
        }
    }
}
