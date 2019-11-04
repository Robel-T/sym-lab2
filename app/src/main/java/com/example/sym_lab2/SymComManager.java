package com.example.sym_lab2;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class SymComManager extends AsyncTask<String,Void, String> {

    private static final String TAG = SymComManager.class.getSimpleName();

    private CommunicationEventListener communicationEventListener = null;

    public void sendRequest(String url, String request) throws Exception {
        this.execute(url,request);

    }

    public void setCommunicationEventListener(CommunicationEventListener communicationEventListener) {
        this.communicationEventListener = communicationEventListener;
    }


    @Override
    protected String doInBackground(String... strings) {
        URL url_server = null;
        HttpURLConnection urlConnection = null;
        String fromServer;
        StringBuilder response = new StringBuilder();
        try {
            url_server = new URL(strings[0]);

            urlConnection = (HttpURLConnection) url_server.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "text/plain");


            OutputStream outputStream = urlConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(strings[1]);
            bufferedWriter.flush();

            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader((inputStream));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);



            while ((fromServer = bufferedReader.readLine()) != null) {
                response.append(fromServer);
            }


        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return response.toString();
        // IL faut parser la string et l envoyer au serveur puis retourner la string (prototype, il faudra
        // faire un formulaire et envoyer
    }

    protected void onPostExecute(String result) {
        communicationEventListener.handleServerResponse(result);
    }
}
