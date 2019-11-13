package com.example.sym_lab2.Business;

import android.os.AsyncTask;

import com.example.sym_lab2.Interface.CommunicationEventListener;

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

public class SymComManager {

    private static final String TAG = SymComManager.class.getSimpleName();
    private CommunicationEventListener communicationEventListener = null;

    public class RequestManager extends AsyncTask<String,Void,String>{

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
                if(strings.length <= 4) {
                    urlConnection.setRequestProperty(strings[2], strings[3]);
                }
                else {
                    urlConnection.setRequestProperty(strings[2], strings[3]);
                    urlConnection.setRequestProperty(strings[4],strings[5]);
                }


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
            }finally {
                urlConnection.disconnect();
            }

            return response.toString();

        }
        protected void onPostExecute(String result) {
            communicationEventListener.handleServerResponse(result);
        }
    }

    public void sendRequest( String[] request) throws Exception {
        RequestManager rm = new RequestManager();
        rm.execute(request);

    }

    public void setCommunicationEventListener(CommunicationEventListener communicationEventListener) {
        this.communicationEventListener = communicationEventListener;
    }

}

