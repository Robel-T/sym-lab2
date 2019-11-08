package com.example.sym_lab2;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;


public class DelayedActivity extends AppCompatActivity {

    private int index = 0;
    private EditText editText;
    private TextView return_server;
    private Map<Integer, String[]> waiting_requests = new HashMap<>();

    SymComManager scm = new SymComManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.delayed_activity_layout);

        editText = findViewById(R.id.testSend);
        return_server = findViewById(R.id.return_server);
        // quitter
        Button btn = findViewById(R.id.btn_send);
        btn.setOnClickListener((v) -> {

            try {
                scm.setCommunicationEventListener(
                        new CommunicationEventListener() {
                            public boolean handleServerResponse(String response) {
                                return_server.setText(response);
                                return true;

                            }
                        });

                String requestBody = editText.getText().toString();
                String address = "http://sym.iict.ch/rest/txt";
                String headersContent = "Content-Type";
                String headersType = "text/plain";

                String[] request = {address, requestBody, headersContent, headersType};

                while (true) {
                    Log.w("BOUCLE", "entering");
                    if (isNetworkAvailable(this)) {
                        Log.w("BOUCLE", "reseau active");

                        if (waiting_requests.isEmpty()) {
                            Log.w("BOUCLE", "requete simple");
                            scm.sendRequest(request);
                            Log.w("BOUCLE", "requete simple traite");

                        } else {
                            Log.w("BOUCLE", "il y a requetes: " + waiting_requests.size());
                            for (String[] waiting : waiting_requests.values()) {
                                Log.w("BOUCLE", "requete buffer");
                                scm.sendRequest(waiting);
                            }
                        }
                        break;
                    } else {
                        Log.w("BOUCLE", "reseau desactive");
                        if (!waiting_requests.containsKey(index-1)) {
                            Log.w("BOUCLE", "ajoute dans le mapping");
                            waiting_requests.put(index, request);
                            index++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

    private boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
