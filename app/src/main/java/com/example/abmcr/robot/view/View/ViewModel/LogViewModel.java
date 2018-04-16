package com.example.abmcr.robot.view.View.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import com.example.abmcr.robot.view.View.Model.Repository;


/**
 * Created by Xavi Burruezo on 07/03/2018.
 */

public class LogViewModel extends ViewModel implements Repository.RepositoryCallbacks {

    //our repository
    private Repository repository;

    //observable variable
    private MutableLiveData<String> sLiveMessages; //logs

    //all messages
    private static String sAllMessages; //entireLogs

    public LogViewModel(){
        repository = new Repository(this);
    }

    public LiveData<String> printMessages (Context context){
        if(sLiveMessages == null){
            //init observable variable
            sLiveMessages = new MutableLiveData<>();
            //*if (entireLogs == null) entireLogs = Constants.LOGS_HEADER;
            //*logs.postValue(entireLogs);
        }
        //tell the repository to start the service
        repository.startService(context);
        //return the observable variable
        return sLiveMessages;
    }

    public void stopMessaging(Context context){
        //tell the repository to stop the service
        repository.stopService(context);
    }

    /*-------------------------- Repository Callbacks -----------------------------*/
    @Override
    public void onIncomingMessage(String msg) {
        //post value to the view
        sAllMessages = sAllMessages + msg + "\n";
        sLiveMessages.postValue(sAllMessages);
    }

    @Override
    public void onServiceStopped() {
        //post value to the view

    }
}