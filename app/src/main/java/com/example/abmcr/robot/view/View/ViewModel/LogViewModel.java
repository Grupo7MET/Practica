package com.example.abmcr.robot.view.View.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.abmcr.robot.view.View.Model.Constants;
import com.example.abmcr.robot.view.View.Model.Repository;


/**
 * ViewModel for the communication fragment
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class LogViewModel extends ViewModel implements Repository.RepositoryCallbacks {

    //our repository
    private Repository repository;

    //observable String variable
    private MutableLiveData<String> sLiveMessages;

    //all messages
    private static String sMessages;

    //Constructor
    public LogViewModel(){
        repository = new Repository(this);
    }

    /**
     * Keeps the logs updated
     * @param context
     * @return
     */
    public LiveData<String> printMessages (Context context){

        if(sLiveMessages == null) {
            //init observable variable
            sLiveMessages = new MutableLiveData<>();
        }

        if (sMessages == null){
            //init variable
            sMessages = Constants.LOG_TITLE;
            sLiveMessages.postValue(sMessages);
        }

        //tells the repository to start the service
        repository.startService(context);
        //return the observable variable
        return sLiveMessages;
    }

    public void stopMessaging(Context context){
        //tell the repository to stop the service
        repository.stopService(context);
    }

    /*-------------------------- Repository Callbacks -----------------------------*/

    /**
     * Continuously refreshes both variables
     * @param msg new received log
     */
    @Override
    public void onIncomingMessage(String msg) {
        //post value to the view
        //We continuously refresh both variables
        sMessages = sMessages + msg + '\n' + '\n';
        sLiveMessages.postValue(sMessages);
    }

    @Override
    public void onServiceStopped() {
        //post value to the view

    }
}