package com.financity.feedmywallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Slider_2 extends AppCompatActivity {

    Button to_slider_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider2);

        to_slider_3 = findViewById(R.id.btn_slide2);
        to_slider_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Slider_3.class);
                startActivity(i);
                finish();
            }
        });
    }
}