package com.example.checkers.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(4444)) {

            System.out.println("Server is listening on port 4444");

            while(true) {

                // connecting sockets
                /*Socket firstClient = serverSocket.accept();
                System.out.println("First client connected");
                System.out.println("Waiting for the second player");

                Socket secondClient = serverSocket.accept();
                System.out.println("Second client connected"); */

                // game loop
                while(true) {
                    //GameModeSelection gameModeSelection = new GameModeSelection(firstClient, secondClient);
                    GameModeSelection gameModeSelection = new GameModeSelection();
                    Thread gameModeSelectionThread = new Thread(gameModeSelection);
                    gameModeSelectionThread.start();
                    try {
                        gameModeSelectionThread.join();
                    } catch (Exception ex) {;}
                    // TODO: handle exception
                    GameMode gameMode = gameModeSelection.getSelection();

                    switch(gameMode) {
                        case BRAZILIAN -> System.out.println("Selected brazilian");
                        // TODO: running game
                        case POLISH -> System.out.println("Selected polish");
                        // TODO: running game
                        // TODO: third variant
                        case EXIT -> { return; }
                    }
                }



                // TODO: Musi byc dokldnie dwoch klientow

            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
