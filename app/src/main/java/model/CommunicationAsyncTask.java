package model;


import android.os.AsyncTask;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

import static java.net.InetAddress.getByName;
import static model.CommunicationService.dgSocket;
import static model.CommunicationService.serviceCallbacks;

/**
 * Class that executes an AsyncTask to send and receive data
 * Implements functions to initialize and stop the connection
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class CommunicationAsyncTask {

    static String dataToSend1;
    static String dataToSend2;
    static String dataToSend3;

    private static boolean txIsOn,rxIsOn;

    private static TxTask txTask;
    //private static RxTask rxTask;


    /**
     * Executes when data is received.
     * A message is received, classified and pushed to the viewModel (doInBackground)
     * It is recalled to keep receiving messages (onPostExecute)
     */

    static Thread rxTask;


    static void initCommunications() {

        txIsOn = true;
        rxIsOn = true;

        txTask = new TxTask();
        txTask.execute();
        if(rxTask == null) {
            rxTask = new Thread() {
                @Override
                public void run() {
                    while (!isInterrupted()) {
                        if (rxIsOn) {
                            byte[] rxData = new byte[Constants.INPUT_BYTES];
                            if (dgSocket == null || dgSocket.isClosed()) {
                                try {
                                    dgSocket = new DatagramSocket(Constants.MOBIL_PORT);
                                } catch (SocketException e) {
                                    e.printStackTrace();
                                }
                            }

                            DatagramPacket dataPkt = new DatagramPacket(rxData, rxData.length);

                            try {
                                //We receive the message
                                dgSocket.receive(dataPkt);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            final String msg = new String(dataPkt.getData()).trim();
                            if (msg.length() > 0) {
                                //identify the message received
                                serviceCallbacks.rxMessage(msg);
                            }
                        }
                    }
                }
            };
        }
        rxTask.start();
        /*rxTask = new RxTask();
        rxTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/
    }

    /**
     * Used to stop the communication. Tasks are cancelled and variables are reassigned
     */

    static void stopCommunications() {

        //Stop both communications
        if (!txTask.isCancelled()) {
            txTask.cancel(false);
        }

        rxTask.interrupt();
        rxTask = null;

        //All variables stop communications
        txIsOn = false;
        rxIsOn = false;
        dataToSend1 = null;
        dataToSend2 = null;
        dataToSend3 = null;
    }

    /**
     * Is executed when transmitting data
     * Sends through the socket 3 types of messages (doInBackground)
     * It is recalled every 5 seconds (onPostExecute)
     */
    private static class TxTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (!isCancelled() && txIsOn){

                try {
                    //Send kind of Message 1
                    if (dataToSend1 != null) {
                        byte[] msg = dataToSend1.getBytes();
                        DatagramPacket dataPkt = new DatagramPacket(msg, msg.length, getByName(Constants.ARDUINO_IP), Constants.ARDUINO_PORT);
                        DatagramSocket dataSocket = new DatagramSocket();
                        //Messages are sent
                        dataSocket.send(dataPkt);
                        dataSocket.close();
                        if (serviceCallbacks != null) {
                            //msg is sent to ViewModel
                            serviceCallbacks.txMessage(dataToSend1);
                        }
                    }

                    //Send kind of Message 2
                    if (dataToSend2 != null) {
                        byte[] msg = dataToSend2.getBytes();
                        DatagramPacket dataPkt = new DatagramPacket(msg, msg.length, getByName(Constants.ARDUINO_IP), Constants.ARDUINO_PORT);
                        DatagramSocket dataSocket = new DatagramSocket();
                        //Message is sent
                        dataSocket.send(dataPkt);
                        dataSocket.close();
                        if (serviceCallbacks != null) {
                            //msg is sent to ViewModel
                            serviceCallbacks.txMessage(dataToSend2);
                        }
                    }

                    //Send kind of Message 3
                    if (dataToSend3 != null) {
                        byte[] msg = dataToSend3.getBytes();
                        DatagramPacket dataPkt = new DatagramPacket(msg, msg.length, getByName(Constants.ARDUINO_IP), Constants.ARDUINO_PORT);
                        DatagramSocket dataSocket = new DatagramSocket();
                        //Message is sent
                        dataSocket.send(dataPkt);
                        dataSocket.close();
                        if (serviceCallbacks != null) {
                            //msg is sent to ViewModel
                            serviceCallbacks.txMessage(dataToSend3);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (txIsOn) {
                //Send a message every 5 seconds (as specified on the statement)
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {

                        txTask = new TxTask();
                        txTask.execute();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(timerTask, Constants.TIME_BETWEEN_MESSAGES);
            }
        }

    }
}
