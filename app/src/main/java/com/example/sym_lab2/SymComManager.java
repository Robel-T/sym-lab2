package com.example.sym_lab2;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SymComManager {

    private static final String TAG = SymComManager.class.getSimpleName();

    private CommunicationEventListener communicationEventListener = null;

    public void sendRequest(String url, String request) throws Exception {

        URL url_server = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) url_server.openConnection();
        urlConnection.setRequestMethod("POST");

        Log.w("tag", "test");


        try {

            OutputStream outputStream = urlConnection.getOutputStream();
            Log.w("tag", "test1");

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            Log.w("tag", "test2");

            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            Log.w("tag", "test3");

            bufferedWriter.write(request);

        } finally {
            urlConnection.disconnect();
        }

        // IL faut parser la string et l envoyer au serveur puis retourner la string (prototype, il faudra
        // faire un formulaire et envoyer
    }

    public void setCommunicationEventListener(CommunicationEventListener communicationEventListener) {
        this.communicationEventListener = communicationEventListener;
    }


}
