package com.example.checkers.client.controllers;

import com.example.checkers.client.Client;

public abstract class Controller {
    protected Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    protected void sendToServer(String message) {
        client.sendMessageToServer("{\"message\":\""+message+"\"}");
    }
}
