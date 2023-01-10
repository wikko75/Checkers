package com.example.checkers.server;

import java.io.*;

public class GameModeSelection implements Runnable {

    private final BufferedReader inF;
    private final BufferedReader inS;
    private final PrintWriter outF;
    private final PrintWriter outS;
    private volatile GameMode selection;

    public GameModeSelection(BufferedReader inF, BufferedReader inS, PrintWriter outF, PrintWriter outS) {
        this.inF = inF;
        this.inS = inS;
        this.outF = outF;
        this.outS = outS;
        selection = GameMode.NOT_SELECTED;
    }

    @Override
    public void run() {

        try {
            String line;
            do {
                System.out.println("Receiving selection from player one");
                outF.println("SELECT_GM");
                outS.println("SELECT_GM");

                line = inF.readLine();
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
