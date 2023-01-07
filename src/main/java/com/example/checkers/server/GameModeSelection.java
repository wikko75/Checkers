package com.example.checkers.server;

import java.net.Socket;

public class GameModeSelection implements Runnable {

    //private Socket firstPlayer;
    //private Socket secondPlayer;
    private volatile GameMode selection;

    //public GameModeSelection(Socket firstPlayer, Socket secondPlayer) {
        //this.firstPlayer = firstPlayer;
        //this.secondPlayer = secondPlayer;
    //}

    @Override
    public void run() {
        //TODO implementation of gamemode selection
        selection = GameMode.BRAZILIAN;
    }

    public GameMode getSelection() {
        return selection;
    }
}
