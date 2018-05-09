package model;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;

/**
 * Class that runs the services
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class Repository implements CommunicationService.CommunicationServiceInterface{

    //interface that implements all the callbacks from the repository to the class that implements the interface
    //in this case the ViewModel
    private static RepositoryCallbacks repositoryCallback;

    private static boolean serviceIsBound;

    private static String[] subStrings;

    //Constructor
    public Repository(RepositoryCallbacks callbacks){
        this.repositoryCallback = callbacks;
    }

    public void startService(Context context) {
        Intent intent = new Intent(context, CommunicationService.class);
        context.startService(intent);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    //When we want to stop the messaging service
    public void stopService(Context context) {
        if (serviceIsBound) {
            context.unbindService(serviceConnection);
            serviceIsBound = false;
        }
        Intent intent = new Intent(context, CommunicationService.class);
        context.stopService(intent);
        //push to the viewmodel that the service has been stopped
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
        public void onServiceDisconnected(ComponentName name) {
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
        /*
        subStrings = msg.split("_");

        //TODO aixo va a cada un dels viewmodels
        //First we check the protocol with just one _
        if(subStrings.length == 2) {

            //Sends the message to the correct Viewmodel
            switch (subStrings[0]) {
                case "acc":

                    break;

                case "rem":
                    //repositoryCallbackRemote.onIncomingTemperature(subStrings[1]);
                    break;

                case "lab":

                    break;

                case "log":
                    //String time = Calendar.getInstance().getTime().toString();
                    //repositoryCallbackLog.onIncomingMessage(time + " Arduino says: " + subStrings[1]);
                    break;
            }
        }*/
    }

    /**
     * pushes transmitted message to the viewmodel
     * @param msg transmitted message
     */
    public void txMessage(String msg) {
        String time = Calendar.getInstance().getTime().toString();
        repositoryCallback.onIncomingMessage(time + " Android says: " + msg);
    }

    /*-------------------- Repository Interface to ViewModel ------------------------*/

    public interface RepositoryCallbacks {
        void onIncomingMessage(String msg);
        void onServiceStopped();
    }

}
