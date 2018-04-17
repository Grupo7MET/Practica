package com.example.abmcr.robot.view.View.View;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abmcr.robot.R;

import java.util.ArrayList;

import com.example.abmcr.robot.view.View.Model.Constants;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Class that creates the view and assign all the visual components.
 * Create the button listeners, manage the pattern detection and the gyroscope
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class RemoteFragment extends Fragment {

    private TextView tvTitle, tvTemp, tvVelocity, tvVelocityValue;
    private ImageView ivDanger;
    private Button btnManual, btnAuto, btnLights, btnThrottle, btnGearDown, btnGearUp;
    private int gear;
    private String curve;

    private GestureLibrary mLibrary;
    private GestureOverlayView gestures;

    private SensorManager mSensorManager;
    private Sensor mGyroscope;
    private SensorEventListener mGyroscopeEventListener;

    private Constants constants = new Constants();

    public static RemoteFragment newInstance(){
        return new RemoteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View v = inflater.inflate(R.layout.fragment_remote,container,false);
        bindViews(v);
        gear = constants.INIT_GEAR;
        curve = "";
        return v;
    }

    //Assigning all the visual components
    private void bindViews(View v){

        tvTitle = v.findViewById(R.id.tvTitle);
        tvTemp = v.findViewById(R.id.tvTemp);
        tvVelocity = v.findViewById(R.id.tvVelocity);
        tvVelocityValue = v.findViewById(R.id.tvVelocityValue);
        ivDanger = v.findViewById(R.id.ivDanger);
        btnAuto = v.findViewById(R.id.btnAuto);
        btnManual = v.findViewById(R.id.btnMan);
        btnLights = v.findViewById(R.id.btnLights);
        btnThrottle = v.findViewById(R.id.btnThrottle);
        btnGearDown = v.findViewById(R.id.btnGearDown);
        btnGearUp = v.findViewById(R.id.btnGearUp);
        mLibrary = GestureLibraries.fromRawResource(getContext(), R.raw.gestures);

        if (!mLibrary.load()) {
            this.getActivity().finish();
        }
        gestures = v.findViewById (R.id.gestures);

        tvTitle.setText(R.string.rTitle);
        tvTemp.setText(R.string.rTemp);
        tvVelocity.setText(R.string.rVelocity);
        btnAuto.setText(R.string.rAuto);
        btnManual.setText(R.string.rManual);
        btnLights.setText(R.string.rLigthsOn);
        btnThrottle.setText(R.string.rThrottle);
        btnGearDown.setText(R.string.rGearDown);
        btnGearUp.setText(R.string.rGearUp);


        gestures.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {

            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                ArrayList<Prediction> predictions = mLibrary.recognize(gesture);

                // We want at least one prediction
                if (predictions.size() > 0) {
                    Prediction prediction = predictions.get(0);
                    // We want at least some confidence in the result
                    if (prediction.score > 1.0) {
                        // Show the spell
                        Toast.makeText(getContext(), prediction.name, Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

        //About the gyroscope
        mSensorManager = (SensorManager)getContext().getSystemService(SENSOR_SERVICE);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (mGyroscope == null){
            Toast.makeText(getContext(),"No gyroscope", Toast.LENGTH_SHORT).show();
            this.getActivity().finish();
        }

        mGyroscopeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                //Returns 0 when phone is horizontal and -9.81/9.81 when vertical
                //Depending on the rotation, we get some sort of directions
                if(sensorEvent.values[1] > -2 && sensorEvent.values[1] < 2){
                    if(!curve.equals("Recte")){
                        curve = "Recte";
                        Toast.makeText(getContext(),curve,Toast.LENGTH_SHORT).show();
                    }
                }else if(sensorEvent.values[1] < -2 && sensorEvent.values[1] > -6){
                    if(!curve.equals("Esquerra Suau")){
                        curve = "Esquerra Suau";
                        Toast.makeText(getContext(),curve,Toast.LENGTH_SHORT).show();
                    }
                }else if(sensorEvent.values[1] < -6 && sensorEvent.values[1] > -10){
                    if(!curve.equals("Esquerra Fort")){
                        curve = "Esquerra Fort";
                        Toast.makeText(getContext(),curve,Toast.LENGTH_SHORT).show();
                    }
                }else if(sensorEvent.values[1] < 6 && sensorEvent.values[1] > 2){
                    if(!curve.equals("Dreta Suau")){
                        curve = "Dreta Suau";
                        Toast.makeText(getContext(),curve,Toast.LENGTH_SHORT).show();
                    }
                }else if(sensorEvent.values[1] < 10 && sensorEvent.values[1] > 6){
                    if(!curve.equals("Dreta Fort")){
                        curve = "Dreta Fort";
                        Toast.makeText(getContext(),curve,Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        //Button Listeners
        btnThrottle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Toast.makeText(getContext(), "Stop", Toast.LENGTH_SHORT).show();
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Toast.makeText(getContext(), "Throttle", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        btnAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAuto.setEnabled(false);
                btnManual.setEnabled(true);
                Toast.makeText(getContext(), "Auto mode", Toast.LENGTH_SHORT).show();
            }
        });

        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAuto.setEnabled(true);
                btnManual.setEnabled(false);
                Toast.makeText(getContext(), "Manual mode", Toast.LENGTH_SHORT).show();
            }
        });

        btnLights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnLights.getText().toString().equals(getContext().getString(R.string.rLigthsOn))){
                    btnLights.setText(R.string.rLigthsOff);
                }else{
                    btnLights.setText(R.string.rLigthsOn);
                }
            }
        });

        btnGearDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Dec gear", Toast.LENGTH_SHORT).show();
            }
        });

        btnGearUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Inc gear", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(mGyroscopeEventListener,mGyroscope,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mGyroscopeEventListener);
    }
}
