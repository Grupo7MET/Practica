package viewModel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import model.Communication.Repository;

/**
 * ViewModel for the splash activity
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

//It exists to clear the Log file every time we open the APP
public class SplashViewModel extends ViewModel implements Repository.RepositoryCallbacks {

    //our repository
    private Repository repository;

    private Context context;

    //Constructor
    public SplashViewModel(){
        repository = new Repository(this);
    }

    public void clearLogsFile(Context context){
        this.context = context;
        repository.clearFileLog(context);
    }

    /*-------------------------- Repository Callbacks -----------------------------*/

    @Override
    public void onIncomingMessage (String msg) {

    }

    @Override
    public void onServiceStopped() {

    }
}