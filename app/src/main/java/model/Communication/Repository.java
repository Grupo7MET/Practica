package model.Communication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Calendar;

import model.Constants;

import static model.Communication.CommunicationService.writeMessage;

/**
 * Class that runs the services
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class Repository implements CommunicationService.CommunicationServiceInterface {

    /**
     * interface that implements all the callbacks from the repository to the class that implements the interface
     * in this case the ViewModel
     */
    private static RepositoryCallbacks repositoryCallback;

    private static boolean serviceIsBound;

    //Constructor
    public Repository(RepositoryCallbacks callbacks){
        this.repositoryCallback = callbacks;
    }

    public void startService(Context context) {
        Intent intent = new Intent(context, CommunicationService.class);
        context.startService(intent);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * When we want to stop the messaging service
     */
    public void stopService(Context context) {
        if (serviceIsBound) {
            context.unbindService(serviceConnection);
            serviceIsBound = false;
        }
        Intent intent = new Intent(context, CommunicationService.class);
        context.stopService(intent);
        //push to the viewmodel saying that the service has been stopped
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
        public void onServiceDisconnected (ComponentName name) {
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
    }


    /**
     * pushes transmitted message to the Logviewmodel. Not Used in this deliverable.
     * @param msg transmitted message
     *
    public void txMessage(String msg) {
        String time = Calendar.getInstance().getTime().toString();
        repositoryCallback.onIncomingMessage(time + " Android says: " + msg);
    }*/

    /**
     * Method used to send a message from the View Models to outside
     * @param msg is the message to send
     */
    public void sendMessage(String msg){
        writeMessage(msg);
    }

    /*-------------------- Repository Interface to ViewModel ------------------------*/

    /**
     * Callbacks to the View Models
     */
    public interface RepositoryCallbacks {
        void onIncomingMessage(String msg);
        void onServiceStopped();
    }

    /*------------------- Logs ---------------------*/

    /**
     * writeFileLog appends a recived/sent message to the logs file
     * @param context is the context of the fragment
     * @param msg is the message we want to write on the file
     */
    public static void writeFileLog(Context context, String msg, String who){

        String time = "";
        String buffer = "";

        time = Calendar.getInstance().getTime().toString();

        if (who == Constants.PROTOCOL_ANDROID){
            buffer = time + Constants.PROTOCOL_WHO_ANDROID + msg + Constants.PROTOCOL_SPACE;
        }else if (who == Constants.PROTOCOL_ARDUINO){
            buffer = time + Constants.PROTOCOL_WHO_ROBOT + msg + Constants.PROTOCOL_SPACE;
        }

        try{
            OutputStream outputStream = context.openFileOutput(Constants.FILE_NAME, Context.MODE_APPEND);
            outputStream.write(buffer.getBytes());
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * readFileLog reads the file
     * @param context
     * @return a String with all the logs read from the file
     */
    public String readFileLog(Context context){
        try{
            InputStream inputStream = context.openFileInput(Constants.FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String lineText;
            String msg = Constants.LOG_TITLE;

            while((lineText = bufferedReader.readLine()) != null) {
                msg = msg + lineText + Constants.PROTOCOL_SPACE;
            }
            return msg;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void clearFileLog(Context context){

        try{
            OutputStream outputStream = context.openFileOutput(Constants.FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write("".getBytes());
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
