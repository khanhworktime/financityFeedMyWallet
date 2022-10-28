package com.financity.feedmywallet.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.financity.feedmywallet.R;
import com.financity.feedmywallet.budget.BudgetBottomSheet;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmptyBudgetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmptyBudgetFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EmptyBudgetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmptyBudgetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmptyBudgetFragment newInstance(String param1, String param2) {
        EmptyBudgetFragment fragment = new EmptyBudgetFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empty_budget, container, false);
        ExtendedFloatingActionButton efabAddBudget = view.findViewById(R.id.efabAddBudget);

        efabAddBudget.setOnClickListener(v -> {
            BudgetBottomSheet addBudgetBottomSheet = new BudgetBottomSheet();
            addBudgetBottomSheet.show(getParentFragmentManager(), addBudgetBottomSheet.getTag());
        });
        return view;
    }
}