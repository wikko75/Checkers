package com.example.checkers.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static void startGame(Communicator communicator, VariantBuilder variantBuilder) {
        VariantDirector director = new VariantDirector(variantBuilder);
        GameInstance gameInstance = new GameInstance(communicator, director.getVariant());
        Thread gameThread = new Thread(gameInstance);
        gameThread.start();
        try {
            gameThread.join();
        }
        catch (InterruptedException ex) {
            System.err.println("Exception waiting for game thread");
            System.exit(1);
        }
    }

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(4444)) {

            System.out.println("Server is listening on port 4444");

            while(true) {

                try {
                    // connecting and initializing player one
                    Socket firstPlayer = serverSocket.accept();
                    System.out.println("First client connected");
                    System.out.println("Waiting for the second player");

                    // connecting and initializing player two
                    Socket secondPlayer = serverSocket.accept();
                    System.out.println("Second client connected");

                    Communicator communicator = new Communicator(firstPlayer, secondPlayer);
                    communicator.initiate();

                    // game loop
                    while (true) {
                        GameModeSelection gameModeSelection = new GameModeSelection(communicator);
                        Thread gameModeSelectionThread = new Thread(gameModeSelection);
                        gameModeSelectionThread.start();
                        try {
                            gameModeSelectionThread.join();
                        } catch (InterruptedException ex) {
                            System.err.println("Exception waiting for selected game mode");
                            System.exit(1);
                        }
                        GameMode gameMode = gameModeSelection.getSelection();
                        VariantBuilder variantBuilder;

                        switch (gameMode) {
                            case BRAZILIAN -> {
                                System.out.println("Selected brazilian");
                                variantBuilder = new BrazilianVariantBuilder();
                                startGame(communicator, variantBuilder);
                            }
                            case POLISH -> {
                                System.out.println("Selected polish");
                                variantBuilder = new PolishVariantBuilder();
                                startGame(communicator, variantBuilder);
                            }
                            // TODO: third variant
                            case EXIT -> {
                                communicator.terminate();
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
