package com.example.checkers.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer implements Runnable {
    private final ServerSocket serverSocket;
    private volatile Communicator communicator;
    TestServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }
    @Override
    public void run() {
        try {
            Socket firstClient = serverSocket.accept();
            Socket secondClient = serverSocket.accept();
            communicator = new Communicator(firstClient, secondClient);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Communicator getCommunicator() {
        return communicator;
    }
}
