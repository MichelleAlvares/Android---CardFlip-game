package com.cardflip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class GameType extends AppCompatActivity implements View.OnClickListener{

    private Button slow;
    private Button moderate;
    private Button fast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_type);

        slow = (Button) findViewById(R.id.slow);
        moderate = (Button) findViewById(R.id.moderate);
        fast = (Button) findViewById(R.id.fast);

        slow.setOnClickListener(this);
        moderate.setOnClickListener(this);
        fast.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {

        Intent i;
        if(view.equals(slow)) {
            i = new Intent(this, PlayGame.class);
            i.putExtra("Speed", "Slow");
        }
        else if(view.equals(moderate)) {
            i = new Intent(this, PlayGame.class);
            i.putExtra("Speed", "Moderate");
        }
        else if(view.equals(fast)) {
            i = new Intent(this, PlayGame.class);
            i.putExtra("Speed", "Fast");
        }
        else {
            i = new Intent(this, PlayGame.class);
            i.putExtra("Speed", "default");
        }
        finish();
        startActivity(i);

    }

}
