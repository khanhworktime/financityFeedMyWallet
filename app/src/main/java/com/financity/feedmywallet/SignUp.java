package com.financity.feedmywallet;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.financity.feedmywallet.component.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    Button btnSignUp;
    TextView txLogin;
    TextInputEditText txEmailReg, txPwdReg, txRePwdReg;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        initUI();
        initListener();

    }

    private void initUI(){
        btnSignUp = findViewById(R.id.btnSignUp);
        txLogin = findViewById(R.id.txLogin);
        txEmailReg = findViewById(R.id.txEmailReg);
        txPwdReg = findViewById(R.id.txPwdReg);
        txRePwdReg = findViewById(R.id.txRePwdReg);
    }

    private void initListener(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txEmailReg.getText().toString().trim();
                String password = txPwdReg.getText().toString().trim();

                Toast.makeText(SignUp.this, "clicked",
                        Toast.LENGTH_SHORT).show();

                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                loadingDialog = new LoadingDialog(SignUp.this);
                loadingDialog.ShowDialog("Signing Up...");

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loadingDialog.HideDialog();
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    Intent i = new Intent(SignUp.this, LogIn.class);
                                    startActivity(i);
                                    finishAffinity();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(SignUp.this, "Đăng ký thất bại, thử lại sau",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });
        txLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LogIn.class);
                startActivity(i);
                finish();
            }
        });
    }
}