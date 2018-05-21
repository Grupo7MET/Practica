package viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import model.Constants;
import model.Repository;


/**
 * ViewModel for the Remote fragment
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class RemoteViewModel extends ViewModel implements Repository.RepositoryCallbacks {

    //our repository
    private Repository repository;

    //observable String variable
    private MutableLiveData<String> sLiveTemperature;
    private MutableLiveData<Integer> iDanger; //1 if manual, 0 if auto

    private static String sMessages;

    private static String[] subStrings;


    //Constructor
    public RemoteViewModel(){
        repository = new Repository(this);
    }

    /**
     * Keeps the variables updated
     * @param context
     * @return
     */

    /*public void startCommunication(Context context){
        //tells the repository to start the service

    }*/

    public LiveData<String> refreshTemperature (Context context){

        if(sLiveTemperature == null) {
            //init observable variable
            sLiveTemperature = new MutableLiveData<>();
            sLiveTemperature.postValue("Temperature");
        }

        repository.startService(context);
        //returns the observable variable
        return sLiveTemperature;
    }

    public LiveData<Integer> refreshDanger (Context context){

        if(iDanger == null) {
            //init observable variable
            iDanger = new MutableLiveData<>();
            iDanger.postValue(0);
        }

        //returns the observable variable
        return iDanger;
    }

    public void stopMessaging(Context context){
        //tell the repository to stop the service
        repository.stopService(context);
    }

    public void sendMessage(String message){
        repository.sendMessage(message);
    }

    /*-------------------------- Repository Callbacks -----------------------------*/

    /**
     * Continuously refreshes both variables
     * @param msg new received log
     */
    @Override
    public void onIncomingMessage (String msg) {
        //Assign value for the temperature only if it is for me
        subStrings = msg.split(Constants.PROTOCOL_SPLIT);


        if(subStrings.length > 1) {
            if (subStrings[0].equals(Constants.PROTOCOL_REMOTE)) {
                switch (subStrings[1]) {
                    case Constants.PROTOCOL_REMOTE_TEMPERATURE:
                        sLiveTemperature.postValue(subStrings[2]);
                        break;

                    case Constants.PROTOCOL_REMOTE_DANGER:
                        iDanger.postValue(Integer.valueOf(subStrings[2]));
                        break;

                }
            }
        }
    }

    @Override
    public void onServiceStopped() {
        //post value to the view

    }
}