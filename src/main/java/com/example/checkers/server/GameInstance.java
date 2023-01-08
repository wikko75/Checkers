package com.example.checkers.server;

import com.example.checkers.game.Variant;

import java.io.*;
import java.net.Socket;

public class GameInstance implements Runnable {

    private final Socket firstPlayer;
    private final Socket secondPlayer;

    private final Variant variant;
    private int turn = 1;

    public GameInstance(Socket firstPlayer, Socket secondPlayer, Variant variant) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.variant = variant;
    }

    @Override
    public void run() {
        System.out.println("Running game...");

        try {
            InputStream inputF = firstPlayer.getInputStream();
            BufferedReader inF = new BufferedReader(new InputStreamReader(inputF));

            InputStream inputS = secondPlayer.getInputStream();
            BufferedReader inS = new BufferedReader(new InputStreamReader(inputS));

            OutputStream outputF = firstPlayer.getOutputStream();
            PrintWriter outF = new PrintWriter(outputF, true);

            OutputStream outputS = secondPlayer.getOutputStream();
            PrintWriter outS = new PrintWriter(outputS, true);

            //game loop
            // TODO game handling
        }
        catch (IOException ex) {
            System.err.println("IO Exception in GameInstance");
            System.exit(1);
        }
    }
}
