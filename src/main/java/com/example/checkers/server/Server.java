package com.example.checkers.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(4444)) {

            System.out.println("Server is listening on port 4444");

            while(true) {

                // connecting sockets
                Socket firstPlayer = serverSocket.accept();
                System.out.println("First client connected");
                System.out.println("Waiting for the second player");

                Socket secondPlayer = serverSocket.accept();
                System.out.println("Second client connected");

                try {
                    OutputStream outputF = firstPlayer.getOutputStream();
                    PrintWriter outF = new PrintWriter(outputF, true);
                    System.out.println("Initializing player one");
                    outF.println("1");

                    OutputStream outputS = secondPlayer.getOutputStream();
                    PrintWriter outS = new PrintWriter(outputS, true);
                    System.out.println("Initializing player two");
                    outS.println("2");

                    // game loop
                    while (true) {
                        GameModeSelection gameModeSelection = new GameModeSelection(firstPlayer, secondPlayer);
                        Thread gameModeSelectionThread = new Thread(gameModeSelection);
                        gameModeSelectionThread.start();
                        try {
                            gameModeSelectionThread.join();
                        } catch (InterruptedException ex) {
                            System.err.println("Exception waiting for selected game mode");
                            System.exit(1);
                        }
                        // TODO: handle exception
                        GameMode gameMode = gameModeSelection.getSelection();

                        switch (gameMode) {
                            case BRAZILIAN -> System.out.println("Selected brazilian");
                            // TODO: running game
                            case POLISH -> System.out.println("Selected polish");
                            // TODO: running game
                            // TODO: third variant
                            case EXIT -> {
                                outF.println("TERMINATE");
                                outF.println("Shutting down...");
                                outS.println("TERMINATE");
                                outS.println("Shutting down...");
                                System.exit(0);
                            }
                        }
                    }
                }
                    catch(IOException ex) {
                    System.err.println("Problem contacting sockets, shutting down");
                    System.exit(1);
                }



                // TODO: Musi byc dokldnie dwoch klientow

            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
