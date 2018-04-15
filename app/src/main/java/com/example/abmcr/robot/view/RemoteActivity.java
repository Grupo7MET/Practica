package com.example.abmcr.robot.view;


import android.content.pm.ActivityInfo;
import android.gesture.GestureLibrary;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.abmcr.robot.R;


/**
 * Created by abmcr on 21/03/2018.
 */

public class RemoteActivity extends AppCompatActivity {

    private String REMOTE_FRAGMENT = "REMOTE_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        initFragment();
    }

    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(REMOTE_FRAGMENT);
        if (fragment == null) {
            fragment = RemoteFragment.newInstance();
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.activity_main_container, fragment, REMOTE_FRAGMENT);
        transaction.commit();

    }
}




