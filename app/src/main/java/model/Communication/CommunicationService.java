package model.Communication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.net.DatagramSocket;
import java.net.SocketException;

import model.Constants;

import static model.Communication.CommunicationAsyncTask.initCommunications;
import static model.Communication.CommunicationAsyncTask.stopCommunications;

/**
 * Class that allows the communication with the service
 * Implements functions that start/stop the communication, prepare data to send and
 * allow to push values to the repository
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class CommunicationService extends Service {

    public static Boolean communicationInProgress = false;
    static DatagramSocket dgSocket;
    //Service binder
    private IBinder binder = new CommunicationServiceBinder();
    //Interface that communicates the service with the class that starts/stops the service
    //in this case the Repository
    static CommunicationServiceInterface serviceCallbacks;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    /**
     * Init communication service
     */
    public int onStartCommand(Intent i, int flags, final int startId){
        try {
            dgSocket = new DatagramSocket(Constants.MOBIL_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        initCommunications();
        communicationInProgress = true;
        return Service.START_NOT_STICKY;
    }

    @Override
    /**
     * @onDestroy Destroys communication service
     */
    public void onDestroy(){
        stopCommunications();
        communicationInProgress = false;
        super.onDestroy();
    }

    /**
     * Method used from the Repository to send a message to outside.
     * Prepares the data to send on the variable
     * @param msg is the message to send
     */
    public static void writeMessage(String msg){
        CommunicationAsyncTask.sendMessageUDP(msg);
    }

    /*-------------------------- Service Binder -----------------------------*/

    /**
     * Set the interface to allow to push values from service to the repository
     */
    public class CommunicationServiceBinder extends Binder {
        public void setInterface(CommunicationService.CommunicationServiceInterface callback){
            serviceCallbacks = callback;
        }
    }
    /*--------------------------------------------------------------------------*/
    /*-------------------------- Service Interface -----------------------------*/

    /**
     * Callbacks to the Repository
     */
    public interface CommunicationServiceInterface{
        void rxMessage(String msg);
        //void txMessage(String msg);
    }
}