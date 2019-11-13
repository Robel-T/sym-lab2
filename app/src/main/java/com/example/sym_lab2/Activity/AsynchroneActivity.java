package com.example.sym_lab2.Activity;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sym_lab2.Interface.CommunicationEventListener;
import com.example.sym_lab2.R;
import com.example.sym_lab2.Business.SymComManager;


public class AsynchroneActivity extends AppCompatActivity implements CommunicationEventListener {

    EditText editText;
    TextView return_server;
    SymComManager scm = new SymComManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.asynchrone_activity_layout);
        editText      = findViewById(R.id.testSend);
        return_server = findViewById(R.id.return_server);
        Button btn = findViewById(R.id.btn_send);

        btn.setOnClickListener((v) -> {
            try {
                scm.setCommunicationEventListener(this);
                scm.sendRequest(requestHandle());
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }


    @Override
    public boolean handleServerResponse(String response) {

        String[] request = requestHandle();
        return_server.setText(response.substring(0,request[1].length()));

        return true;
    }

    private String[] requestHandle(){
        String requestBody = editText.getText().toString();
        String address = "http://sym.iict.ch/rest/txt";
        String headersContent = "Content-Type";
        String headersType = "text/plain";

        return new String[]{address,requestBody,headersContent,headersType};
    }
}
