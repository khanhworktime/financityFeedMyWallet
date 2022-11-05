package com.financity.feedmywallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    Button btnLogin;
    TextView txSignUp, txUsername, txPwd;
    CheckBox ckRememberAccount;
    protected FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initUI();
        initListener();

    }

    private void initUI(){
        txSignUp = findViewById(R.id.txSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        txUsername = findViewById(R.id.txUsername);
        txPwd = findViewById(R.id.txPwd);
        ckRememberAccount = findViewById(R.id.ckRememberAccount);
    }

    private void initListener(){

        txSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignUp.class);
                startActivity(i);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreateNewWallet.class);
                startActivity(i);
                finish();
            }
        });
    }
}