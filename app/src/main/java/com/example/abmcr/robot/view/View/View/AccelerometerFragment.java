package com.example.abmcr.robot.view.View.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abmcr.robot.R;

/**
 * Class that creates the accelerometer view and assign all the visual components
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class AccelerometerFragment extends Fragment {

    private TextView tvTitle, tvX,tvY,tvZ, tvXValue,tvYValue,tvZValue;

    public static AccelerometerFragment newInstance(){
        return new AccelerometerFragment();
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
        View v = inflater.inflate(R.layout.fragment_accelerometer,container,false);
        bindViews(v);
        return v;
    }

    //Assigning all the visual components
    private void bindViews(View v){
        tvTitle = v.findViewById(R.id.tvTitle);
        tvX = v.findViewById(R.id.tvX);
        tvY = v.findViewById(R.id.tvY);
        tvZ = v.findViewById(R.id.tvZ);
        tvXValue = v.findViewById(R.id.tvXValue);
        tvYValue = v.findViewById(R.id.tvYValue);
        tvZValue = v.findViewById(R.id.tvZValue);

        tvTitle.setText(R.string.aTitle);
        tvX.setText(R.string.aX);
        tvY.setText(R.string.aY);
        tvZ.setText(R.string.aZ);
        tvXValue.setText("3");
        tvYValue.setText("3");
        tvZValue.setText("3");
    }
}
