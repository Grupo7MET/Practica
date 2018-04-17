package com.example.abmcr.robot.view.View.View;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.abmcr.robot.R;
import com.example.abmcr.robot.view.View.Model.Constants;
import com.example.abmcr.robot.view.View.View.MainActivity;

/**
 * Class that creates the view and keeps it visible for splash duration
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class SplashActivity extends AppCompatActivity {

    Animation scale;
    ProgressBar progressBar;
    ImageView splash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        scale = new AnimationUtils().loadAnimation(getApplicationContext(), R.anim.scale);
        splash = (ImageView)findViewById(R.id.splash);
        splash.startAnimation(scale);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress",Constants.MIN_PROGRESS,Constants.MAX_PROGRESS);
        animation.setDuration(Constants.ANIMATION_DURATION);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    /**
     * Keeps the splashScreen visible until splash duration is finished
     */
    protected void onStart(){
        super.onStart();

        Handler mHandler = new Handler();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

        }, Constants.SPLASH_DURATION);
    }
}

