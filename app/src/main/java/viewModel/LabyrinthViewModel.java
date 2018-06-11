package viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import model.Communication.Repository;
import model.Constants;

/**
 * Created by Manel on 9/5/18.
 */

public class LabyrinthViewModel extends ViewModel implements Repository.RepositoryCallbacks {

    //our repository
    private Repository repository;

    //observable String variable
    private MutableLiveData<String> sCells, sVerWalls, sHorWalls;

    private Context context;
    private String prev = "0_4";

    private String vertWalls;
    private String horWalls;

    //all messages
    //private static String sMessages;

    //Constructor
    public LabyrinthViewModel(){
        repository = new Repository(this);
    }

    /**
     * Keeps the logs updated
     * @param context
     * @return
     */
    public LiveData<String> refreshCells (Context context){

        if(sCells == null) {
            //init observable variable
            sCells = new MutableLiveData<>();
            sCells.postValue("0_4_0_4");
        }

        this.context = context;

        repository.startService(context);

        return sCells;
    }

    public LiveData<String> refreshVerWalls (Context context){

        if(sVerWalls == null) {
            //init observable variable
            sVerWalls = new MutableLiveData<>();
            //sWalls.postValue("0_4_0_4");
        }

        this.context = context;

        return sVerWalls;
    }

    public LiveData<String> refreshHorWalls (Context context){

        if(sHorWalls == null) {
            //init observable variable
            sHorWalls = new MutableLiveData<>();
            //sWalls.postValue("0_4_0_4");
        }

        this.context = context;

        return sHorWalls;
    }

    public void sendMessage(String msg){
        repository.sendMessage(msg);
    }

    public void stopMessaging(Context context){
        //tell the repository to stop the service
        repository.stopService(context);
    }

    /*-------------------------- Repository Callbacks -----------------------------*/

    /**
     * Continuously refreshes both variables
     * @param msg new received log
     */
    @Override
    public void onIncomingMessage (String msg) {
        //post value to the view

        String[] subStrings;
        subStrings = msg.split(Constants.PROTOCOL_SPLIT);

        if(subStrings[0].equals(Constants.SENDING_PROTOCOL_LABYRINTH)) {

            //Check if it is the end of the labyrinth
            if(subStrings[1].equals(Constants.PROTOCOL_FINISHED)){

                sCells.postValue(msg.substring(2, msg.length()));
            }else{

                //We send a string with: prevColumn_prevRow_currentColumn_currentRow
                sCells.postValue(prev + Constants.PROTOCOL_SPLIT + subStrings[4] + Constants.PROTOCOL_SPLIT + subStrings[5]);

                prev = subStrings[4] + Constants.PROTOCOL_SPLIT + subStrings[5];

                //Depending on the orientation we can paint the walls
                paintWalls(msg);
            }
        }
    }

    void paintWalls(String msg){
        String[] subStrings;
        subStrings = msg.split(Constants.PROTOCOL_SPLIT);

        switch (subStrings[6]){
            case Constants.PROTOCOL_SOUTH:
                vertWalls = "";
                horWalls = "";

                if(subStrings[1].equals("1")){
                    if(!vertWalls.equals("")) {
                        vertWalls = vertWalls + "_";
                    }
                    vertWalls = vertWalls + (Integer.valueOf(subStrings[4])+1) + Constants.PROTOCOL_SPLIT + Integer.valueOf(subStrings[5]);
                }

                if(subStrings[2].equals("1")){
                    if(!horWalls.equals("")) {
                        horWalls = horWalls + "_";
                    }
                    horWalls = horWalls + subStrings[4] + Constants.PROTOCOL_SPLIT + (Integer.valueOf(subStrings[5])+1);
                }

                if(subStrings[3].equals("1")){
                    if(!vertWalls.equals("")) {
                        vertWalls = vertWalls + "_";
                    }
                    vertWalls = vertWalls + subStrings[4] + Constants.PROTOCOL_SPLIT + subStrings[5];
                }

                sVerWalls.postValue(vertWalls);
                sHorWalls.postValue(horWalls);
                break;


            case Constants.PROTOCOL_NORTH:
                vertWalls = "";
                horWalls = "";

                if(subStrings[1].equals("1")){
                    if(!vertWalls.equals("")) {
                        vertWalls = vertWalls + "_";
                    }
                    vertWalls = vertWalls + subStrings[4] + Constants.PROTOCOL_SPLIT + subStrings[5];
                }

                if(subStrings[2].equals("1")){
                    if(!horWalls.equals("")) {
                        horWalls = horWalls + "_";
                    }
                    horWalls = horWalls + subStrings[4] + Constants.PROTOCOL_SPLIT + subStrings[5];
                }

                if(subStrings[3].equals("1")){
                    if(!vertWalls.equals("")) {
                        vertWalls = vertWalls + "_";
                    }
                    vertWalls = vertWalls + (Integer.valueOf(subStrings[4])+1) + Constants.PROTOCOL_SPLIT + Integer.valueOf(subStrings[5]);
                }

                sVerWalls.postValue(vertWalls);
                sHorWalls.postValue(horWalls);
                break;


            case Constants.PROTOCOL_EAST:
                vertWalls = "";
                horWalls = "";

                if(subStrings[1].equals("1")){
                    if(!horWalls.equals("")) {
                        horWalls = horWalls + "_";
                    }
                    horWalls = horWalls + subStrings[4] + Constants.PROTOCOL_SPLIT + subStrings[5];
                }

                if(subStrings[2].equals("1")){
                    if(!vertWalls.equals("")) {
                        vertWalls = vertWalls + "_";
                    }
                    vertWalls = vertWalls + (Integer.valueOf(subStrings[4])+1) + Constants.PROTOCOL_SPLIT + subStrings[5];
                }

                if(subStrings[3].equals("1")){
                    if(!horWalls.equals("")) {
                        horWalls = horWalls + "_";
                    }
                    horWalls = horWalls + subStrings[4] + Constants.PROTOCOL_SPLIT + (Integer.valueOf(subStrings[5])+1);
                }

                sVerWalls.postValue(vertWalls);
                sHorWalls.postValue(horWalls);
                break;


            case Constants.PROTOCOL_WEST:
                vertWalls = "";
                horWalls = "";

                if(subStrings[1].equals("1")){
                    if(!horWalls.equals("")) {
                        horWalls = horWalls + "_";
                    }
                    horWalls = horWalls + subStrings[4] + Constants.PROTOCOL_SPLIT + (Integer.valueOf(subStrings[5])+1);
                }

                if(subStrings[2].equals("1")){
                    if(!vertWalls.equals("")) {
                        vertWalls = vertWalls + "_";
                    }
                    vertWalls = vertWalls + subStrings[4] + Constants.PROTOCOL_SPLIT + subStrings[5];
                }

                if(subStrings[3].equals("1")){
                    if(!horWalls.equals("")) {
                        horWalls = horWalls + "_";
                    }
                    horWalls = horWalls + subStrings[4] + Constants.PROTOCOL_SPLIT + subStrings[5];
                }

                sVerWalls.postValue(vertWalls);
                sHorWalls.postValue(horWalls);
                break;
        }
    }

    @Override
    public void onServiceStopped() {
        //post value to the view

    }
}
