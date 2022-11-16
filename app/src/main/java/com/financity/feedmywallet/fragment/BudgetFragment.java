package com.financity.feedmywallet.fragment;



import static com.financity.feedmywallet.App.budgets;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.financity.feedmywallet.R;
import com.financity.feedmywallet.budget.Budget;
import com.financity.feedmywallet.budget.BudgetAdapter;
import com.financity.feedmywallet.budget.BudgetBottomSheet;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public BudgetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BudgetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BudgetFragment newInstance(String param1, String param2) {
        BudgetFragment fragment = new BudgetFragment();
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

    RecyclerView rvBudget;
    ConstraintLayout layoutNoBudget, layoutBudgets;
    BudgetAdapter budgetAdapter;

    FirebaseDatabase mDatabase;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference getBudget = mDatabase.getReference("users_data")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .child("budgets");


        rvBudget = view.findViewById(R.id.rvBudget);


        getBudget.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Budget> tempBudgets = new ArrayList<>();

                snapshot.getChildren ().forEach(child ->{
                    tempBudgets.add(child.getValue(Budget.class));
                });

                budgets = tempBudgets;
                budgetAdapter = new BudgetAdapter(budgets, getParentFragmentManager());
                budgetAdapter.notifyDataSetChanged();
                rvBudget.setAdapter(budgetAdapter);
                layoutNoBudget = view.findViewById(R.id.layoutNoBudget);
                layoutBudgets = view.findViewById(R.id.layoutBudgets);
                if (budgets.size() == 0) {
                    layoutNoBudget.setVisibility(View.VISIBLE);
                    layoutBudgets.setVisibility(View.INVISIBLE);
                }
                else {
                    layoutNoBudget.setVisibility(View.INVISIBLE);
                    layoutBudgets.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        rvBudget.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        ExtendedFloatingActionButton efabAddBudget = view.findViewById(R.id.efabAddBudget);
        efabAddBudget.setOnClickListener(v -> {
            BudgetBottomSheet addBudgetBottomSheet = new BudgetBottomSheet();
            addBudgetBottomSheet.show(requireActivity().getSupportFragmentManager(), "BottomSheetBudget");
        });
        return view;
    }

}