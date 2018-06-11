package view.Labyrinth;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.abmcr.robot.R;

import model.Constants;
import viewModel.LabyrinthViewModel;

/**
 * Class that creates the labyrinth view, assign all the visual components and manage to paint the grid
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class LabyrinthFragment extends Fragment{

    private Constants constants = new Constants();

    private ImageView[][] ivCasillas = new ImageView[constants.LABYRINTH_NROWS][constants.LABYRINTH_NCOLUMNS];
    private ImageView[][] ivVerticalWalls = new ImageView[constants.LABYRINTH_NROWS][constants.LABYRINTH_NCOLUMNS + 1];
    private ImageView[][] ivHorizontalWalls = new ImageView[constants.LABYRINTH_NROWS + 1][constants.LABYRINTH_NCOLUMNS];

    private Observer<String> cells, verWalls, horWalls;

    private ImageView ivPlay, ivReplay;

    private LabyrinthViewModel viewModel;

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
        initViewModel();

        return v;
    }

    //Assigning all the visual components
    private void bindViews(View v){
        ivPlay = v.findViewById(R.id.ivPlay);
        ivReplay = v.findViewById(R.id.ivReplay);
        assignCasillas(v);
        assignVerticalWalls(v);
        assignHorizontalWalls(v);

        //Button Listeners
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.sendMessage(Constants.SENDING_PROTOCOL_LABYRINTH);
            }
        });

        ivReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.sendMessage(Constants.SENDING_PROTOCOL_REPLAY_LABYRINTH);
            }
        });
    }

    private void assignCasillas(View v){
        ivCasillas [0][0] = v.findViewById(R.id.c11);
        ivCasillas [0][1] = v.findViewById(R.id.c12);
        ivCasillas [0][2] = v.findViewById(R.id.c13);
        ivCasillas [0][3] = v.findViewById(R.id.c14);
        ivCasillas [0][4] = v.findViewById(R.id.c15);
        ivCasillas [1][0] = v.findViewById(R.id.c21);
        ivCasillas [1][1] = v.findViewById(R.id.c22);
        ivCasillas [1][2] = v.findViewById(R.id.c23);
        ivCasillas [1][3] = v.findViewById(R.id.c24);
        ivCasillas [1][4] = v.findViewById(R.id.c25);
        ivCasillas [2][0] = v.findViewById(R.id.c31);
        ivCasillas [2][1] = v.findViewById(R.id.c32);
        ivCasillas [2][2] = v.findViewById(R.id.c33);
        ivCasillas [2][3] = v.findViewById(R.id.c34);
        ivCasillas [2][4] = v.findViewById(R.id.c35);
        ivCasillas [3][0] = v.findViewById(R.id.c41);
        ivCasillas [3][1] = v.findViewById(R.id.c42);
        ivCasillas [3][2] = v.findViewById(R.id.c43);
        ivCasillas [3][3] = v.findViewById(R.id.c44);
        ivCasillas [3][4] = v.findViewById(R.id.c45);
        ivCasillas [4][0] = v.findViewById(R.id.c51);
        ivCasillas [4][1] = v.findViewById(R.id.c52);
        ivCasillas [4][2] = v.findViewById(R.id.c53);
        ivCasillas [4][3] = v.findViewById(R.id.c54);
        ivCasillas [4][4] = v.findViewById(R.id.c55);
    }

    private void assignVerticalWalls(View v){
        ivVerticalWalls [0][0] = v.findViewById(R.id.pv11);
        ivVerticalWalls [0][1] = v.findViewById(R.id.pv12);
        ivVerticalWalls [0][2] = v.findViewById(R.id.pv13);
        ivVerticalWalls [0][3] = v.findViewById(R.id.pv14);
        ivVerticalWalls [0][4] = v.findViewById(R.id.pv15);
        ivVerticalWalls [0][5] = v.findViewById(R.id.pv16);
        ivVerticalWalls [1][0] = v.findViewById(R.id.pv21);
        ivVerticalWalls [1][1] = v.findViewById(R.id.pv22);
        ivVerticalWalls [1][2] = v.findViewById(R.id.pv23);
        ivVerticalWalls [1][3] = v.findViewById(R.id.pv24);
        ivVerticalWalls [1][4] = v.findViewById(R.id.pv25);
        ivVerticalWalls [1][5] = v.findViewById(R.id.pv26);
        ivVerticalWalls [2][0] = v.findViewById(R.id.pv31);
        ivVerticalWalls [2][1] = v.findViewById(R.id.pv32);
        ivVerticalWalls [2][2] = v.findViewById(R.id.pv33);
        ivVerticalWalls [2][3] = v.findViewById(R.id.pv34);
        ivVerticalWalls [2][4] = v.findViewById(R.id.pv35);
        ivVerticalWalls [2][5] = v.findViewById(R.id.pv36);
        ivVerticalWalls [3][0] = v.findViewById(R.id.pv41);
        ivVerticalWalls [3][1] = v.findViewById(R.id.pv42);
        ivVerticalWalls [3][2] = v.findViewById(R.id.pv43);
        ivVerticalWalls [3][3] = v.findViewById(R.id.pv44);
        ivVerticalWalls [3][4] = v.findViewById(R.id.pv45);
        ivVerticalWalls [3][5] = v.findViewById(R.id.pv46);
        ivVerticalWalls [4][0] = v.findViewById(R.id.pv51);
        ivVerticalWalls [4][1] = v.findViewById(R.id.pv52);
        ivVerticalWalls [4][2] = v.findViewById(R.id.pv53);
        ivVerticalWalls [4][3] = v.findViewById(R.id.pv54);
        ivVerticalWalls [4][4] = v.findViewById(R.id.pv55);
        ivVerticalWalls [4][5] = v.findViewById(R.id.pv56);
    }

    private void assignHorizontalWalls(View v){
        ivHorizontalWalls [0][0] = v.findViewById(R.id.ph11);
        ivHorizontalWalls [0][1] = v.findViewById(R.id.ph12);
        ivHorizontalWalls [0][2] = v.findViewById(R.id.ph13);
        ivHorizontalWalls [0][3] = v.findViewById(R.id.ph14);
        ivHorizontalWalls [0][4] = v.findViewById(R.id.ph15);
        ivHorizontalWalls [1][0] = v.findViewById(R.id.ph21);
        ivHorizontalWalls [1][1] = v.findViewById(R.id.ph22);
        ivHorizontalWalls [1][2] = v.findViewById(R.id.ph23);
        ivHorizontalWalls [1][3] = v.findViewById(R.id.ph24);
        ivHorizontalWalls [1][4] = v.findViewById(R.id.ph25);
        ivHorizontalWalls [2][0] = v.findViewById(R.id.ph31);
        ivHorizontalWalls [2][1] = v.findViewById(R.id.ph32);
        ivHorizontalWalls [2][2] = v.findViewById(R.id.ph33);
        ivHorizontalWalls [2][3] = v.findViewById(R.id.ph34);
        ivHorizontalWalls [2][4] = v.findViewById(R.id.ph35);
        ivHorizontalWalls [3][0] = v.findViewById(R.id.ph41);
        ivHorizontalWalls [3][1] = v.findViewById(R.id.ph42);
        ivHorizontalWalls [3][2] = v.findViewById(R.id.ph43);
        ivHorizontalWalls [3][3] = v.findViewById(R.id.ph44);
        ivHorizontalWalls [3][4] = v.findViewById(R.id.ph45);
        ivHorizontalWalls [4][0] = v.findViewById(R.id.ph51);
        ivHorizontalWalls [4][1] = v.findViewById(R.id.ph52);
        ivHorizontalWalls [4][2] = v.findViewById(R.id.ph53);
        ivHorizontalWalls [4][3] = v.findViewById(R.id.ph54);
        ivHorizontalWalls [4][4] = v.findViewById(R.id.ph55);
        ivHorizontalWalls [5][0] = v.findViewById(R.id.ph61);
        ivHorizontalWalls [5][1] = v.findViewById(R.id.ph62);
        ivHorizontalWalls [5][2] = v.findViewById(R.id.ph63);
        ivHorizontalWalls [5][3] = v.findViewById(R.id.ph64);
        ivHorizontalWalls [5][4] = v.findViewById(R.id.ph65);
    }

    //Void able to paint a cell in some colors depending on the state
    public void paintCell(int row, int column, String state){
        switch (state){
            case Constants.PROTOCOL_STATE_VISITED:
                ivCasillas[row][column].setBackgroundColor(Color.GRAY);
                break;

            case Constants.PROTOCOL_STATE_CURRENT:
                ivCasillas[row][column].setBackgroundResource(R.mipmap.ic_launcher);
                break;

            case Constants.PROTOCOL_STATE_SOLUTION:
                ivCasillas[row][column].setBackgroundColor(Color.GREEN);
                break;

            case Constants.PROTOCOL_STATE_NOT_SOLUTION:
                ivCasillas[row][column].setBackgroundColor(Color.RED);
                break;
        }
    }

    //Void able to paint vertical walls
    public void paintVerticalWall(int row, int column){
        ivVerticalWalls[row][column].setBackgroundColor(getResources().getColor(R.color.colorBlack));
    }

    //Void able to paint horizontal walls
    public void paintHorizontalWall(int row, int column){
        ivHorizontalWalls[row][column].setBackgroundColor(getResources().getColor(R.color.colorBlack));
    }

    private void initViewModel(){

        viewModel = ViewModelProviders.of(this).get(LabyrinthViewModel.class);

        cells = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                String[] subStrings;
                subStrings = msg.split(Constants.PROTOCOL_SPLIT);

                if(subStrings[0].equals(Constants.PROTOCOL_FINISHED)){
                    int i = 1;
                    do{
                        paintCell(Integer.valueOf(subStrings[i+1]), Integer.valueOf(subStrings[i]), Constants.PROTOCOL_STATE_SOLUTION);
                        i += 2;
                    }while(i < subStrings.length);

                }else {
                    //paint previous cells as visited cells
                    paintCell(Integer.valueOf(subStrings[1]), Integer.valueOf(subStrings[0]), Constants.PROTOCOL_STATE_VISITED);

                    //place the current location for the robot
                    paintCell(Integer.valueOf(subStrings[3]), Integer.valueOf(subStrings[2]), Constants.PROTOCOL_STATE_CURRENT);
                }
            }
        };
        viewModel.refreshCells(getContext()).observe(this, cells);

        verWalls = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                String[] subStrings;
                subStrings = msg.split(Constants.PROTOCOL_SPLIT);

                if(subStrings.length > 0){

                    paintVerticalWall(Integer.valueOf(subStrings[1]),Integer.valueOf(subStrings[0]));
                    if(subStrings.length > 2){
                        paintVerticalWall(Integer.valueOf(subStrings[3]),Integer.valueOf(subStrings[2]));
                    }
                }
            }
        };
        viewModel.refreshVerWalls(getContext()).observe(this, verWalls);

        horWalls = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                String[] subStrings;
                subStrings = msg.split(Constants.PROTOCOL_SPLIT);

                if(subStrings.length > 0){
                    paintHorizontalWall(Integer.valueOf(subStrings[1]),Integer.valueOf(subStrings[0]));
                    if(subStrings.length > 2){
                        paintHorizontalWall(Integer.valueOf(subStrings[3]),Integer.valueOf(subStrings[2]));
                    }
                }
            }
        };
        viewModel.refreshHorWalls(getContext()).observe(this, horWalls);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        viewModel.sendMessage(Constants.SENDING_PROTOCOL_BACK_TO_MENU);
        viewModel.stopMessaging(getContext());
        getActivity().finish();
        super.onStop();
    }
}
