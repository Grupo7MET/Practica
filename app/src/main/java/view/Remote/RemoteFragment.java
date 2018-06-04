package view.Remote;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import model.Communication.RemotePacket;
import viewModel.RemoteViewModel;
import static android.content.Context.SENSOR_SERVICE;
import static java.lang.Math.abs;

/**
 * Class that creates the view and assign all the visual components.
 * Create the button listeners, manage the pattern detection and the gyroscope
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

/**
 * USED PROTOCOL:
 * At starting the program, the robot already knows that we are in the menu mode.
 * When anything changes, we send a packet with the proper PROTOCOL_MODE + a number. i.e. r0
 * When we are in a remote mode, we send packets when something significant changes (just if we can move with a
 * gear != 0, or if we are in manual mode.
 * Both the lights and manual/auto mode are changed just when the robot answers with an acknowledge packet.
 * It is always sent a PROTOCOL_MOVEMENT + a number. This number is the current gear.
 */

public class RemoteFragment extends Fragment {

    private RemoteViewModel viewModel;

    /**
     * Visual part to link the fragment with the View
     */
    private static TextView tvTitle, tvTemp, tvVelocity, tvVelocityValue;
    private ImageView ivDangerUS, ivDangerBumper1, ivDangerBumper2;
    private Button btnManual, btnAuto, btnLights, btnThrottle, btnGearDown, btnGearUp;

    /**
     * Variables containing the current status
     */
    private int gear;
    private String curve;
    private boolean manual;
    private String lights;
    private float readValue;
    private boolean moving;

    /**
     * Observer variables that will be received through UDP packets
     */
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
    private Observer<Boolean> lightsOn;
    private Observer<Boolean> liveManual;

    /**
     * Other resources used in this Fragment
     */
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
        manual = false; //By this way, we initialize when postValue is done in the View Model
        lights = constants.PROTOCOL_LIGHTS_ON; //By this way, we initialize when postValue is done in the View Model
        initViewModel();
        bindViews(v);
        moving = false;
        gear = constants.GEAR_INIT;
        curve = "";
        viewModel.sendMessage(constants.SENDING_PROTOCOL_REMOTE + Integer.toString(convertGear(gear)));
        return v;
    }

    /**
     * Assigning all the visual components
     */
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
                        switch (prediction.name){
                            case Constants.PREDICTION_CIRCLE:
                                viewModel.sendMessage(constants.SENDING_PROTOCOL_MOVEMENT_CIRCLE + constants.SENDING_PROTOCOL_VELOCITY_MEDIUM);
                                break;

                            case Constants.PREDICTION_SQUARE:
                                viewModel.sendMessage(constants.SENDING_PROTOCOL_MOVEMENT_SQUARE + constants.SENDING_PROTOCOL_VELOCITY_MEDIUM);
                                break;

                            case Constants.PREDICTION_TRIANGLE:
                                viewModel.sendMessage(constants.SENDING_PROTOCOL_MOVEMENT_TRIANGLE + constants.SENDING_PROTOCOL_VELOCITY_MEDIUM);
                                break;
                        }
                    }
                }
            }
        });


        /**
         * About the gyroscope
         */
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
                        if (!curve.equals(constants.SENDING_PROTOCOL_MOVEMENT_FORWARD)) {
                            curve = constants.SENDING_PROTOCOL_MOVEMENT_FORWARD;
                            if (moving) {
                                viewModel.sendMessage(constants.SENDING_PROTOCOL_MOVEMENT_FORWARD + Integer.toString(convertGear(gear)));
                            }
                        }
                    } else if (readValue < -constants.GYRO_MAX_FORWARD && readValue > -constants.GYRO_MAX_SOFT) {
                        if (!curve.equals(constants.SENDING_PROTOCOL_MOVEMENT_SOFT_LEFT)) {
                            curve = constants.SENDING_PROTOCOL_MOVEMENT_SOFT_LEFT;
                            if (moving) {
                                viewModel.sendMessage(constants.SENDING_PROTOCOL_MOVEMENT_SOFT_LEFT + Integer.toString(convertGear(gear)));
                            }
                        }
                    } else if (readValue < -constants.GYRO_MAX_SOFT && readValue > -constants.GYRO_MAX_HARD) {
                        if (!curve.equals(constants.SENDING_PROTOCOL_MOVEMENT_HARD_LEFT)) {
                            curve = constants.SENDING_PROTOCOL_MOVEMENT_HARD_LEFT;
                            if (moving) {
                                viewModel.sendMessage(constants.SENDING_PROTOCOL_MOVEMENT_HARD_LEFT + Integer.toString(convertGear(gear)));
                            }
                        }
                    } else if (readValue < constants.GYRO_MAX_SOFT && readValue > constants.GYRO_MAX_FORWARD) {
                        if (!curve.equals(constants.SENDING_PROTOCOL_MOVEMENT_SOFT_RIGHT)) {
                            curve = constants.SENDING_PROTOCOL_MOVEMENT_SOFT_RIGHT;
                            if (moving) {
                                viewModel.sendMessage(constants.SENDING_PROTOCOL_MOVEMENT_SOFT_RIGHT + Integer.toString(convertGear(gear)));
                            }
                        }
                    } else if (readValue < constants.GYRO_MAX_HARD && readValue > constants.GYRO_MAX_SOFT) {
                        if (!curve.equals(constants.SENDING_PROTOCOL_MOVEMENT_HARD_RIGHT)) {
                            curve = constants.SENDING_PROTOCOL_MOVEMENT_HARD_RIGHT;
                            if (moving) {
                                viewModel.sendMessage(constants.SENDING_PROTOCOL_MOVEMENT_HARD_RIGHT + Integer.toString(convertGear(gear)));
                            }
                        }
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        /**
         * Button listeners
         */
        btnThrottle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (manual) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        moving = false;
                        viewModel.sendMessage(constants.SENDING_PROTOCOL_MOVEMENT_FORWARD + Integer.toString(convertGear(gear)));
                    }
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        moving = true;
                        if (gear != 0) viewModel.sendMessage(constants.SENDING_PROTOCOL_MOVEMENT_FORWARD + Integer.toString(convertGear(gear)));
                    }
                }
                return false;
            }
        });

        btnAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.sendMessage(constants.SENDING_PROTOCOL_AUTOMATIC + constants.SENDING_PROTOCOL_VELOCITY_MEDIUM);
            }
        });

        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.sendMessage(constants.SENDING_PROTOCOL_MANUAL + Integer.toString(convertGear(gear)));
            }
        });

        btnLights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.sendMessage(constants.SENDING_PROTOCOL_FRONT_LIGHTS + Integer.toString(convertGear(gear)));
            }
        });

        btnGearDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gear > -constants.GEAR_MAX && manual) {
                    gear--;
                    if (moving) viewModel.sendMessage(curve + Integer.toString(convertGear(gear)));
                    if (gear < 0){
                        tvVelocityValue.setText("-" + Integer.toString(constants.VELOCITY[abs(gear)]) + " km/h");
                    }else{
                        tvVelocityValue.setText(Integer.toString(constants.VELOCITY[abs(gear)]) + " km/h");
                    }
                }
            }
        });

        btnGearUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gear < constants.GEAR_MAX && manual) {
                    gear++;
                    if (moving) viewModel.sendMessage(curve + Integer.toString(convertGear(gear)));
                    tvVelocityValue.setText(Integer.toString(constants.VELOCITY[abs(gear)]) + " km/h");
                }
            }
        });

    }

    /**
     * Dealing with all the Observer variables
     */
    private void initViewModel(){

        viewModel = ViewModelProviders.of(this).get(RemoteViewModel.class);

        sDegrees = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                tvTemp.setText(msg + "ºC");
            }
        };
        viewModel.refreshTemperature(getContext()).observe(this, sDegrees);

        lightsOn = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean msg) {
                if(lights.equals(constants.PROTOCOL_LIGHTS_OFF)){
                    btnLights.setText(R.string.rLigthsOff);
                    lights = constants.PROTOCOL_LIGHTS_ON;
                }else{
                    btnLights.setText(R.string.rLigthsOn);
                    lights = constants.PROTOCOL_LIGHTS_OFF;
                }
            }
        };
        viewModel.refreshLights(getContext()).observe(this, lightsOn);

        liveManual = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean msg) {
                if (manual) {
                    // Automatic mode starts
                    btnAuto.setEnabled(false);
                    btnManual.setEnabled(true);
                    manual = false;

                    gear = Integer.valueOf(constants.SENDING_PROTOCOL_VELOCITY_MEDIUM);
                    tvVelocityValue.setText(Integer.toString(constants.VELOCITY[abs(gear)]) + " km/h");

                } else {
                    // Manual mode starts
                    btnAuto.setEnabled(true);
                    btnManual.setEnabled(false);
                    manual = true;

                    gear = 0;
                    tvVelocityValue.setText(Integer.toString(constants.VELOCITY[abs(gear)]) + " km/h");
                    curve = constants.SENDING_PROTOCOL_MOVEMENT_FORWARD;
                }

            }
        };
        viewModel.refreshManual(getContext()).observe(this, liveManual);

        /**
         * Packets are analyzed and sends a packet to the robot if it is needed to change anything
         * (if any packet was lost)
         */
        packet = new Observer<RemotePacket>() {
            @Override
            public void onChanged(@Nullable RemotePacket msg) {

                if(!curve.equals(msg.getMovement()) && moving){
                    viewModel.sendMessage(curve + Integer.toString(convertGear(gear)));
                }

                if(convertGear(gear) != Integer.valueOf(msg.getVelocity())){
                    viewModel.sendMessage(curve + Integer.toString(convertGear(gear)));
                }

                if(!lights.equals(msg.getLights())){
                    viewModel.sendMessage(constants.SENDING_PROTOCOL_FRONT_LIGHTS + Integer.toString(convertGear(gear)));
                }

                if(manual != msg.getManual()){
                    if(manual){
                        viewModel.sendMessage(constants.SENDING_PROTOCOL_MANUAL + Integer.toString(convertGear(gear)));
                    }else{
                        viewModel.sendMessage(constants.SENDING_PROTOCOL_AUTOMATIC + Integer.toString(convertGear(gear)));
                    }
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

                if (aux >= constants.DANGER_BUMPER2) {
                    ivDangerBumper2.setVisibility(View.VISIBLE);
                    aux -= constants.DANGER_BUMPER2;
                }else{
                    ivDangerBumper2.setVisibility(View.INVISIBLE);
                }

                if (aux >= constants.DANGER_BUMPER1) {
                    ivDangerBumper1.setVisibility(View.VISIBLE);
                    aux -= constants.DANGER_BUMPER1;
                }else{
                    ivDangerBumper1.setVisibility(View.INVISIBLE);
                }
            }
        };
        viewModel.refreshDanger(getContext()).observe(this, danger);
    }

    /**
     * @param g gives the current gear between -3 and 3
     * @return the same gear from 0 to 3 and converts -1 -> 4, -2 -> 5, -3 -> 6
     */
    public int convertGear(int g){
        int a = 0;

        if(!moving) a = 0;
        else if(g >= 0) a = g;
        else if(g == -1) a = constants.SENDING_PROTOCOL_VELOCITY_MINUS1;
        else if (g == -2) a = constants.SENDING_PROTOCOL_VELOCITY_MINUS2;
        else if (g == -3) a = constants.SENDING_PROTOCOL_VELOCITY_MINUS3;

        return a;
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

    /**
     * Before exit, send a packet saying that we quit
     * The communication is closed
     */
    @Override
    public void onStop() {
        viewModel.sendMessage(constants.SENDING_PROTOCOL_BACK_TO_MENU + Integer.toString(convertGear(gear)));
        viewModel.stopMessaging(getContext());
        getActivity().finish();
        super.onStop();
    }
}
