package com.financity.feedmywallet.budget;

import static com.financity.feedmywallet.App.budgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.financity.feedmywallet.R;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetVH>{

    public BudgetAdapter() {
    }

    @NonNull
    @Override
    public BudgetVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_row, parent, false);
        return new BudgetVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetVH holder, int position) {
        Budget budget = budgets.get(position);

        holder.txBudgetWallet.setText(budget.getWallet().getName());
        holder.txBudgetName.setText(budget.getName());
        holder.txDuration.setText(budget.getDuration());
        holder.txAmount.setText(String.valueOf(budget.getAmount()));
//        holder.prgBudget
    }

    @Override
    public int getItemCount() {
        return budgets.size();
    }

    static class BudgetVH extends RecyclerView.ViewHolder{

        final TextView txBudgetName;
        final TextView txBudgetWallet;
        final TextView txAmount;
        final TextView txDuration;
        final ProgressBar prgBudget;

        public BudgetVH(@NonNull View itemView) {
            super(itemView);
            txBudgetName = itemView.findViewById(R.id.txBudgetName);
            txAmount = itemView.findViewById(R.id.txAmount);
            txBudgetWallet = itemView.findViewById(R.id.txBudgetWallet);
            txDuration = itemView.findViewById(R.id.txDuration);
            prgBudget = itemView.findViewById(R.id.prgBudget);
        }
    }
}
