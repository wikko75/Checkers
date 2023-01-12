package com.example.checkers.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    Client(Socket socket) throws IOException {
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    void sendMessageToServer(String messageToServer) {
        out.println(messageToServer);
    }

    String receiveMessageFromServer() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.err.println("IOException in readline");
            System.exit(1);
        }
        return null;
    }

    boolean isConnected() {
        return socket.isConnected();
    }

    void close() {
        try{
            if(in != null) {
                in.close();
            }
            if(out != null) {
                out.close();
            }
            if(socket != null) {
                socket.close();
            }
        }
        catch(IOException ex) {
            System.err.println("Error closing client");
        }
    }
}
