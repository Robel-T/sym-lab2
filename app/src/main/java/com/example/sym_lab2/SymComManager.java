package com.example.sym_lab2;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SymComManager {

    private static final String TAG = SymComManager.class.getSimpleName();
    private CommunicationEventListener communicationEventListener = null;

    public class RequestManager extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            URL url_server = null;
            ResponseHandler rh = null;
            HttpURLConnection urlConnection = null;
            String fromServer;
            StringBuilder response = new StringBuilder();
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
                InputStreamReader inputStreamReader = new InputStreamReader((inputStream));
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                while ((fromServer = bufferedReader.readLine()) != null) {
                    response.append(fromServer);
                }

                rh = new ResponseHandler(response, strings[1],strings[3]);

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                urlConnection.disconnect();
            }

            return rh.ResponseDispatcher();

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

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "?";
    }
}

