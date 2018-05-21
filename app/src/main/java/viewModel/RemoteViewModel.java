package viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

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

    /**
     * Live data shared with the fragment
     */
    private MutableLiveData<String> sLiveTemperature;
    private MutableLiveData<Integer> iDanger;
    private MutableLiveData<Boolean> liveLightsOn;
    private MutableLiveData<Boolean> liveManual;
    private MutableLiveData<RemotePacket> livePacket;

    private static String[] subStrings;

    /**
     * Remote Packet variable used to parse the information
     */
    private RemotePacket packet;


    //Constructor
    public RemoteViewModel(){
        repository = new Repository(this);
    }

    /**
     * All liveData variables are initialized and repository starts connection
     * @param context
     * @return the liveData that needs to be refreshing
     */
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

    public LiveData<Boolean> refreshLights (Context context){

        if(liveLightsOn == null) {
            //init observable variable
            liveLightsOn = new MutableLiveData<>();
            liveLightsOn.postValue(false);
        }

        //returns the observable variable
        return liveLightsOn;
    }

    public LiveData<Boolean> refreshManual (Context context){

        if(liveManual == null) {
            //init observable variable
            liveManual = new MutableLiveData<>();
            liveManual.postValue(true);
        }

        //returns the observable variable
        return liveManual;
    }

    /**
     * tells the repository to stop the service
     * @param context
     */
    public void stopMessaging(Context context){
        repository.stopService(context);
    }

    /**
     * Pushes the message to be sent
     * @param message is the message to send
     */
    public void sendMessage(String message){
        repository.sendMessage(message);
    }

    /*-------------------------- Repository Callbacks -----------------------------*/

    /**
     * Method that selects who needs to receive information
     * @param msg new received packet
     */
    @Override
    public void onIncomingMessage (String msg) {

        subStrings = msg.split(Constants.PROTOCOL_SPLIT);

        if(subStrings[0].equals(Constants.SENDING_PROTOCOL_REMOTE)) {
            if(subStrings[1].equals(Constants.PROTOCOL_LIGHTS_CHANGED)){
                liveLightsOn.postValue(!liveLightsOn.getValue());
            }else if (subStrings[1].equals(Constants.PROTOCOL_MANUAL_CHANGED)){
                liveManual.postValue(!liveManual.getValue());
            }else {
                parseInfo(msg);
                checkInfo(packet);
            }
        }
    }

    @Override
    public void onServiceStopped() {
        //post value to the view

    }

    /**
     * Creates a data structure with all the fields saved
     * @param info is the full packet
     */
    public void parseInfo(String info){

        subStrings = info.split(Constants.PROTOCOL_SPLIT);

        packet = new RemotePacket(subStrings[1],subStrings[2],subStrings[3],subStrings[4],subStrings[5],subStrings[6]);
    }

    /**
     * Refreshes the fields on the fragment
     * @param packet is the data structure with all the data
     */
    public void checkInfo(RemotePacket packet){
        sLiveTemperature.postValue(packet.getTemperature());
        iDanger.postValue(Integer.valueOf(packet.getDanger()));
        livePacket.postValue(packet);
    }
}