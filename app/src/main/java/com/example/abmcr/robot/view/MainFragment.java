package com.example.abmcr.robot.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abmcr.robot.R;

/**
 * Created by abmcr on 21/03/2018.
 */

public class MainFragment extends Fragment {

    private Button button1, button2, button3, button4;

    //private MainViewModel viewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        bindViews(v);
        return v;
    }

    private void bindViews(View v) {

        button1 = v.findViewById(R.id.button1);
        button1.setText(R.string.mmButton1);
        button2 = v.findViewById(R.id.button2);
        button2.setText(R.string.mmButton2);
        button3 = v.findViewById(R.id.button3);
        button3.setText(R.string.mmButton3);
        button4 = v.findViewById(R.id.button4);
        button4.setText(R.string.mmButton4);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RemoteActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LabyrinthActivity.class);
                startActivity(intent);

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AccelerometerActivity.class);
                startActivity(intent);

            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CommunicationActivity.class);
                startActivity(intent);
            }
        });
    }
}