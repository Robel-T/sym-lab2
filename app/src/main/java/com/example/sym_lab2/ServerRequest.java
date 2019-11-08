package com.example.sym_lab2;

import java.util.Map;

public class ServerRequest {

    private String requestBody;
    private String serverAddr;
    private Map<String,String> headers;

    public ServerRequest(String request, String serverAddr, Map<String,String> headers) {
        this.headers =  headers;
        this.requestBody = request;
        this.serverAddr = serverAddr;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
    public int getMapSize(){
        return headers.size();
    }

}
