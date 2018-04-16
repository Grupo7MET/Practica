package com.example.abmcr.robot.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abmcr.robot.R;

/**
 * Created by abmcr on 21/03/2018.
 */

public class AccelerometerFragment extends Fragment {

    private TextView tvTitle, tvX,tvY,tvZ, tvXValue,tvYValue,tvZValue;

    //private MainViewModel viewModel;

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
    }
}
