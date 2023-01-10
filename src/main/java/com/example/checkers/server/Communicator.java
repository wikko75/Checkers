package com.example.checkers.server;

import org.json.JSONObject;

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
        outF.println("{\"instruction\":\"init\",\"value\":\"1\"}");
        outS.println("{\"instruction\":\"init\",\"value\":\"2\"}");
    }

    void terminate() {
        outF.println("{\"instruction\":\"terminate\"}");
        outS.println("{\"instruction\":\"terminate\"}");
    }

    void sendGameModeSelectionRequest() {
        outF.println("{\"instruction\":\"select_game_mode\",\"player\":\"1\"}");
        outS.println("{\"instruction\":\"select_game_mode\",\"player\":\"1\"}");
    }

    void sendMoveRequest(int turn, String message) {
        outF.println("{\"instruction\":\"move\",\"player\":\""+turn+"\",\"message\":\""+message+"\"}");
        outS.println("{\"instruction\":\"move\",\"player\":\""+turn+"\",\"message\":\""+message+"\"}");
    }

    void drawBoard(String boardState) {
        outF.println("{\"instruction\":\"draw_board\",\"board_state\":\""+boardState+"\"}");
        outS.println("{\"instruction\":\"draw_board\",\"board_state\":\""+boardState+"\"}");
    }

    void endGame() {
        // TODO
        outF.println("{\"instruction\":\"end_game\",\"winner\":\"white\"}");
        outS.println("{\"instruction\":\"end_game\",\"winner\":\"white\"}");
    }

    String getClientInput(int client) throws IOException {
        String input;
        if(client == 1) {
            input = inF.readLine();
        } else {
            input = inS.readLine();
        }
        JSONObject json = new JSONObject(input);
        return json.getString("message");
    }
}
