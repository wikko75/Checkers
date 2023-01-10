package com.example.checkers.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static void startGame(BufferedReader inF, BufferedReader inS, PrintWriter outF, PrintWriter outS, VariantBuilder variantBuilder) {
        VariantDirector director = new VariantDirector(variantBuilder);
        GameInstance gameInstance = new GameInstance(inF, inS, outF, outS, director.getVariant());
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
                    BufferedReader inF = new BufferedReader(new InputStreamReader(firstPlayer.getInputStream()));
                    PrintWriter outF = new PrintWriter(firstPlayer.getOutputStream(), true);
                    outF.println("1");
                    System.out.println("First client connected");
                    System.out.println("Waiting for the second player");

                    // connecting and initializing player two
                    Socket secondPlayer = serverSocket.accept();
                    BufferedReader inS = new BufferedReader(new InputStreamReader(secondPlayer.getInputStream()));
                    PrintWriter outS = new PrintWriter(secondPlayer.getOutputStream(), true);
                    outS.println("2");
                    System.out.println("Second client connected");

                    // game loop
                    while (true) {
                        GameModeSelection gameModeSelection = new GameModeSelection(inF, inS, outF, outS);
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
                                startGame(inF, inS, outF, outS, variantBuilder);
                            }
                            case POLISH -> {
                                System.out.println("Selected polish");
                                variantBuilder = new PolishVariantBuilder();
                                startGame(inF, inS, outF, outS, variantBuilder);
                            }
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
