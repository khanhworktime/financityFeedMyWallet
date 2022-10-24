package com.financity.feedmywallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {

    Button to_slider;
    TextView to_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        to_slider = findViewById(R.id.btn_signup);
        to_slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Slider_1.class);
                startActivity(i);
                finish();
            }
        });

        to_login = findViewById(R.id.tv_login);
        to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LogIn.class);
                startActivity(i);
                finish();
            }
        });
    }
}