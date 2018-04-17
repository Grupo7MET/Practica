package com.example.abmcr.robot.view.View.View;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.abmcr.robot.R;

/**
 * Initializes the communication fragment
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class CommunicationActivity extends AppCompatActivity {
    private String COMMUNICATION_FRAGMENT = "COMMUNICATION_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initFragment();
    }

    private void initFragment(){
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(COMMUNICATION_FRAGMENT);
        if(fragment == null){
            fragment = CommunicationFragment.newInstance();
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        transaction.replace(R.id.activity_main_container,fragment,COMMUNICATION_FRAGMENT);
        transaction.commit();

    }
}
