package com.example.checkers.client.controllers;

import com.example.checkers.client.Client;

/**
 * Used to handle Client.class
 */
public abstract class Controller {
    /**
     * Client passed from {@link com.example.checkers.client.Main}
     */
    protected Client client;

    /**
     * Sets protected field client
     * @param client
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Packs given String in json format and sends it to server
     * @param message
     */
    protected void sendToServer(String message) {
        client.sendMessageToServer("{\"message\":\""+message+"\"}");
    }
}
