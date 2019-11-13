package com.example.sym_lab2.Interface;

import java.util.EventListener;

public interface CommunicationEventListener extends EventListener {
    public boolean handleServerResponse(String response);
}
