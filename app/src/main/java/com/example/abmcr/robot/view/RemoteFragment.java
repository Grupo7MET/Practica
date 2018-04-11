package com.example.abmcr.robot.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abmcr.robot.R;

/**
 * Created by abmcr on 21/03/2018.
 */

public class RemoteFragment extends Fragment {
    private TextView textView;

    private GestureDetectorCompat mDetector;

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
        return v;
    }

    private void bindViews(View v){
        textView = v.findViewById(R.id.textview);
        textView.setText("CONTROL REMOTO");

        /*
        View myView = (View) v.findViewById(R.id.my_view);

        myView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getPointerCount()){
                    case 0:
                        Toast.makeText(getContext(), "zero", Toast.LENGTH_LONG).show();
                        break;

                    case 1:
                        Toast.makeText(getContext(), "one", Toast.LENGTH_LONG).show();
                        break;

                    case 2:
                        Toast.makeText(getContext(), "two", Toast.LENGTH_LONG).show();
                        break;

                    case 3:
                        Toast.makeText(getContext(), "three", Toast.LENGTH_LONG).show();
                        break;
                }
                //Toast.makeText(getContext(), "touch", Toast.LENGTH_LONG).show();
                return true;
            }
        });
        */
    }
}
