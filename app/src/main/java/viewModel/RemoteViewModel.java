package viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import model.Constants;
import model.RemotePacket;
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

    private RemotePacket packet;
    private MutableLiveData<RemotePacket> livePacket;


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

    public LiveData<RemotePacket> refreshData (Context context){

        if(livePacket == null) {
            //init observable variable
            livePacket = new MutableLiveData<>();
        }

        //returns the observable variable
        return livePacket;
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

        if(subStrings[0].equals(Constants.SENDING_PROTOCOL_REMOTE)) {
            parseInfo(msg);
            checkInfo(packet);
        }
    }

    public void parseInfo(String info){
        subStrings = info.split(Constants.PROTOCOL_SPLIT);

        if(subStrings[0].equals(Constants.SENDING_PROTOCOL_REMOTE)){
            packet = new RemotePacket(subStrings[1],subStrings[2],subStrings[3],subStrings[4],subStrings[5],subStrings[6]);
        }else{
            sendMessage(Constants.SENDING_PROTOCOL_REMOTE);
        }

    }

    public void checkInfo(RemotePacket packet){
        sLiveTemperature.postValue(packet.getTemperature());
        iDanger.postValue(Integer.valueOf(packet.getDanger()));
        livePacket.postValue(packet);
    }

    @Override
    public void onServiceStopped() {
        //post value to the view

    }
}