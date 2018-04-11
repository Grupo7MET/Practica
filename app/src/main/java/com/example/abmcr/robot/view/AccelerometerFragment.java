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
    private TextView textView;

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
        textView = v.findViewById(R.id.textview);
        textView.setText("RETO DEL ACELERÓMETRO");
    }
}
