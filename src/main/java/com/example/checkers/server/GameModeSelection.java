package com.example.checkers.server;

import java.io.*;

public class GameModeSelection implements Runnable {

    private final Communicator communicator;
    private volatile GameMode selection;

    public GameModeSelection(Communicator communicator) {
        this.communicator = communicator;
        selection = GameMode.NOT_SELECTED;
    }

    @Override
    public void run() {

        try {
            String line;
            do {
                System.out.println("Receiving selection from player one");
                communicator.sendGameModeSelectionRequest();

                line = communicator.getClientInput(1);
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
