package com.example.sym_lab2;

import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.json.JSONArray;
import org.json.*;
import org.jdom2.*;
import org.w3c.dom.Attr;
import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonParser;


public class JsonXmlActivity extends AppCompatActivity{

        EditText fistName;
        EditText lastName;
        EditText age;
        EditText gender;
        EditText tel;
        TextView return_server;

        SymComManager scm = new SymComManager();



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.json_activity_layout);

            fistName      = findViewById(R.id.firstName);
            lastName      = findViewById(R.id.lastName);
            gender        = findViewById(R.id.gender);
            tel           = findViewById(R.id.tel);

            return_server = findViewById(R.id.return_server);

            // quitter
            Button btnJson = findViewById(R.id.btn_sendjson);
            btnJson.setOnClickListener((v) -> {

                try {
                    scm.setCommunicationEventListener(
                            new CommunicationEventListener(){
                                public boolean handleServerResponse(String response) {
                                    return_server.setText(response);
                                    return true;

                                }
                            });

                    String fn = fistName.getText().toString();
                    String ln = lastName.getText().toString();
                    String gn = gender.getText().toString();
                    int tl = Integer.parseInt(tel.getText().toString());

                    Person p = new Person(fn,ln,gn,tl);

                    Gson gson = new Gson();


                    String address = "http://sym.iict.ch/rest/json";
                    String headersContent = "Content-Type";
                    String headersType = "application/json";

                    String[] request = {address,gson.toJson(p),headersContent,headersType};

                    scm.sendRequest(request);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

            // quitter
            Button btnXml = findViewById(R.id.btn_sendxml);
            btnXml.setOnClickListener((v) -> {

                String prettyF = "";

                try {
                    scm.setCommunicationEventListener(
                            new CommunicationEventListener(){
                                public boolean handleServerResponse(String response) {
                                    return_server.setText(response);
                                    return true;

                                }
                            });

                    String fn = fistName.getText().toString();
                    String ln = lastName.getText().toString();
                    String gn = gender.getText().toString();
                    int tl = Integer.parseInt(tel.getText().toString());


                    Element directory = new Element("directory");

                    Document doc = new Document(directory);
                    DocType docType = new DocType("directory", null,
                            "http://sym.iict.ch/directory.dtd");
                    doc.setDocType(docType);

                    Element personElement = new Element("person");

                    Element elemLN = new Element("name");
                    Element elemFN = new Element("firstname");
                    Element elemG = new Element("gender");
                    Element elemTel = new Element("phone");
                    elemTel.setAttribute(new Attribute("type", "mobile"));

                    personElement.getChildren().add(elemLN);
                    personElement.getChildren().add(elemFN);
                    personElement.getChildren().add(elemG);
                    personElement.getChildren().add(elemTel);

                    directory.getChildren().add(personElement);

                    elemLN.setText(fn);
                    elemFN.setText(ln);
                    elemG.setText(gn);
                    elemTel.setText(Integer.toString(tl));

                    XMLOutputter xmlOutput = new XMLOutputter();

                    // display ml
                    xmlOutput.setFormat(Format.getPrettyFormat());
                    prettyF = xmlOutput.outputString(doc);

                    String address = "http://sym.iict.ch/rest/xml";
                    String headersContent = "Content-Type";
                    String headersType = "application/xml";

                    String[] request = {address, prettyF, headersContent, headersType};

                    scm.sendRequest(request);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

        }

}
