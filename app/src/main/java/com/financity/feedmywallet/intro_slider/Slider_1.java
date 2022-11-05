package com.financity.feedmywallet.intro_slider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.financity.feedmywallet.R;

public class Slider_1 extends AppCompatActivity {

    Button to_slider_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider1);

        to_slider_2 = findViewById(R.id.btn_slide1);
        to_slider_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Slider_2.class);
                startActivity(i);
            }
        });
    }
}