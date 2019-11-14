package com.example.sym_lab2.Activity;

import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.*;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sym_lab2.Interface.CommunicationEventListener;
import com.example.sym_lab2.Modele.Person;
import com.example.sym_lab2.R;
import com.example.sym_lab2.Business.SymComManager;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.StringReader;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class JsonXmlActivity extends AppCompatActivity implements CommunicationEventListener {

        EditText fistName;
        EditText lastName;
        EditText gender;
        EditText tel;
        TextView return_server;
        SymComManager scm = new SymComManager();
        boolean json = false;


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
            Button btnXml = findViewById(R.id.btn_sendxml);



            btnJson.setOnClickListener((v) -> {

                try {
                    json = true;
                    scm.setCommunicationEventListener(this);
                    scm.sendRequest(requestHandle());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

            btnXml.setOnClickListener((v) -> {

                try {
                    json = false;
                    scm.setCommunicationEventListener(this);
                    scm.sendRequest(requestHandle());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }

    @Override
    public boolean handleServerResponse(String response) {

            String[] request = requestHandle();
            String prettyFormat = "";

        String[] parsedResponse = response.split(Objects.requireNonNull(System.getProperty("line.separator")));

            if (request[3].equals("application/json")) {
                prettyFormat = parsedResponse[0];

                Gson gson = new Gson();
                return_server.setText((gson.fromJson(prettyFormat, Person.class)).toString());

            } else{
                prettyFormat = xmlToString(response);
                return_server.setText(prettyFormat);
            }

        return true;
    }

    private String[] requestHandle(){
        String fn = fistName.getText().toString();
        String ln = lastName.getText().toString();
        String gn = gender.getText().toString();
        int tl = Integer.parseInt(tel.getText().toString());
        Person p = new Person(fn,ln,gn,tl);
        String person="";

        String headersContent = "Content-Type";
        String address="";
        String headersType="";


        if(json){
            address = "http://sym.iict.ch/rest/json";
            headersType = "application/json";
            Gson gson = new Gson();
            person = gson.toJson(p);

        }else{
            address = "http://sym.iict.ch/rest/xml";
            headersType = "application/xml";
            person = toXml(p);
        }

        return new String[]{address,person,headersContent,headersType};
    }

    private String toXml(Person p){
        String prettyF = "";


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

        elemLN.setText(p.getFirstName());
        elemFN.setText(p.getLastName());
        elemG.setText(p.getGender());
        elemTel.setText(Integer.toString(p.getPhone()));

        XMLOutputter xmlOutput = new XMLOutputter();

        // display ml
        xmlOutput.setFormat(Format.getPrettyFormat());
        prettyF = xmlOutput.outputString(doc);

        return prettyF;
    }

    private String xmlToString(String response) {
            String prettyFormat="";
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource(new StringReader(response));
                org.w3c.dom.Document doc = db.parse(is);
                doc.getDocumentElement().normalize();

                NodeList nodes = doc.getElementsByTagName("person");
                Node node = nodes.item(0);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    prettyFormat = "Message reçu par le serveur\n"
                            + element.getElementsByTagName("name").item(0).getTextContent()
                            + " " + element.getElementsByTagName("firstname").item(0).getTextContent()
                            + ", " + element.getElementsByTagName("gender").item(0).getTextContent()
                            + "\nMobile : " + element.getElementsByTagName("phone").item(0).getTextContent();
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }

            return prettyFormat;
    }
}
