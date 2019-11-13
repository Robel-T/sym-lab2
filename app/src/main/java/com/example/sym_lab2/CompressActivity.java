package com.example.sym_lab2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class CompressActivity extends AppCompatActivity {
    EditText editText;
    TextView return_server;
    SymComManager scm = new SymComManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.compress_activity_layout);

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

                String requestBody = editText.getText().toString();
                byte[] requestBodyCompressed = compressRequest(requestBody);
                String address = "http://sym.iict.ch/rest/txt";
                String headersContent = "Content-Encoding";
                String headersType = "deflate";




                String[] request = {address,requestBodyCompressed.toString(),headersContent,headersType};

                scm.sendRequest(request);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });


    }

    public byte[] compressRequest (String request) throws IOException {

        byte[] input =request.getBytes(Charset.defaultCharset());

        ByteArrayOutputStream compressed = new ByteArrayOutputStream();
        Deflater deflater = new Deflater();
        DeflaterOutputStream defStream = new DeflaterOutputStream(compressed,deflater);

        defStream.write(input);
        defStream.finish();
        return compressed.toByteArray();
    }

}
