package com.example.checkers.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class TestClient implements Runnable {
    private final int port;
    private PrintWriter out;
    private volatile String latestMessage;
    private volatile boolean active;
    TestClient(int port) {
        this.port = port;
    }
    @Override
    public void run() {
        BufferedReader in;
        try {
            Socket socket = new Socket("localhost", port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        active = true;

        while(active) {
            try {
                latestMessage = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    String getLatestMessage() {
        return latestMessage;
    }

    void sendMessage(String message) {
        out.println(message);
    }
}
