package view.Remote;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.abmcr.robot.R;

import view.Remote.RemoteFragment;


/**
 * Initializes the remote fragment
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class RemoteActivity extends AppCompatActivity {

    private String REMOTE_FRAGMENT = "REMOTE_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

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




