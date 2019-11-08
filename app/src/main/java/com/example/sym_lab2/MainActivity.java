package com.example.sym_lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button asynch     = null;
    private Button delayed    = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.asynch = findViewById(R.id.asynchrone);
        this.delayed = findViewById(R.id.differee);

        asynch.setOnClickListener((v) -> {
                Intent intent = new Intent(this, AsynchroneActivity.class);
                this.startActivity(intent);
        });

        delayed.setOnClickListener((v) -> {
            Intent intent = new Intent(this, DelayedActivity.class);
            this.startActivity(intent);
        });
    }
}
