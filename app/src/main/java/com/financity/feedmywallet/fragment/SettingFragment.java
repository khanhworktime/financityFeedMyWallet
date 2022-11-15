package com.financity.feedmywallet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.financity.feedmywallet.LogIn;
import com.financity.feedmywallet.R;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingFragment extends Fragment {
    public SettingFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    TextView txUsername;
    CircleImageView imgUserAvatar;
    ConstraintLayout layoutEditUser, layoutLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        txUsername = view.findViewById(R.id.txUsername);
        imgUserAvatar = view.findViewById(R.id.imgUserAvatar);
        layoutEditUser = view.findViewById(R.id.layoutEditUser);
        layoutLogout = view.findViewById(R.id.layoutLogout);

        layoutLogout.setOnClickListener(view1 -> {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(getActivity(), LogIn.class);
            startActivity(i);
            getActivity().finishAffinity();
        });

        return view;
    }
}
