package com.cardflip;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayGame extends AppCompatActivity {

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;

    private TextView textView;
    private TextView scoreView;

    private String bottomToTop ="bottomToTop";
    private String topToBottom ="topToBottom";
    private String leftToRight ="leftToRight";
    private String rightToLeft ="rightToLeft";

    private List<String> colourNames = new ArrayList<String>();
    private List<String> rotationX = new ArrayList<String>();
    private List<String> rotationY = new ArrayList<String>();
    private Map<String, ImageView> colourImageMap = new HashMap<String, ImageView>();

    final AnimatorSet set = new AnimatorSet();

    private String direction = "";
    private int duration;
    private int startDelay;
    private float x1, x2;
    private float y1, y2;
    private int score = 0;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String dataName = "MyData";
    String intName = "MyInt";
    int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        Bundle bundle = getIntent().getExtras();
        String speed = bundle.getString("Speed");
        if(speed != null) {
            switch (speed) {
                case "Slow":
                    duration = 1800;
                    startDelay = 700;
                    break;
                case "Moderate":
                    duration = 1400;
                    startDelay = 500;
                    break;
                case "Fast":
                    duration = 1000;
                    startDelay = 200;
                    break;
                default:
                    duration = 1400;
                    startDelay = 500;
            }
        }
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        imageView5 = (ImageView) findViewById(R.id.imageView5);

        textView = (TextView) findViewById(R.id.text);
        scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText("Score = "+score);

        colourNames.add("yellow");
        colourNames.add("red");
        colourNames.add("grey");
        colourNames.add("blue");
        colourNames.add("green");

        colourImageMap.put("yellow", imageView1);
        colourImageMap.put("red", imageView2);
        colourImageMap.put("grey", imageView3);
        colourImageMap.put("blue", imageView4);
        colourImageMap.put("green", imageView5);

        rotationY.add(bottomToTop);
        rotationY.add(topToBottom);
        rotationX.add(leftToRight);
        rotationX.add(rightToLeft);

        prefs = getSharedPreferences(dataName,MODE_PRIVATE);
        editor = prefs.edit();
        highScore = prefs.getInt(intName, 0);

        startRotations();
    }

    public void startRotations(){
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                set.playTogether(getAnimations());
                set.start();
            }
        });
        set.playTogether(getAnimations());
        set.start();
    }

    public List<Animator> getAnimations(){
        direction = "";
        Collections.shuffle(colourNames);
        colourImageMap.get(colourNames.get(0));

        Spannable word = new SpannableString(colourNames.get(0));
        switch (colourNames.get(0)){
            case "yellow":
                word.setSpan(new ForegroundColorSpan(Color.GREEN), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "red":
                word.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "grey":
                word.setSpan(new ForegroundColorSpan(Color.RED), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "blue":
                word.setSpan(new ForegroundColorSpan(Color.GRAY), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "green":
                word.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            default:
                word.setSpan(new ForegroundColorSpan(Color.CYAN), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
        }
        textView.setText(word);

        Collections.shuffle(rotationX);
        Collections.shuffle(rotationY);
        Map<ImageView, String> animatorMap = new HashMap<ImageView, String>();
        animatorMap.put(imageView1, rotationY.get(0));
        animatorMap.put(imageView2, rotationX.get(0));
        animatorMap.put(imageView3, rotationX.get(1));
        animatorMap.put(imageView4, rotationY.get(0));
        animatorMap.put(imageView5, rotationY.get(1));

        List<Animator> animationList = new ArrayList<>();
        for(Map.Entry<ImageView, String> entry : animatorMap.entrySet()) {
            animationList.add(getCustomAnimation(entry.getKey(), entry.getValue()));
        }

        direction = animatorMap.get(colourImageMap.get(colourNames.get(0)));
        return animationList;
    }

    public Animator getCustomAnimation(ImageView imageView, String direction){
        ObjectAnimator objectAnimator;
        switch (direction){
            case "bottomToTop":
                objectAnimator = ObjectAnimator.ofFloat(imageView, "rotationX", 0f, 180f);
                break;
            case "topToBottom":
                objectAnimator = ObjectAnimator.ofFloat(imageView, "rotationX", 180f, 0f);
                break;
            case "leftToRight":
                objectAnimator = ObjectAnimator.ofFloat(imageView, "rotationY", 0f, 180f);
                break;
            case "rightToLeft":
                objectAnimator = ObjectAnimator.ofFloat(imageView, "rotationY", 180f, 0f);
                break;
            default:
                objectAnimator = ObjectAnimator.ofFloat(imageView, "rotationY", 0f, 180f);
        }
            objectAnimator.setDuration(duration);
//            objectAnimator.setStartDelay(startDelay);
        return objectAnimator;
    }

    public boolean onTouchEvent(MotionEvent touchevent)
    {
        List<String> directionList = new ArrayList<String>();

        switch (touchevent.getAction())
        {
            // when user first touches the screen we get x and y coordinate
            case MotionEvent.ACTION_DOWN:
            {
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                x2 = touchevent.getX();
                y2 = touchevent.getY();

                //if left to right sweep event on screen
                if (x1 < x2)
                {
                    directionList.add(leftToRight);
                }

                // if right to left sweep event on screen
                if (x1 > x2)
                {
                    directionList.add(rightToLeft);
                }

                // if UP to Down sweep event on screen
                if (y1 < y2)
                {
                    directionList.add(topToBottom);
                }

                //if Down to UP sweep event on screen
                if (y1 > y2)
                {
                    directionList.add(bottomToTop);
                }
                break;
            }
        }
        if(touchevent.getAction() == MotionEvent.ACTION_UP) {
            if (directionList.contains(direction)) {
                score += 1;
                scoreView.setText("Score = " + score);
                final Toast toast = Toast.makeText(this, "+1", Toast.LENGTH_SHORT);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 400);
            } else {
                set.cancel();
                if(score > highScore)
                {
                    final Toast toast = Toast.makeText(this, "New High score", Toast.LENGTH_SHORT);
                    toast.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 700);
                    highScore = score;
                    editor.putInt(intName, highScore);
                    editor.commit();
                }
                finish();
                Intent i;
                i = new Intent(this, GameOver.class);
                i.putExtra("score", score);
                startActivity(i);
            }
        }
        return false;
    }
}
