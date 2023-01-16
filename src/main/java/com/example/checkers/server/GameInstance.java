package com.example.checkers.server;

import com.example.checkers.game.Color;
import com.example.checkers.game.Type;
import com.example.checkers.game.Variant;

import java.io.*;
import java.net.Socket;

/**
 * Runnable class that handles running game
 */
public class GameInstance implements Runnable {

    private final Communicator communicator;
    private final Variant variant;
    private int turn = 1;

    public GameInstance(Communicator communicator, Variant variant) {
        this.communicator = communicator;
        this.variant = variant;
    }

    /**
     * Tells current player to move until they've done all correct moves
     * Handles exceptions is {@link Variant#doMove(int, int, int, int, Color)} and notifies client accordingly
     * @param message initial message for client
     */
    private void attemptMove(String message) {
        if(variant.checkForWinningConditions()!= Variant.Winner.NONE) {
            return;
        }
        try {
            communicator.drawBoard(variant.getBoardState());
            communicator.sendMoveRequest(turn, message);
            String line = communicator.getClientInput(turn);
            String[] cords = line.split("\\W+");
            int x1 = Integer.parseInt(cords[0]);
            int y1 = Integer.parseInt(cords[1]);
            int x2 = Integer.parseInt(cords[2]);
            int y2 = Integer.parseInt(cords[3]);
            Color color = turn==1 ? Color.WHITE : Color.BLACK;

            boolean end_turn = variant.doMove(x1, y1, x2, y2, color);
            //TODO remove Type from variant.doMove()
            communicator.drawBoard(variant.getBoardState());
            if(!end_turn) {
                attemptMove("Finish your move");
            }

        }
        catch (Exception ex) {
            // TODO handle exceptions from variant.doMove()
            ex.printStackTrace();
            attemptMove((ex.getClass()).toString());
        }
    }

    /**
     * Starts game thread
     */
    @Override
    public void run() {
        System.out.println("Running game...");
        communicator.createBoard(variant.getBoardSize(), variant.getLeftDownCornerBlack());

        communicator.drawBoard(variant.getBoardState());
        //game loop
        do {
            attemptMove("Your move");
            variant.checkForKings();
            turn = turn==1 ? 2 : 1;
        }
        while (variant.checkForWinningConditions()==Variant.Winner.NONE);
        communicator.endGame(variant.checkForWinningConditions().toString());
        try {
            communicator.getClientInput(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
