package view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.abmcr.robot.R;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;
import model.Constants;
import model.RemotePacket;
import viewModel.RemoteViewModel;
import static android.content.Context.SENSOR_SERVICE;
import static java.lang.Math.abs;

/**
 * Class that creates the view and assign all the visual components.
 * Create the button listeners, manage the pattern detection and the gyroscope
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class RemoteFragment extends Fragment {

    private RemoteViewModel viewModel;

    private static TextView tvTitle, tvTemp, tvVelocity, tvVelocityValue;
    private ImageView ivDangerUS, ivDangerBumper1, ivDangerBumper2;
    private Button btnManual, btnAuto, btnLights, btnThrottle, btnGearDown, btnGearUp;
    private int gear;
    private String curve;
    private boolean manual;
    private String lights;
    private float readValue;
    /**
     * danger represents in 3 bits the possible dangers
     * Weights:
     * 1 for right bumper
     * 2 for left bumper
     * 4 for Ultrasound
     * The sum of them is the result for this number
     */
    private Observer<Integer> danger;
    private Observer<String> sDegrees; //a string with the degrees received from the Arduino
    private Observer<RemotePacket> packet;
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
        initViewModel();
        bindViews(v);
        gear = constants.GEAR_INIT;
        lights = constants.PROTOCOL_LIGHTS_OFF;
        manual = true;
        curve = "";
        viewModel.sendMessage(constants.PROTOCOL_REMOTE);
        return v;
    }

    //Assigning all the visual components
    private void bindViews(View v){

        tvTitle = v.findViewById(R.id.tvTitle);
        tvTemp = v.findViewById(R.id.tvTemp);
        tvVelocity = v.findViewById(R.id.tvVelocity);
        tvVelocityValue = v.findViewById(R.id.tvVelocityValue);
        ivDangerUS = v.findViewById(R.id.ivDangerUS);
        ivDangerBumper1 = v.findViewById(R.id.ivDangerBumper1);
        ivDangerBumper2 = v.findViewById(R.id.ivDangerBumper2);
        btnAuto = v.findViewById(R.id.btnAuto);
        btnManual = v.findViewById(R.id.btnMan);
        btnLights = v.findViewById(R.id.btnLights);
        btnThrottle = v.findViewById(R.id.btnThrottle);
        btnGearDown = v.findViewById(R.id.btnGearDown);
        btnGearUp = v.findViewById(R.id.btnGearUp);
        mLibrary = GestureLibraries.fromRawResource(getContext(), R.raw.gestures);

        btnAuto.setEnabled(true);
        btnManual.setEnabled(false);

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
        tvVelocityValue.setText(R.string.rVelocityValue);


        gestures.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {

            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                ArrayList<Prediction> predictions = mLibrary.recognize(gesture);

                // We want at least one prediction
                if (predictions.size() > 0) {
                    Prediction prediction = predictions.get(0);
                    // We want at least some confidence in the result
                    if (prediction.score > constants.PREDICTOR_MIN_SCORE) {
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

                readValue = sensorEvent.values[1];
                if(manual) {
                    //Returns 0 when phone is horizontal and -9.81/9.81 when vertical
                    //Depending on the rotation, we get some sort of directions
                    if (readValue > -constants.GYRO_MAX_FORWARD && readValue < constants.GYRO_MAX_FORWARD) {
                        if (!curve.equals(constants.PROTOCOL_MOVEMENT_FORWARD)) {
                            curve = constants.PROTOCOL_MOVEMENT_FORWARD;
                            viewModel.sendMessage(curve);
                        }
                    } else if (readValue < -constants.GYRO_MAX_FORWARD && readValue > -constants.GYRO_MAX_SOFT) {
                        if (!curve.equals(constants.PROTOCOL_MOVEMENT_SOFT_LEFT)) {
                            curve = constants.PROTOCOL_MOVEMENT_SOFT_LEFT;
                            viewModel.sendMessage(curve);
                        }
                    } else if (readValue < -constants.GYRO_MAX_SOFT && readValue > -constants.GYRO_MAX_HARD) {
                        if (!curve.equals(constants.PROTOCOL_MOVEMENT_HARD_LEFT)) {
                            curve = constants.PROTOCOL_MOVEMENT_HARD_LEFT;
                            viewModel.sendMessage(curve);
                        }
                    } else if (readValue < constants.GYRO_MAX_SOFT && readValue > constants.GYRO_MAX_FORWARD) {
                        if (!curve.equals(constants.PROTOCOL_MOVEMENT_SOFT_RIGHT)) {
                            curve = constants.PROTOCOL_MOVEMENT_SOFT_RIGHT;
                            viewModel.sendMessage(curve);
                        }
                    } else if (readValue < constants.GYRO_MAX_HARD && readValue > constants.GYRO_MAX_SOFT) {
                        if (!curve.equals(constants.PROTOCOL_MOVEMENT_HARD_RIGHT)) {
                            curve = constants.PROTOCOL_MOVEMENT_HARD_RIGHT;
                            viewModel.sendMessage(curve);
                        }
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
                if (manual) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        viewModel.sendMessage(constants.PROTOCOL_BRAKE);
                    }
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        viewModel.sendMessage(constants.PROTOCOL_THROTTLE);
                    }
                }
                return false;
            }
        });

        btnAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAuto.setEnabled(false);
                btnManual.setEnabled(true);
                manual = false;
                viewModel.sendMessage(constants.PROTOCOL_AUTOMATIC);
            }
        });

        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAuto.setEnabled(true);
                btnManual.setEnabled(false);
                manual = true;
                viewModel.sendMessage(constants.PROTOCOL_MANUAL);
            }
        });

        btnLights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnLights.getText().toString().equals(getContext().getString(R.string.rLigthsOn))){
                    viewModel.sendMessage(constants.PROTOCOL_LIGHTS_ON);
                    btnLights.setText(R.string.rLigthsOff);
                    lights = constants.PROTOCOL_LIGHTS_ON;
                }else{
                    viewModel.sendMessage(constants.PROTOCOL_LIGHTS_OFF);
                    btnLights.setText(R.string.rLigthsOn);
                    lights = constants.PROTOCOL_LIGHTS_OFF;
                }
            }
        });

        btnGearDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gear > -constants.GEAR_MAX && manual) {
                    gear--;
                    viewModel.sendMessage(constants.PROTOCOL_GEAR_CHANGE + Integer.toString(gear));
                    tvVelocityValue.setText(Integer.toString(constants.VELOCITY[abs(gear)]) + " km/h");
                }
            }
        });

        btnGearUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gear < constants.GEAR_MAX && manual) {
                    gear++;
                    viewModel.sendMessage(constants.PROTOCOL_GEAR_CHANGE + Integer.toString(gear));
                    tvVelocityValue.setText(Integer.toString(constants.VELOCITY[abs(gear)]) + " km/h");
                }
            }
        });

    }

    //Assign ViewModel to this fragment & the observer variable
    private void initViewModel(){

        viewModel = ViewModelProviders.of(this).get(RemoteViewModel.class);

        sDegrees = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                tvTemp.setText(msg + "ºC");
            }
        };
        viewModel.refreshTemperature(getContext()).observe(this, sDegrees);

        packet = new Observer<RemotePacket>() {
            @Override
            public void onChanged(@Nullable RemotePacket msg) {

                Log.e("packet",msg.getMovement()+"_"+msg.getVelocity()+"_"+msg.getLights()+"_"+msg.getManual());
                Log.e("packet",curve+"_"+constants.VELOCITY[abs(gear)]+"_"+lights+"_"+manual);
                if(!curve.equals(msg.getMovement())){
                    viewModel.sendMessage(curve);
                    Log.e("error","curve");
                }

                if(gear != Integer.valueOf(msg.getVelocity())){
                    viewModel.sendMessage(constants.PROTOCOL_GEAR_CHANGE + Integer.toString(gear));
                    Log.e("error","gear");
                }

                if(!lights.equals(msg.getLights())){
                    if(lights.equals(constants.PROTOCOL_LIGHTS_ON)) {
                        viewModel.sendMessage(constants.PROTOCOL_LIGHTS_ON);
                    }else{
                        viewModel.sendMessage(constants.PROTOCOL_LIGHTS_OFF);
                    }
                    Log.e("error","lights");
                }

                if(manual != msg.getManual()){
                    if(manual){
                        viewModel.sendMessage(constants.PROTOCOL_MANUAL);
                    }else{
                        viewModel.sendMessage(constants.PROTOCOL_AUTOMATIC);
                    }
                    Log.e("error","manual");
                }
            }
        };
        viewModel.refreshData(getContext()).observe(this, packet);


        danger = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer msg) {
                int aux = msg;
                if (aux >= constants.DANGER_US) {
                    ivDangerUS.setVisibility(View.VISIBLE);
                    aux -= constants.DANGER_US;
                }else{
                    ivDangerUS.setVisibility(View.INVISIBLE);
                }

                if (aux >= constants.DANGER_BUMPER1) {
                    ivDangerBumper1.setVisibility(View.VISIBLE);
                    aux -= constants.DANGER_BUMPER1;
                }else{
                    ivDangerBumper1.setVisibility(View.INVISIBLE);
                }

                if (aux >= constants.DANGER_BUMPER2) {
                    ivDangerBumper2.setVisibility(View.VISIBLE);
                    aux -= constants.DANGER_BUMPER2;
                }else{
                    ivDangerBumper2.setVisibility(View.INVISIBLE);
                }
            }
        };
        viewModel.refreshDanger(getContext()).observe(this, danger);
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

    @Override
    public void onStop() {
        viewModel.stopMessaging(getContext());
        viewModel.sendMessage(constants.MODE_MENU);
        getActivity().finish();
        super.onStop();
    }
}
