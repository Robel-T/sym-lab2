package com.example.sym_lab2;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class AsynchroneActivity extends AppCompatActivity {

    Person marion  = new Person("Dutu Launay", "Marion", 24);
    Person edoardo = new Person("Carpita", "Edoardo", 26);
    Person robel   = new Person("Teklehaimanot", "Robel", 26);

    EditText editText;
    TextView return_server;

    SymComManager scm = new SymComManager();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.asynchrone_activity_layout);

        editText      = findViewById(R.id.testSend);
        return_server = findViewById(R.id.return_server);
        // quitter
        Button btn = findViewById(R.id.btn_send);
        btn.setOnClickListener((v) -> {

            try {
                scm.setCommunicationEventListener(
                        new CommunicationEventListener(){
                            public boolean handleServerResponse(String response) {
                                return_server.setText(response);
                                return true;

                            }
                        });


                String request = editText.getText().toString();

                scm.sendRequest("http://sym.iict.ch/rest/txt", request);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }


}
