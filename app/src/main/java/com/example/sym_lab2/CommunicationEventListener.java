package com.example.sym_lab2;

import java.util.EventListener;

public interface CommunicationEventListener extends EventListener {
    public boolean hadleServerResponse(String response);
}
