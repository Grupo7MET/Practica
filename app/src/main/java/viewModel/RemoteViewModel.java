package viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import model.Constants;
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

    private static String sMessages;


    //Constructor
    public RemoteViewModel(){
        repository = new Repository(this);

    }

    /**
     * Keeps the logs updated
     * @param context
     * @return
     */
    public LiveData<String> printMessages (Context context){

        if(sLiveTemperature == null) {
            //init observable variable
            sLiveTemperature = new MutableLiveData<>();
        }

        //tells the repository to start the service
        repository.startService(context);
        //return the observable variable
        return sLiveTemperature;
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
        //Assign value for the temperature
        sLiveTemperature.postValue(msg);

        //TODO aqui tratamos la string (el switch)
    }

    @Override
    public void onServiceStopped() {
        //post value to the view

    }
}