package com.example.sym_lab2.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sym_lab2.Interface.CommunicationEventListener;
import com.example.sym_lab2.Modele.Author;
import com.example.sym_lab2.Modele.Post;
import com.example.sym_lab2.R;
import com.example.sym_lab2.Business.SymComManager;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class GraphqlActivity extends AppCompatActivity implements CommunicationEventListener, AdapterView.OnItemSelectedListener {
    SymComManager scm = new SymComManager();
    Spinner spinnerActors;
    List<Author> listItems=null;
    TextView return_id;
    Author[] authors=null;
    Post[] posts = null;
    boolean selectionned= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectionned = false;

        scm.setCommunicationEventListener(this);
        setContentView(R.layout.graphql_activity_layout);
        spinnerActors = findViewById(R.id.spinner);
        spinnerActors.setOnItemSelectedListener(this);
        return_id = findViewById(R.id.return_id);
        String requestBody = "{\"query\":\"{allAuthors{id,first_name,last_name,email}}\"}";

        try {
            scm.sendRequest(requestHandle(requestBody));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean handleServerResponse(String response) {

        try {
            JSONArray jsonArray=null;
            Gson gson = new Gson();

            if(!selectionned) {
                jsonArray = new JSONObject(response).getJSONObject("data").getJSONArray("allAuthors");
                authors = gson.fromJson(jsonArray.toString(), Author[].class);

                //Creating the ArrayAdapter instance having the country list
                ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, authors);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                spinnerActors.setAdapter(arrayAdapter);
            }
            else{
                jsonArray = new JSONObject(response).getJSONObject("data").getJSONArray("allPostByAuthor");
                posts     = gson.fromJson(jsonArray.toString(),Post[].class);
                String post = "";

                for(Post p : posts){
                    post += p.toString() + "\n";
                }

                return_id.setText(post);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;

    }

        private String[] requestHandle(String requestBody){

        String address = "http://sym.iict.ch/api/graphql";
        String headersContent = "Content-Type";
        String headersType = "application/json";

        return new String[]{address,requestBody,headersContent,headersType};
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectionned = true;
        int authorId = authors[position].getId();


        String requestBody = "{\"query\":\"{allPostByAuthor(authorId: "+ authorId +"){title, description}}\"}";
        try {
            scm.sendRequest(requestHandle(requestBody));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
