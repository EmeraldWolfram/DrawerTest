package com.example.user09.drawertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        text1   = (TextView) findViewById(R.id.text1);
        text2   = (TextView) findViewById(R.id.text2);

        text1.setText("FIRST 1");
        text2.setText("FIRST 2");

        enterAnim = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.entering_anim_left_in_1);
        exitAnim  = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.exiting_anim_left_out);
        enterFromRight  = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.entering_anim_right_in_1);
        exitToRight     = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.exiting_anim_right_out);
        containerLayout = (LinearLayout) findViewById(R.id.childLayout);
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
