package com.example.checkers.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Communicator {
    private final BufferedReader inF;
    private final BufferedReader inS;
    private final PrintWriter outF;
    private final PrintWriter outS;

    Communicator(Socket firstPlayer, Socket secondPlayer) throws IOException {
        inF = new BufferedReader(new InputStreamReader(firstPlayer.getInputStream()));
        inS = new BufferedReader(new InputStreamReader(secondPlayer.getInputStream()));
        outF = new PrintWriter(firstPlayer.getOutputStream(), true);
        outS = new PrintWriter(secondPlayer.getOutputStream(), true);
    }

    void initiate() {
        outF.println("1");
        outS.println("2");
    }

    void terminate() {
        outF.println("TERMINATE");
        outF.println("Shutting down...");
        outS.println("TERMINATE");
        outS.println("Shutting down...");
    }

    void sendGameModeSelectionRequest() {
        outF.println("SELECT_GM");
        outS.println("SELECT_GM");
    }

    void sendMoveRequest(int turn, String message) {
        if(turn == 1) {
            outF.println("MOVE");
            outF.println(message);
        } else {
            outS.println("MOVE");
            outS.println(message);
        }
    }

    void sendWaitForMoveRequest(int turn) {
        if(turn == 1) {
            outS.println("WAIT_FOR_MOVE");
        } else {
            outF.println("WAIT_FOR_MOVE");
        }
    }

    void drawBoard(String boardState) {
        outF.println("DRAW_BOARD");
        outF.println(boardState);
        outS.println("DRAW_BOARD");
        outS.println(boardState);
    }

    void endGame() {
        outF.println("GAME_END");
        outS.println("GAME_END");
    }

    String getClientInput(int client) throws IOException {
        if(client == 1) {
            return inF.readLine();
        } else {
            return inS.readLine();
        }
    }
}
