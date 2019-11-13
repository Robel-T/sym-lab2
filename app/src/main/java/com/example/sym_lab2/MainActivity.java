package com.example.sym_lab2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.example.sym_lab2.Activity.AsynchroneActivity;
import com.example.sym_lab2.Activity.CompressActivity;
import com.example.sym_lab2.Activity.DelayedActivity;
import com.example.sym_lab2.Activity.GraphqlActivity;
import com.example.sym_lab2.Activity.JsonXmlActivity;

public class MainActivity extends AppCompatActivity {

    private Button asynch     = null;
    private Button delayed    = null;
    private Button json       = null;
    private Button compress   = null;
    private Button graphql    = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.asynch  = findViewById(R.id.asynchrone);
        this.delayed = findViewById(R.id.differee);
        this.json    = findViewById(R.id.serJson);
        this.compress= findViewById(R.id.compress);
        this.graphql = findViewById(R.id.graphql);


        asynch.setOnClickListener((v) -> {
                Intent intent = new Intent(this, AsynchroneActivity.class);
                this.startActivity(intent);
        });

        delayed.setOnClickListener((v) -> {
            Intent intent = new Intent(this, DelayedActivity.class);
            this.startActivity(intent);
        });

        json.setOnClickListener((v) -> {
            Intent intent = new Intent(this, JsonXmlActivity.class);
            this.startActivity(intent);
        });

        compress.setOnClickListener((v) -> {
            Intent intent = new Intent(this, CompressActivity.class);
            this.startActivity(intent);
        });

        graphql.setOnClickListener((v) ->{
            Intent intent = new Intent(this, GraphqlActivity.class);
            this.startActivity(intent);
        });
    }
}
