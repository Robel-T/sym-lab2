package com.example.sym_lab2.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sym_lab2.Interface.CommunicationEventListener;
import com.example.sym_lab2.R;
import com.example.sym_lab2.Business.SymComManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressActivity extends AppCompatActivity implements CommunicationEventListener {
    EditText editText;
    TextView return_server;
    SymComManager scm = new SymComManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.compress_activity_layout);

        editText = findViewById(R.id.testSend);
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
        String prettyFormat = "";
        String[] parsedResponse = response.split(Objects.requireNonNull(System.getProperty("line.separator")));
        try {
            prettyFormat = decompressRequest(parsedResponse[0].getBytes("UTF-8"));
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
        String headersContent2 = "X-Network";
        String headersType2 = "CSD";

        String res = new String((compressRequest(requestBody)));
        return new String[]{address, res, headersContent, headersType, headersContent2, headersType2};
    }



    /* on a implemente au mieux possible les fonctions de compression et decompression*/
    /* avec cette version on genere plus d'exceptions de type header chek invalid ou similaires*/
    /* au niveau du inflater, mais la decompression ne resulte pas tout le temps fiable  */
    private byte[] compressRequest(String compress) throws UnsupportedEncodingException {

        int compressed_data_length = 0;
        byte[] bytes_out = new byte[compress.length()];
        byte[] bytes_in = compress.getBytes("UTF-8");
        byte[] bytes_res;

        Deflater deflater = new Deflater(Deflater.DEFAULT_COMPRESSION, true);
        deflater.setInput(bytes_in);
        deflater.finish();
        compressed_data_length = deflater.deflate(bytes_out);
        deflater.end();

        bytes_res = new byte[compressed_data_length];
        System.arraycopy(bytes_out, 0, bytes_res, 0, compressed_data_length);
        return bytes_res;

    }


    private String decompressRequest(byte[] decompress) {

        int compressed_data_length = decompress.length;
        int decompressed_data_lenght = 0;
        byte[] byte_out = new byte[compressed_data_length];
        String string_res = "";

        Inflater inflater = new Inflater(true);
        inflater.setInput(decompress, 0, compressed_data_length);

        try {
            decompressed_data_lenght = inflater.inflate(byte_out);
            inflater.end();
            string_res = new String(byte_out, 0, decompressed_data_lenght, "UTF-8");

        } catch (UnsupportedEncodingException | DataFormatException e) {
            e.printStackTrace();
        }

        return string_res;
    }
}
