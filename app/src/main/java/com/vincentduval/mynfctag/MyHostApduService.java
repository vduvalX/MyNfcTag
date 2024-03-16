package com.vincentduval.mynfctag;

import static com.vincentduval.mynfctag.Utils.convertBytesToStringHexa;
import static com.vincentduval.mynfctag.Utils.convertBytesToStringHexaReadable;
import static com.vincentduval.mynfctag.Utils.convertStringHexaToBytes;

import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;


public class MyHostApduService extends HostApduService {

    @Override public void onCreate() {Log.d("VINCENT_TAG", "MyHostApduService/onCreate()");}


    @Override public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        String commandApduHexa = convertBytesToStringHexa(commandApdu);
        Log.d("VINCENT_TAG", "MyHostApduService/processCommandApdu: commande apdu reçue = " + commandApduHexa);
        String commandApduReadable = convertBytesToStringHexaReadable(commandApdu);
        Log.d("VINCENT_TAG", "MyHostApduService/processCommandApdu: commande apdu reçue = " + commandApduReadable);

        displayMessage("commande reçue\ndu reader NFC:\n" + commandApduReadable);

        boolean isReponseImmediate = false; // choisir ici de faire une réponse immédiate (true) ou différée (false)

        if (isReponseImmediate) { // REPONSE IMMEDIATE

            byte[] immediateResponse = new byte[0]; // à développer, par exemple '9000'

            String immediateResponseReadable = convertBytesToStringHexaReadable(immediateResponse);
            Log.d("VINCENT_TAG", "MyHostApduService/processCommandApdu: réponse apdu immédiate envoyée = " + immediateResponseReadable);
            displayMessage("réponse immédiate\nenvoyée au\nreader NFC\n" + immediateResponseReadable);

            return immediateResponse;
        }

        else { // REPONSE EN DIFFERE
            Thread thread = new Thread(new Runnable() {@Override public void run() {

                try {Thread.sleep(2000);} catch (InterruptedException e) {throw new RuntimeException(e);} // émulation d'un délai de réponse

                //byte[] differedResponse = new byte[0]; // à développer, par exemple '9000'
                byte[] differedResponse = new byte[] {(byte) 0x90, (byte) 0x00};
                //byte[] differedResponse = convertStringHexaToBytes("6F2384 0E3 25041592E5359532E4444463031 A5 11 BF 0C 0E 61 0C 4F 07 A0000000 031010 87 01 01 9000");

                sendResponseApdu(differedResponse); // retourne la réponse apdu en différée

                String differedResponseReadable = convertBytesToStringHexaReadable(differedResponse);
                Log.d("VINCENT_TAG", "MyHostApduService/processCommandApdu: réponse apdu différée envoyée = " + differedResponseReadable);
                displayMessage("réponse apdu différée\nenvoyée au\nreader NFC\n" + differedResponseReadable);
            }}); thread.start();

            Log.d("VINCENT_TAG", "MyHostApduService/processCommandApdu: temporise avec une réponse apdu null");
            return null; // réponse provisoire pour signifier que la véritable réponse arrivera plus tard
        }
    }


    @Override public void onDeactivated(int reason) {
        Log.d("VINCENT_TAG", "MyHostApduService/onDeactivated()");
        displayMessage("déconnecté du\nreader NFC");
    }


    @Override public void onDestroy() {
        Log.d("VINCENT_TAG", "MyHostApduService/onDestroy()");
        super.onDestroy();
    }

    void displayMessage(String messageToDisplayInMainActivity) {
        Intent broadcastIntent = new Intent("MaMessagerie");
        broadcastIntent.putExtra("message", new String(messageToDisplayInMainActivity));
        sendBroadcast(broadcastIntent);
    }

}
