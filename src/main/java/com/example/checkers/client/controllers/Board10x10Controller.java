package com.example.checkers.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Board10x10Controller extends BoardController {
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
    }
}
