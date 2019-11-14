package com.example.sym_lab2.Business;

import android.os.AsyncTask;
import android.util.Log;

import com.example.sym_lab2.Interface.CommunicationEventListener;

import org.apache.commons.io.IOUtils;

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
import java.util.logging.Logger;

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
            String body = null;

            try {
                url_server = new URL(strings[0]);

                urlConnection = (HttpURLConnection) url_server.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty(strings[2], strings[3]);



                OutputStream outputStream = urlConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                bufferedWriter.write(strings[1]);
                bufferedWriter.flush();


                InputStream inputStream = urlConnection.getInputStream();

                String encoding = urlConnection.getContentEncoding();
                encoding = encoding == null ? "UTF-8" : encoding;
                body = IOUtils.toString(inputStream, encoding);

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                urlConnection.disconnect();
            }

            return body;

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

