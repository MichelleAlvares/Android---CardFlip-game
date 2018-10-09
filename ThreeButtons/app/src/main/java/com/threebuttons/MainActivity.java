package com.threebuttons;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button button1;
    Button button2;
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int color1 = Color.argb(255, 255, 175, 64);
        int color2 = Color.argb(255, 175, 175, 64);
        int color3 = Color.argb(255, 64, 175, 64);
        View view = findViewById(R.id.view);
        if (v == button1){
            view.setBackgroundColor(color1);
        }
        if (v == button2){
            view.setBackgroundColor(color2);
        }
        if (v == button3){
            view.setBackgroundColor(color3);
        }
    }
}
