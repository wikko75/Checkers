package com.example.checkers.server;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * Class responsible for sending info to players and getting input from them*/
public class Communicator {
    private final BufferedReader inF;
    private final BufferedReader inS;
    private final PrintWriter outF;
    private final PrintWriter outS;

    /**
     * Constructor - sets buffer readers and print writers of the two players
     * @param firstPlayer socket of the first player
     * @param secondPlayer socket of the second player */
    Communicator(Socket firstPlayer, Socket secondPlayer) throws IOException {
        inF = new BufferedReader(new InputStreamReader(firstPlayer.getInputStream()));
        inS = new BufferedReader(new InputStreamReader(secondPlayer.getInputStream()));
        outF = new PrintWriter(firstPlayer.getOutputStream(), true);
        outS = new PrintWriter(secondPlayer.getOutputStream(), true);
    }

    /**Initiates communication*/
    void initiate() {
        outF.println("{\"instruction\":\"init\",\"value\":\"1\"}");
        outS.println("{\"instruction\":\"init\",\"value\":\"2\"}");
    }

    /**
     *Tells client to terminate app */
    void terminate() {
        outF.println("{\"instruction\":\"terminate\"}");
        outS.println("{\"instruction\":\"terminate\"}");
    }

    /**
     * Sends request for selecting game mode to players
     * player field determines which player should select game mode*/
    void sendGameModeSelectionRequest() {
        outF.println("{\"instruction\":\"select_game_mode\",\"player\":\"1\"}");
        outS.println("{\"instruction\":\"select_game_mode\",\"player\":\"1\"}");
    }

    /**
     * Sends request for making moves by players
     * turn field determines which player should move */
    void sendMoveRequest(int turn, String message) {
        outF.println("{\"instruction\":\"move\",\"player\":\""+turn+"\",\"message\":\""+message+"\"}");
        outS.println("{\"instruction\":\"move\",\"player\":\""+turn+"\",\"message\":\""+message+"\"}");
    }

    /**
     * Tells clients to prepare board scene
     * @param size size of the board
     * @param leftDownCornerBlack color coding of the board
     */
    void createBoard(int size, boolean leftDownCornerBlack) {
        outF.println("{\"instruction\":\"create_board\",\"size\":"+size+",\"black\":"+leftDownCornerBlack+"}");
        outS.println("{\"instruction\":\"create_board\",\"size\":"+size+",\"black\":"+leftDownCornerBlack+"}");
    }

    /**
     * Sends current board state to clients
     * @param boardState contains information about all pieces on the board
     */
    void drawBoard(String boardState) {
        outF.println("{\"instruction\":\"draw_board\",\"board_state\":\""+boardState+"\"}");
        outS.println("{\"instruction\":\"draw_board\",\"board_state\":\""+boardState+"\"}");
    }

    /**
     * Tells client to end the game
     * @param winner winner
     */
    void endGame(String winner) {
        outF.println("{\"instruction\":\"end_game\",\"winner\":\""+winner+"\"}");
        outS.println("{\"instruction\":\"end_game\",\"winner\":\""+winner+"\"}");
    }

    /**
     * waits for input from client using readLine() and returns message
     * @param client 1 - client one, 2 - client two
     * @return content of "message" field in received json
     * @throws IOException
     */
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
