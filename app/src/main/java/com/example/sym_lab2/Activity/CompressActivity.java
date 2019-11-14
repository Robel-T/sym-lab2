package com.example.sym_lab2.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sym_lab2.Interface.CommunicationEventListener;
import com.example.sym_lab2.R;
import com.example.sym_lab2.Business.SymComManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class CompressActivity extends AppCompatActivity implements CommunicationEventListener {
    EditText editText;
    TextView return_server;
    SymComManager scm = new SymComManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.compress_activity_layout);

        editText      = findViewById(R.id.testSend);
        return_server = findViewById(R.id.return_server);
        Button btn    = findViewById(R.id.btn_send);

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
        String prettyFormat ="";
        try {
            prettyFormat =  decompressRequest(response.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return_server.setText(prettyFormat);

        return true;
    }

    private String[] requestHandle() throws IOException {
        String requestBody = editText.getText().toString();
        String address = "http://sym.iict.ch/rest/txt";
        String headersContent = "X-Content-Encoding";
        String headersType = "gzip, deflate";

        return new String[]{address,compressRequest(requestBody).toString(),headersContent,headersType};
    }

    private byte[] compressRequest (String request) throws IOException {

        byte[] input =request.getBytes(Charset.defaultCharset());

        ByteArrayOutputStream compressed = new ByteArrayOutputStream();
        Deflater deflater = new Deflater();
        DeflaterOutputStream defStream = new DeflaterOutputStream(compressed,deflater);

        defStream.write(input);
        defStream.finish();
        return compressed.toByteArray();
    }

    private String decompressRequest (byte[] compressedRequest ) throws IOException {

        ByteArrayOutputStream decompressed = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();
        inflater.setInput(compressedRequest);

        InflaterOutputStream infStream = new InflaterOutputStream(decompressed, inflater);
        return infStream.toString();
    }


}