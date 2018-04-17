package com.example.abmcr.robot.view.View.Model;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.util.Calendar;

/**
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class Repository implements CommunicationService.CommunicationServiceInterface{

    //interface that implements all the callbacks from the repository to the class that implements the interface
    //in this case the ViewModel
    private RepositoryCallbacks repositoryCallback;

    private boolean serviceIsBound;

    //Constructor
    public Repository(RepositoryCallbacks callbacks) {
        //get the interface from the viewmodel so we can push values to the viewmodel
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
    @Override
    public void rxMessage(String msg) {
        //push received message to the viewmodel
        String time = Calendar.getInstance().getTime().toString();
        repositoryCallback.onIncomingMessage(time + " Arduino says: " + msg);
    }

    public void txMessage(String msg) {
        //push transmitted message to the viewmodel
        String time = Calendar.getInstance().getTime().toString();
        repositoryCallback.onIncomingMessage(time + " Android says: " + msg);
    }

    /*-------------------- Repository Interface to ViewModel ------------------------*/

    public interface RepositoryCallbacks {
        void onIncomingMessage(String msg);
        void onServiceStopped();
    }
}
