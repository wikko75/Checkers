package com.example.checkers.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Board8x8Controller extends BoardController {
    @FXML
    private GridPane boardPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        board = new ImageView[8][8];
        boardState = new String[8][8];
        for(Node node : boardPane.getChildren()) {
            if(node!=null && GridPane.getColumnIndex(node)!=null && GridPane.getRowIndex(node)!=null) {
                board[GridPane.getColumnIndex(node)][7 - GridPane.getRowIndex(node)] = (ImageView)node;
            }
        }
    }

    @Override
    protected void sendMove(String id1, String id2) {
        int x1 = id1.charAt(2) - '0';
        int y1 = id1.charAt(1) - '0';
        int x2 = id2.charAt(2) - '0';
        int y2 = id2.charAt(1) - '0';
        if(!white) {
            x1 = 7 - x1;
            y1 = 7 - y1;
            x2 = 7 - x2;
            y2 = 7 - y2;
        }
        String move = x1+" "+y1+" "+x2+" "+y2;
        sendToServer(move);
        System.out.println("Sending move: "+move);
    }

    @Override
    public void setBoardState(String state) {
        String[] fields = state.split("\\W+");
        if(white) {
            for (int i = 0; i < 64; i++) {
                boardState[i / 8][i % 8] = fields[i];
            }
        } else {
            for (int i = 0; i < 64; i++) {
                boardState[(63-i) / 8][(63-i) % 8] = fields[i];
            }
        }
        for(int y=0; y<8; y++) {
            for (int x = 0; x < 8; x++) {
                updateIcon(x, y);
            }
        }
    }
}
