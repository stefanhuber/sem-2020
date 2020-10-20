package fhku.animations;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected View bubble;
    protected int gameSpeed = 1500;
    protected float startY = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bubble = findViewById(R.id.image);

        findViewById(R.id.btn_left).setOnClickListener(this);
        findViewById(R.id.btn_right).setOnClickListener(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (startY < 0) {
                    startY = bubble.getY();
                }

                ObjectAnimator am = ObjectAnimator.ofFloat(bubble, "y", startY, startY - 400);
                am.setDuration(gameSpeed/2);
                am.setRepeatMode(ObjectAnimator.REVERSE);
                am.setRepeatCount(2);
                am.setInterpolator(new DecelerateInterpolator());
                am.start();

                handler.postDelayed(this, gameSpeed);
            }
        }, gameSpeed);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_left && bubble.getX() > 0) {
            ObjectAnimator am = ObjectAnimator.ofFloat(bubble, "x", bubble.getX(), bubble.getX() - 60);
            am.setInterpolator(new DecelerateInterpolator());
            am.setDuration(gameSpeed/4);
            am.start();
        } else if (v.getId() == R.id.btn_right) {
            ObjectAnimator am = ObjectAnimator.ofFloat(bubble, "x", bubble.getX(), bubble.getX() + 60);
            am.setInterpolator(new DecelerateInterpolator());
            am.setDuration(gameSpeed/4);
            am.start();
        }
    }
}