package com.financity.feedmywallet.introSlider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.financity.feedmywallet.CreateNewWallet;
import com.financity.feedmywallet.R;

public class Slider_3 extends AppCompatActivity {

    Button to_createnew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider3);

        to_createnew = findViewById(R.id.btn_slide3);
        to_createnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreateNewWallet.class);
                startActivity(i);
                finish();
            }
        });
    }
}