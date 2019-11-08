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
    private LinkedList<String[]> waiting_requests = new LinkedList<>();

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

                waiting_requests.add(request);

                if (isNetworkAvailable(DelayedActivity.this)) {
                    scm.sendRequest(waiting_requests.pop());
                } else {
                    new Thread(() -> {
                        while (!isNetworkAvailable(DelayedActivity.this)) {

                            try {
                                //Affichage du nombre de message en attente
                                Log.w("Boucle", "pas de reseau");
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        //Lorsque le réseau est de retour appel de la tâche d'envoie des message
                        try {
                            while(!waiting_requests.isEmpty()) {
                                scm.sendRequest(waiting_requests.pop());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
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
