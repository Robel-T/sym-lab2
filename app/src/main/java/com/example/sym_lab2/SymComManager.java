package com.example.sym_lab2;

import org.json.JSONException;
import org.json.JSONObject;

public class SymComManager {

    private static final String TAG = SymComManager.class.getSimpleName();

    private CommunicationEventListener communicationEventListener = null;

    public void sendRequest(String url, String request) throws JSONException {
        JSONObject jsonObject = new JSONObject(request);


        // IL faut parser la string et l envoyer au serveur puis retourner la string (prototype, il faudra
        // faire un formulaire et envoyer
    }

    public void setCommunicationEventListener(CommunicationEventListener communicationEventListener) {
        this.communicationEventListener = communicationEventListener;
    }


}
