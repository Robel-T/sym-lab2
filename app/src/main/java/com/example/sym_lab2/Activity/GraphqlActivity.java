package com.example.sym_lab2.Activity;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sym_lab2.Interface.CommunicationEventListener;
import com.example.sym_lab2.R;
import com.example.sym_lab2.Business.SymComManager;
import com.google.gson.Gson;

public class GraphqlActivity extends AppCompatActivity implements CommunicationEventListener {
    SymComManager scm = new SymComManager();
    ListView lv = findViewById(R.id.listActors);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scm.setCommunicationEventListener(this);
        setContentView(R.layout.graphql_activity_layout);

        String address = "http://sym.iict.ch/api/graphql";
        String headersContent = "Content-Type";
        String headersType = "application/json";

        String[] request = {address,headersContent,headersType};

        try {
            scm.sendRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean handleServerResponse(String response) {

        String prettyFormat = "";
        prettyFormat = response.substring(0, response.length() - 1);
        prettyFormat += "}";

        Gson gson = new Gson();
        //eturn (gson.fromJson(prettyFormat, Person.class)).toString();

     //   lv.a((gson.fromJson(prettyFormat,Author.class)).toString());

        return true;
    }
}
