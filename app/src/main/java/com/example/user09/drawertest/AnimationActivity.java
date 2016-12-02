package com.example.user09.drawertest;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class AnimationActivity extends AppCompatActivity {

    private TextView text1;
    private TextView text2;
    private Animation enterAnim;
    private Animation exitAnim;
    private Animation enterFromRight;
    private Animation exitToRight;
    private LinearLayout containerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        getSupportActionBar().setTitle("My own title");
        getSupportActionBar().setSubtitle("Something in subtitle");

        text1   = (TextView) findViewById(R.id.text1);
        text2   = (TextView) findViewById(R.id.text2);

        text1.setText("FIRST 1");
        text2.setText("FIRST 2");

        enterAnim = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.entering_anim_left_in_1);
        exitAnim  = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.exiting_anim_left_out);
        enterFromRight  = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.entering_anim_right_in_1);
        exitToRight     = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.exiting_anim_right_out);
        containerLayout = (LinearLayout) findViewById(R.id.childLayout);

        enterFromRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                containerLayout.animate().translationX(0).setDuration(300).start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        enterAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                containerLayout.animate().translationX(0).setDuration(300).start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        exitToRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                containerLayout.startAnimation(enterAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        exitAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                containerLayout.startAnimation(enterFromRight);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        containerLayout.setOnTouchListener(new View.OnTouchListener() {
            float dX;
            float startingX, endingX, viewChanges;

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startingX = view.getX();
                        dX = startingX - event.getRawX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.animate().x(event.getRawX() + dX).setDuration(0).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        endingX     = view.getX();
                        viewChanges = endingX - startingX;
                        if(viewChanges > 300){
                            view.startAnimation(exitToRight);
                        } else if(viewChanges < -300) {
                            view.startAnimation(exitAnim);
                        } else {
                            view.animate().translationX(0).setDuration(300).start();
                        }
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

    }


    public void onEnter(View view){
        //left enter
        containerLayout.startAnimation(exitAnim);
        exitAnim.setAnimationListener(new Animation.AnimationListener()  {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                text1.setText("");
                text2.setText("");
                containerLayout.startAnimation(enterFromRight);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    public void onExit(View view){
        //right enter
        containerLayout.startAnimation(exitToRight);
        exitToRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                text1.setText("");
                text2.setText("");
                containerLayout.startAnimation(enterAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void onChange(View view){
        text1.setText("SECOND 1");
        text2.setText("SECOND 2");
    }




}
