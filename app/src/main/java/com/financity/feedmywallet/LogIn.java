package com.financity.feedmywallet;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.financity.feedmywallet.component.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {

    Button btnLogin;
    TextView txSignUp, txUsername, txPwd;
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
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String email = txUsername.getText().toString().trim();
                String password = txPwd.getText().toString().trim();

                if(email.equals("") || password.equals("")) {
                    Snackbar.make(view, "Tài khoản hoặc mật khẩu trống !",
                            Snackbar.LENGTH_SHORT).show();
                    return ;
                }

                LoadingDialog loading = new LoadingDialog(LogIn.this);
                loading.ShowDialog("Logging in...");
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loading.HideDialog();
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent i = new Intent(getApplicationContext(), App.class);
                                    startActivity(i);
                                    finishAffinity();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LogIn.this, "Đăng nhập thất bại, thử lại sau",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}