package com.example.checkers.server;

import java.io.*;
import java.net.Socket;

public class GameModeSelection implements Runnable {

    private final Socket firstPlayer;
    private final Socket secondPlayer;
    private volatile GameMode selection;

    public GameModeSelection(Socket firstPlayer, Socket secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        selection = GameMode.NOT_SELECTED;
    }

    @Override
    public void run() {
        //TODO implementation of gamemode selection
        selection = GameMode.BRAZILIAN;

        try {
            InputStream inputF = firstPlayer.getInputStream();
            BufferedReader inF = new BufferedReader(new InputStreamReader(inputF));

            InputStream inputS = secondPlayer.getInputStream();
            BufferedReader inS = new BufferedReader(new InputStreamReader(inputS));

            OutputStream outputF = firstPlayer.getOutputStream();
            PrintWriter outF = new PrintWriter(outputF, true);

            OutputStream outputS = secondPlayer.getOutputStream();
            PrintWriter outS = new PrintWriter(outputS, true);

            String line;
            do {
                System.out.println("Receiving selection from player one");
                outF.println("SEND");
                outF.println("Select game mode");
                outS.println("RECEIVE");
                outS.println("Wait for player one to select game mode");

                line = inF.readLine();
                outF.println("RECEIVE");
                outF.println("Starting game...");
                System.out.println(line);
                switch (line) {
                    case "BRAZILIAN" -> selection = GameMode.BRAZILIAN;
                    case "POLISH" -> selection = GameMode.POLISH;
                    case "EXIT" -> selection = GameMode.EXIT;
                    default -> selection = GameMode.NOT_SELECTED;
                }
            } while (selection == GameMode.NOT_SELECTED);
        }
        catch(IOException ex) {
            System.out.println("Exception in GameModeSelection");
        }
    }

    public GameMode getSelection() {
        return selection;
    }
}
