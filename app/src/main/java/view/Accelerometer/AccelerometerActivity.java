package view.Accelerometer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.abmcr.robot.R;

/**
 * Initializes the accelerometer fragment
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class AccelerometerActivity extends AppCompatActivity {
    private String ACCELEROMETER_FRAGMENT = "ACCELEROMETER_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        initFragment();
    }

    private void initFragment(){
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(ACCELEROMETER_FRAGMENT);
        if(fragment == null){
            fragment = AccelerometerFragment.newInstance();
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        transaction.replace(R.id.activity_main_container,fragment,ACCELEROMETER_FRAGMENT);
        transaction.commit();

    }
}
