package model;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;

import static model.CommunicationService.writeMessage;

/**
 * Class that runs the services
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class Repository implements CommunicationService.CommunicationServiceInterface{

    /**
     * interface that implements all the callbacks from the repository to the class that implements the interface
     * in this case the ViewModel
     */
    private static RepositoryCallbacks repositoryCallback;

    private static boolean serviceIsBound;

    //Constructor
    public Repository(RepositoryCallbacks callbacks){
        this.repositoryCallback = callbacks;
    }

    public void startService(Context context) {
        Intent intent = new Intent(context, CommunicationService.class);
        context.startService(intent);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * When we want to stop the messaging service
     */
    public void stopService(Context context) {
        if (serviceIsBound) {
            context.unbindService(serviceConnection);
            serviceIsBound = false;
        }
        Intent intent = new Intent(context, CommunicationService.class);
        context.stopService(intent);
        //push to the viewmodel saying that the service has been stopped
        repositoryCallback.onServiceStopped();
    }


    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CommunicationService.CommunicationServiceBinder binder
                    = (CommunicationService.CommunicationServiceBinder) service;
            //set the interface to the service so we can listen to the service callbacks
            binder.setInterface(Repository.this);
            serviceIsBound = true;
        }

        @Override
        public void onServiceDisconnected (ComponentName name) {
            serviceIsBound = false;
        }
    };


    /*----------------- Service interface callbakcs to Communication Service -------------------*/

    /**
     * pushes received message to the viewmodel
     * @param msg received message
     */
    @Override
    public void rxMessage(String msg) {
        repositoryCallback.onIncomingMessage(msg);
    }


    /**
     * pushes transmitted message to the Logviewmodel. Not Used in this deliverable.
     * @param msg transmitted message
     */
    public void txMessage(String msg) {
        String time = Calendar.getInstance().getTime().toString();
        repositoryCallback.onIncomingMessage(time + " Android says: " + msg);
    }

    /**
     * Method used to send a message from the View Models to outside
     * @param msg is the message to send
     */
    public void sendMessage(String msg){
        writeMessage(msg);
    }

    /*-------------------- Repository Interface to ViewModel ------------------------*/

    /**
     * Callbacks to the View Models
     */
    public interface RepositoryCallbacks {
        void onIncomingMessage(String msg);
        void onServiceStopped();
    }

}
