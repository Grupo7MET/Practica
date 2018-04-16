package com.example.abmcr.robot.view.View.Model;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Manel on 16/4/18.
 */

public class CommunicationService extends Service {


    //service binder
    private IBinder binder = new CommunicationServiceBinder();
    //interface that communicates the service with the class that starts/stops the service
    //in this case the Repository
    private CommunicationServiceInterface serviceCallbakcs;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /*
    @Override
    public void onCreate() {
        super.onCreate();
    }
*/
    @Override
    public int onStartCommand(Intent i, int flags, final int startId){
        //TODO
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        //TODO
        super.onDestroy();
    }

    /*public static void sendDatagram(String datagram){
        CommunicationTasks.datagramToSend = datagram;
        DebugUtils.debug("Datagram:", datagram);
        //serviceCallbakcs.txMessageValue(datagram);
    }*/

    /*-------------------------- Service Binder -----------------------------*/
    public class CommunicationServiceBinder extends Binder {

        //set the interface to allow to push values from service to the repository
        public void setInterface(CommunicationService.CommunicationServiceInterface callback){
            serviceCallbakcs = callback;
        }
    }
    /*--------------------------------------------------------------------------*/
    /*-------------------------- Service Interface -----------------------------*/
    public interface CommunicationServiceInterface{
        void rxMessage(String msg);
        void txMessage(String msg);
    }
    /*--------------------------------------------------------------------------*/

}