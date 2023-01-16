package com.example.checkers.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Client.class is responsible for communication with server
 */
public class Client {
    /**
     * Socket used for connection to server
     */
    private final Socket socket;
    /**
     * Used to send information to server
     */
    private final BufferedReader in;
    /**
     * Used to receive information from server
     */
    private final PrintWriter out;

    Client(Socket socket) throws IOException {
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Sends given String message to server
     * @param messageToServer message to send
     */
    public void sendMessageToServer(String messageToServer) {
        out.println(messageToServer);
    }

    /**
     * Receives message from server using readLine()
     * @return received message
     */
    String receiveMessageFromServer() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.err.println("IOException in readline");
            System.exit(1);
        }
        return null;
    }

    /**
     * Tells whether client is connected
     * @return true if connected, false otherwise
     */
    boolean isConnected() {
        return socket.isConnected();
    }

    /**
     * Closes all connection
     */
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
