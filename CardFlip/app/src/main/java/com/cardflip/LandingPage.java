package com.cardflip;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class LandingPage extends Activity implements View.OnClickListener
{

    SharedPreferences preferences;
    String dataName = "MyData";
    String intName = "MyInt";
    int highScore;
    Button button;
    Button instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        ImageView imageView = (ImageView) findViewById(R.id.main_image);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(imageView, "rotationY", 0f, 180f);
        objectAnimator1.setDuration(1000);
        objectAnimator1.setRepeatCount(-1);
        objectAnimator1.start();

        button =(Button) findViewById(R.id.button);
        instructions = (Button) findViewById(R.id.instructions);
        button.setOnClickListener(this);
        instructions.setOnClickListener(this);

        preferences = getSharedPreferences(dataName,MODE_PRIVATE);
        highScore = preferences.getInt(intName, 0);
        TextView highScoreText = (TextView) findViewById(R.id.high_score);
        highScoreText.setText("HighScore = "+highScore);
    }

    @Override
    public void onClick(View view)
    {
        Intent i;
        if(view.equals(button))
            i = new Intent(this, GameType.class);
        else
            i = new Intent(this, Instructions.class);
        finish();
        startActivity(i);

    }
}
