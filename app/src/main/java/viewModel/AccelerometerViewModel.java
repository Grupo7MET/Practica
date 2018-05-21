package viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import model.Constants;
import model.Repository;

import static java.lang.Integer.valueOf;

/**
 * ViewModel for the accelerometer fragment
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class AccelerometerViewModel extends ViewModel implements Repository.RepositoryCallbacks {

    //our repository
    private Repository repository;

    //observable String variable
    private MutableLiveData<String> iLiveAccelerometerX;
    private MutableLiveData<String> iLiveAccelerometerY;
    private MutableLiveData<String> iLiveAccelerometerZ;

    private static String[] subStrings;

    //Constructor
    public AccelerometerViewModel(){
        repository = new Repository(this);
    }

    /**
     * Keeps the variables updated
     * @param context
     * @return
     */
    public LiveData<String> refreshAccelerometerX (Context context){

        if(iLiveAccelerometerX == null) {
            //init observable variable
            iLiveAccelerometerX = new MutableLiveData<>();
            iLiveAccelerometerX.postValue("");
        }

        //tells the repository to start the service
        repository.startService(context);
        //return the observable variable
        return iLiveAccelerometerX;
    }

    public LiveData<String> refreshAccelerometerY (Context context){

        if(iLiveAccelerometerY == null) {
            //init observable variable
            iLiveAccelerometerY = new MutableLiveData<>();
            iLiveAccelerometerY.postValue("");
        }

        //return the observable variable
        return iLiveAccelerometerY;
    }

    public LiveData<String> refreshAccelerometerZ (Context context){

        if(iLiveAccelerometerZ == null) {
            //init observable variable
            iLiveAccelerometerZ = new MutableLiveData<>();
            iLiveAccelerometerZ.postValue("");
        }

        //return the observable variable
        return iLiveAccelerometerZ;
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
     * Parses the information and sends it to the fragment
     * @param msg new received packet
     */
    @Override
    public void onIncomingMessage (String msg) {

        subStrings = msg.split(Constants.PROTOCOL_SPLIT);

        if (subStrings[0].equals(Constants.SENDING_PROTOCOL_ACCELEROMETER)) {
            iLiveAccelerometerX.postValue(subStrings[1]);
            iLiveAccelerometerY.postValue(subStrings[2]);
            iLiveAccelerometerZ.postValue(subStrings[3]);
        }
    }

    @Override
    public void onServiceStopped() {
        //post value to the view

    }
}

