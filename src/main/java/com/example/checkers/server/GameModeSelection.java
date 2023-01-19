package com.example.checkers.server;

import java.io.*;

/**
 * Runnable class that handles game selection
 */
public class GameModeSelection implements Runnable {

    private final Communicator communicator;
    private volatile GameMode selection;

    public GameModeSelection(Communicator communicator) {
        this.communicator = communicator;
        selection = GameMode.NOT_SELECTED;
    }

    /**
     * Starts game selection thread
     */
    @Override
    public void run() {

        try {
            System.out.println("Receiving selection from player one");
            communicator.sendGameModeSelectionRequest();

            switch (communicator.getClientInput(1)) {
                case "italian" -> selection = GameMode.ITALIAN;
                case "german" -> selection = GameMode.GERMAN;
                case "polish" -> selection = GameMode.POLISH;
                case "exit" -> selection = GameMode.EXIT;
                default -> selection = GameMode.NOT_SELECTED;
            }
        }
        catch(IOException ex) {
            System.out.println("Exception in GameModeSelection");
        }
    }

    /**
     * Allows accessing selection from outside the class
     * @return selected game mode
     */
    public GameMode getSelection() {
        return selection;
    }
}
