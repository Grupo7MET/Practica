package view.Labyrinth;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.abmcr.robot.R;

import model.Constants;

/**
 * Class that creates the labyrinth view, assign all the visual components and manage to paint the grid
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class LabyrinthFragment extends Fragment{

    private Constants constants = new Constants();

    private ImageView[][] ivCasillas = new ImageView[constants.LABYRINTH_NROWS][constants.LABYRINTH_NCOLUMNS];
    private ImageView[][] ivVerticalWalls = new ImageView[constants.LABYRINTH_NROWS][constants.LABYRINTH_NCOLUMNS + 1];
    private ImageView[][] ivHorizontalWalls = new ImageView[constants.LABYRINTH_NROWS + 1][constants.LABYRINTH_NCOLUMNS];

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

    //Assigning all the visual components
    private void bindViews(View v){
        ivPlay = v.findViewById(R.id.ivPlay);
        assignCasillas(v);
        assignVerticalWalls(v);
        assignHorizontalWalls(v);

        //Button Listeners
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Play", Toast.LENGTH_SHORT).show();
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
            case "occupied":
                ivCasillas[row][column].setBackgroundColor(Color.RED);
                break;

            case "free":
                ivCasillas[row][column].setBackgroundColor(Color.GREEN);
                break;
        }
    }

    //Void able to paint vertical walls
    public void paintVerticalWall(int row, int column){
        ivVerticalWalls[row][column].setBackgroundColor(getResources().getColor(R.color.colorBlack));
    }

    //Void able to paint horizontal walls
    public void paintHorizontalWall(int row, int column){
        ivHorizontalWalls[row][column].setBackgroundColor(getResources().getColor(R.color.colorWhite));
    }

    //Example void showing we are able to paint
    private void playExamples(){
        paintCell(0,0,"free");
        paintHorizontalWall(3,3);
        paintVerticalWall(2,2);
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
        //viewModel.stopMessaging(getContext());
        getActivity().finish();
        super.onStop();
    }
}
