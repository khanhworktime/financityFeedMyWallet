package com.financity.feedmywallet.budget;

import static com.financity.feedmywallet.App.budgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.financity.feedmywallet.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetVH>{

    ArrayList<Budget> budgets;
    FragmentManager fm;

    public interface Listener{
        void onItemClicked(View v, int position);
    }

    public BudgetAdapter(ArrayList<Budget> budgets, FragmentManager fm) { this.budgets = budgets; this.fm = fm;}

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
        holder.txBudgetCategory.setText(budget.getCategory().getValue());
        holder.setItemOnClick(new Listener() {
            @Override
            public void onItemClicked(View v, int position) {
                BudgetBottomSheet bgBottomSheet = new BudgetBottomSheet(position);
                bgBottomSheet.show(fm, "Budget bottom sheet");
            }
        });
    }

    @Override
    public int getItemCount() {
        return budgets.size();
    }

     class BudgetVH extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView txBudgetName;
        final TextView txBudgetWallet;
        final TextView txAmount;
        final TextView txDuration;
        final TextView txBudgetCategory;
        final ProgressBar prgBudget;
        MaterialCardView cardBudget;
        Listener onItemClicked;


        public BudgetVH(@NonNull View itemView) {
            super(itemView);
            txBudgetName = itemView.findViewById(R.id.txBudgetName);
            txAmount = itemView.findViewById(R.id.txAmount);
            txBudgetWallet = itemView.findViewById(R.id.txBudgetWallet);
            txDuration = itemView.findViewById(R.id.txDuration);
            prgBudget = itemView.findViewById(R.id.prgBudget);
            txBudgetCategory = itemView.findViewById(R.id.txBudgetCategory);
            cardBudget = itemView.findViewById(R.id.cardBudget);
        }

        public void setItemOnClick(Listener onItemClicked){
            this.onItemClicked = onItemClicked;
            cardBudget.setOnClickListener(this);
        }

         @Override
         public void onClick(View v) {
            onItemClicked.onItemClicked(v, getAdapterPosition());
         }
     }

}
