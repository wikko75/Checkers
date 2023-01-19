package com.example.checkers.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main class in com.example.checkers.server,
 * handles socket connections and running threads
 */
public class Server {

    /**
     * Starts game of selected game mode
     * @param communicator used for communicating between game thread and players
     * @param variantBuilder builder for the selected game mode
     */
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

    /**
     * Starts server, connects clients and starts threads
     * @param args
     */
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(4444)) {

            System.out.println("Server is listening on port 4444");

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
                        case ITALIAN -> {
                            System.out.println("Selected italian");
                            variantBuilder = new ItalianVariantBuilder();
                            startGame(communicator, variantBuilder);
                        }
                        case GERMAN -> {
                            System.out.println("Selected german");
                            variantBuilder = new GermanVariantBuilder();
                            startGame(communicator, variantBuilder);
                        }
                        case POLISH -> {
                            System.out.println("Selected polish");
                            variantBuilder = new PolishVariantBuilder();
                            startGame(communicator, variantBuilder);
                        }
                        case EXIT -> {
                            communicator.terminate();
                            System.exit(0);
                        }
                        case NOT_SELECTED -> {
                            System.err.println("Problem in GameModeSelection");
                            System.exit(1);
                        }
                    }
                }
            }
            catch(IOException ex) {
                System.err.println("Problem contacting sockets, shutting down");
                System.exit(1);
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
