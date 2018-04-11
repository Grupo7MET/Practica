package com.example.abmcr.robot.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abmcr.robot.R;

/**
 * Created by abmcr on 21/03/2018.
 */

public class LabyrinthFragment extends Fragment{

    private TextView textView;

    //private MainViewModel viewModel;

    public static LabyrinthFragment newInstance(){
        return new LabyrinthFragment();
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
        View v = inflater.inflate(R.layout.fragment_labyrinth,container,false);
        bindViews(v);
        return v;
    }

    private void bindViews(View v){
        textView = v.findViewById(R.id.textview);
        textView.setText("SOLUCIÓN DEL LABERINTO");

        View myView = (View) v.findViewById(R.id.my_view);
        myView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){

                }
                return true;
            }
        });
    }
}
