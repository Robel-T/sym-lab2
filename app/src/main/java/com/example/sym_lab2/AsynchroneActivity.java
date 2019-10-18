package com.example.sym_lab2;


import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class AsynchroneActivity extends AppCompatActivity {

    Person marion  = new Person("Dutu Launay", "Marion", 24);
    Person edoardo = new Person("Carpita", "Edoardo", 26);
    Person robel   = new Person("Teklehaimanot", "Robel", 26);

    SymComManager scm = new SymComManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.asynchrone_activity_layout);

        // quitter
        Button btn = findViewById(R.id.btn_send);
        btn.setOnClickListener((v) -> {

            try {
                scm.sendRequest("http://sym.iict.ch/rest/txt", marion.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }


}
