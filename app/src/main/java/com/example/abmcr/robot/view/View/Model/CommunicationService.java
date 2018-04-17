package com.example.abmcr.robot.view.View.Model;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.net.DatagramSocket;
import java.net.SocketException;

import static com.example.abmcr.robot.view.View.Model.CommunicationAsyncTask.initCommunications;
import static com.example.abmcr.robot.view.View.Model.CommunicationAsyncTask.stopCommunications;

/**
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class CommunicationService extends Service {

    public static Boolean communicationInProgress = false;
    static DatagramSocket dgSocket;
    //service binder
    private IBinder binder = new CommunicationServiceBinder();
    //interface that communicates the service with the class that starts/stops the service
    //in this case the Repository
    static CommunicationServiceInterface serviceCallbakcs;

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
    //Init communication service
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
    //destroy communication service
    public void onDestroy(){
        stopCommunications();
        communicationInProgress = false;
        super.onDestroy();
    }

    //Prepare the data to send on the variables
    public static void sendMessage(String data1, String data2, String data3){
        CommunicationAsyncTask.dataToSend1 = data1;
        CommunicationAsyncTask.dataToSend2 = data2;
        CommunicationAsyncTask.dataToSend3 = data3;
    }

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
    /*------------------- Callback Interface to CommunicationAsyncTask ---------------------*/
    public static void writeMessage(){
        //TODO change kind of message to send
        sendMessage("hi1", "hi2", "hi3");
    }
}