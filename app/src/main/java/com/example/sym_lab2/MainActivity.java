package com.example.sym_lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button asynch     = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.asynch = findViewById(R.id.asynchrone);

        asynch.setOnClickListener((v) -> {
                Intent intent = new Intent(this, AsynchroneActivity.class);
                this.startActivity(intent);


        });

    }
}
