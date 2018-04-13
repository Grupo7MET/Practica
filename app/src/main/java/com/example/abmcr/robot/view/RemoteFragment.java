package com.example.abmcr.robot.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abmcr.robot.R;

import Model.Constants;

/**
 * Created by abmcr on 21/03/2018.
 */

public class RemoteFragment extends Fragment {

    private TextView tvTitle, tvTemp, tvVelocity, tvVelocityValue;
    private ImageView ivDanger;
    private Button btnManual, btnAuto, btnLights, btnThrottle, btnGearDown, btnGearUp;
    int gear;

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
        return v;
    }

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

        tvTitle.setText(R.string.rTitle);
        tvTemp.setText("17 "+ R.string.rTemp);
        tvVelocity.setText(R.string.rVelocity);
        btnAuto.setText(R.string.rAuto);
        btnManual.setText(R.string.rManual);
        btnLights.setText(R.string.rLigths);
        btnThrottle.setText(R.string.rThrottle);
        btnGearDown.setText(R.string.rGearDown);
        btnGearUp.setText(R.string.rGearUp);

        //TODO els listeners dels buttons
    }
}
