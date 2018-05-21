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

    static String dataToSend;

    private static boolean txIsOn,rxIsOn;

    private static TxTask txTask;

    /**
     * Executes when data is received.
     * A message is received, classified and pushed to the viewModel (doInBackground)
     * It is recalled to keep receiving messages (onPostExecute)
     */

    static Thread rxTask;


    static void initCommunications() {

        rxIsOn = true;

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
    }

    static void sendMessageUDP(){
        txIsOn = true;
        txTask = new TxTask();
        txTask.execute();
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
        dataToSend = null;
    }

    /**
     * Is executed when transmitting data
     * Sends through the socket the specified message (doInBackground)
     */
    private static class TxTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (!isCancelled() && txIsOn){
                try {
                    //Send the UDP message
                    if (dataToSend != null) {
                        byte[] msg = dataToSend.getBytes();
                        DatagramPacket dataPkt = new DatagramPacket(msg, msg.length, getByName(Constants.ARDUINO_IP), Constants.ARDUINO_PORT);
                        DatagramSocket dataSocket = new DatagramSocket();
                        //Messages are sent
                        dataSocket.send(dataPkt);
                        dataSocket.close();
                        if (serviceCallbacks != null) {
                            //msg is sent to ViewModel (for LOG activity)
                            serviceCallbacks.txMessage(dataToSend);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //We close the communication
            if (!txTask.isCancelled()) {
                txTask.cancel(false);
            }
            txIsOn = false;
            return null;
        }
    }
}
