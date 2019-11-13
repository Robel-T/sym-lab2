package com.example.sym_lab2;

import com.google.gson.Gson;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ResponseHandler {

    StringBuilder response;
    String header;
    String request;

    public ResponseHandler(StringBuilder response, String request, String header) {
        this.response = response;
        this.header = header;
        this.request = request;
    }

    public String ResponseDispatcher() {
        String prettyFormat = "";
        try {
            if (header.equals("application/json")) {
                prettyFormat = response.substring(0, request.length() - 1);
                prettyFormat += "}";

                Gson gson = new Gson();
                return (gson.fromJson(prettyFormat, Person.class)).toString();

            } else if (header.equals("application/xml")) {

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource(new StringReader(response.toString()));
                Document doc = db.parse(is);
                doc.getDocumentElement().normalize();

                NodeList nodes = doc.getElementsByTagName("person");
                Node node = nodes.item(0);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    prettyFormat = "Message reçu par le serveur\n"
                            + element.getElementsByTagName("name").item(0).getTextContent()
                            + " " + element.getElementsByTagName("firstname").item(0).getTextContent()
                            + "\nGender : " + element.getElementsByTagName("gender").item(0).getTextContent()
                            + "\nMobile : " + element.getElementsByTagName("phone").item(0).getTextContent();
                }
            }

            else if(header.equals("deflate")){
               prettyFormat =  decompressRequest(response.toString().getBytes());
            }
            else {
                prettyFormat = response.substring(0, request.length());
            }
        } catch (
                ParserConfigurationException e) {
            e.printStackTrace();
        } catch (
                SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prettyFormat;
    }

    public String decompressRequest (byte[] compressedRequest ) throws IOException {

        ByteArrayOutputStream decompressed = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();
        inflater.setInput(compressedRequest);

        InflaterOutputStream infStream = new InflaterOutputStream(decompressed, inflater);
        infStream.flush();
        return infStream.toString();
    }
}
