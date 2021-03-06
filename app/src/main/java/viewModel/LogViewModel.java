package viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import model.Constants;
import model.Communication.Repository;


/**
 * ViewModel for the communication fragment
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class LogViewModel extends ViewModel implements Repository.RepositoryCallbacks {

    //our repository
    private Repository repository;

    //observable String variable
    private MutableLiveData<String> sLiveMessages;

    private Context context;

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

        sLiveMessages.postValue(repository.readFileLog(context));

        this.context = context;
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
    public void onIncomingMessage (String msg) {
        //post value to the view
        //We continuously refresh both variables
        repository.writeFileLog(context,msg,Constants.PROTOCOL_ARDUINO);

        sLiveMessages.postValue(repository.readFileLog(context));

    }

    @Override
    public void onServiceStopped() {
        //post value to the view

    }
}