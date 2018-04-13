package com.example.abmcr.robot.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abmcr.robot.R;

/**
 * Created by abmcr on 21/03/2018.
 */

public class LabyrinthFragment extends Fragment{

    //private MainViewModel viewModel;
    private ImageView[][] ivCasillas = new ImageView[5][5];
    private ImageView[][] ivVerticalWalls = new ImageView[6][5];
    private ImageView[][] ivHorizontalWalls = new ImageView[5][6];

    private ImageView ivPlay;

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

        playExamples();

        return v;
    }

    private void bindViews(View v){
        ivPlay = v.findViewById(R.id.ivPlay);
        assignCasillas(v);
        assignVerticalWalls(v);
        assignHorizontalWalls(v);

        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Play", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void assignCasillas(View v){
        //TODO inicializar todos
        ivCasillas [0][0] = v.findViewById(R.id.c11);
    }

    private void assignVerticalWalls(View v){
        //TODO inicializar todos
        ivVerticalWalls [5][2] = v.findViewById(R.id.pv11);
    }

    private void assignHorizontalWalls(View v){
        //TODO inicializar todos
        ivHorizontalWalls [3][3] = v.findViewById(R.id.ph11);
    }

    public void paintCell(int row, int column, String state){
        switch (state){
            case "occupied":
                ivCasillas[row][column].setBackgroundColor(Color.RED);
                break;

            case "free":
                ivCasillas[row][column].setBackgroundColor(Color.GREEN);
                break;
        }
    }

    public void paintVerticalWall(int row, int column){
        ivVerticalWalls[row][column].setBackgroundColor(getResources().getColor(R.color.colorWhite));
    }

    public void paintHorizontalWall(int row, int column){
        ivHorizontalWalls[row][column].setBackgroundColor(getResources().getColor(R.color.colorWhite));
    }

    private void playExamples(){
        //Ejemplos de pintar
        paintCell(0,0,"free");
        paintHorizontalWall(3,3);
        paintVerticalWall(5,2);
    }
}
