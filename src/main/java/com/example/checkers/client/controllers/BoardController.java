package com.example.checkers.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public abstract class BoardController extends Controller {
    @FXML
    private Text board_state;
    @FXML
    private Label info;
    @FXML
    private TextField field;
    @FXML
    private Button send;
    private boolean leftDownCornerBlack;

    public void sendMove(ActionEvent event) {
        String move = field.getText();
        field.setText("");
        if(move!=null) {
            client.sendMessageToServer(move);
        }
    }

    public void moveRequest(boolean turn, String message) {
        if(turn) {
            send.setDisable(false);
            Platform.runLater(() -> info.setText(message));
        } else {
            send.setDisable(true);
            Platform.runLater(() -> info.setText("Opponent's turn"));
        }
    }

    // TODO: overwrite for 8x8 and 10x10
    public void setBoardState(String boardState) {
        //Platform.runLater(() -> board_state.setText(boardState));
    }

    public void setLeftDownCornerBlack(boolean leftDownCornerBlack) {
        this.leftDownCornerBlack = leftDownCornerBlack;
    }
}
