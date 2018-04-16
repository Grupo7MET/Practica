package Model;

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

public class CommunicationService  extends Service {

    //constants
    private static final long UPDATE_INTERVAL = 1000; //ms
    private static final long DELAY = 1000; //ms

    //seconds
    private int seconds;
    //timer
    private Timer timer;
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

    @Override
    public int onStartCommand(Intent i, int flags, final int startId){
        //init timer to count seconds
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seconds++;
                //push seconds value to the repository
                serviceCallbakcs.postSecondsValue(seconds);
            }
        },DELAY,UPDATE_INTERVAL);

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        //cancel timer when service is destroyed
        timer.cancel();
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
        void postSecondsValue(int seconds);
    }
    /*--------------------------------------------------------------------------*/

}