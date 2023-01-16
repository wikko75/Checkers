package com.example.checkers.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Extends BoardController with functionality for 10x10 board
 */
public class Board10x10Controller extends BoardController {
    /**
     * GridPane holding ImageView object representing board fields
     */
    @FXML
    private GridPane boardPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        board = new ImageView[10][10];
        boardState = new String[10][10];
        for(Node node : boardPane.getChildren()) {
            if(node!=null && GridPane.getColumnIndex(node)!=null && GridPane.getRowIndex(node)!=null) {
                board[GridPane.getColumnIndex(node)][9 - GridPane.getRowIndex(node)] = (ImageView)node;
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
            x1 = 9 - x1;
            y1 = 9 - y1;
            x2 = 9 - x2;
            y2 = 9 - y2;
        }
        String move = x1+" "+y1+" "+x2+" "+y2;
        sendToServer(move);
        System.out.println("Sending move: "+move);
    }

    @Override
    public void setBoardState(String state) {
        String[] fields = state.split("\\W+");
        if(white) {
            for (int i = 0; i < 100; i++) {
                boardState[i / 10][i % 10] = fields[i];
            }
        } else {
            for (int i = 0; i < 100; i++) {
                boardState[(99-i) / 10][(99-i) % 10] = fields[i];
            }
        }
        for(int y=0; y<10; y++) {
            for (int x = 0; x < 10; x++) {
                updateIcon(x, y);
            }
        }
    }
}
