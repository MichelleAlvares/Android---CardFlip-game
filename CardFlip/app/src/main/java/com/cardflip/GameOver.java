package com.cardflip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class GameOver extends AppCompatActivity implements View.OnClickListener{

    private TextView score;
    private Button button;
    private Button logout;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        Bundle bundle = getIntent().getExtras();
        int value = bundle.getInt("score");
        score = (TextView) findViewById(R.id.score);
        score.setText("Score = " + value);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        button =(Button) findViewById(R.id.button);
        logout = (Button) findViewById(R.id.logout);

        button.setOnClickListener(this);
        logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        if(view == button){
            finish();
            startActivity(new Intent(this, GameType.class));
        }
        if(view == logout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

}
