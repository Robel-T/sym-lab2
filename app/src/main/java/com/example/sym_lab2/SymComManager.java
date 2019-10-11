package com.example.sym_lab2;

public class SymComManager {

    private static final String TAG = SymComManager.class.getSimpleName();

    private CommunicationEventListener communicationEventListener = null;

    public void sendRequest(String url, String request) {
        //TODO to implement
    }

    public void setCommunicationEventListener(CommunicationEventListener communicationEventListener) {
        this.communicationEventListener = communicationEventListener;
    }


}
