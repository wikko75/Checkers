package com.example.checkers.server;

import com.example.checkers.game.Color;
import com.example.checkers.game.Type;
import com.example.checkers.game.Variant;

import java.io.*;
import java.net.Socket;

public class GameInstance implements Runnable {

    private final BufferedReader inF;
    private final BufferedReader inS;
    private final PrintWriter outF;
    private final PrintWriter outS;
    private final Variant variant;
    private int turn = 1;

    public GameInstance(BufferedReader inF, BufferedReader inS, PrintWriter outF, PrintWriter outS, Variant variant) {
        this.inF = inF;
        this.inS = inS;
        this.outF = outF;
        this.outS = outS;
        this.variant = variant;
    }

    private void drawBoard(PrintWriter out1, PrintWriter out2) {
        out1.println("DRAW_BOARD");
        out1.println(variant.getBoardState());
        out2.println("DRAW_BOARD");
        out2.println(variant.getBoardState());
    }

    private void attemptMove(String command, BufferedReader in, PrintWriter out, PrintWriter out2) {
        if(variant.checkForWinningConditions()!= Variant.Winner.NONE) {
            return;
        }
        try {
            out.println("MOVE");
            out.println(command);
            String line = in.readLine();
            String[] cords = line.split("\\W+");
            int x1 = Integer.parseInt(cords[0]);
            int y1 = Integer.parseInt(cords[1]);
            int x2 = Integer.parseInt(cords[2]);
            int y2 = Integer.parseInt(cords[3]);
            Color color = turn==1 ? Color.WHITE : Color.BLACK;

            boolean end_turn = variant.doMove(x1, y1, x2, y2, color, Type.MAN);
            //TODO remove Type from variant.doMove()
            drawBoard(out, out2);
            if(!end_turn) {
                attemptMove("Finish your move", in, out, out2);
            }

        }
        catch (Exception ex) {
            //TODO handle exceptions from variant.doMove()
        }
    }

    @Override
    public void run() {
        System.out.println("Running game...");

        drawBoard(outF, outS);
        //game loop
        do {
            if(turn==1) {
                outS.println("WAIT_FOR_MOVE");
                attemptMove("Your move", inF, outF, outS);
            }
            else {
                outF.println("WAIT_FOR_MOVE");
                attemptMove("Your move", inS, outS, outF);
            }
            turn = turn==1 ? 2 : 1;
        }
        while (variant.checkForWinningConditions()==Variant.Winner.NONE);
        outF.println("GAME_END");
        outS.println("GAME_END");
    }
}
