package com.example.checkers.client.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public abstract class BoardController extends Controller implements Initializable{
    @FXML
    private Text board_state;
    @FXML
    private Label info;
    protected ImageView[][] board;
    private boolean active;
    private ImageView selected;
    //private Image bf, bf_wm, bf_wk, bf_bm, bf_bk, wf, wf_wm, wf_wk, wf_bm, wf_bm;
    private boolean leftDownCornerBlack;

    private void sendMove(String id1, String id2) {
        String move = id1.charAt(1)+" "+id1.charAt(2)+" "+id2.charAt(1)+" "+id2.charAt(2);
        client.sendMessageToServer(move);
        System.out.println("Sending move: "+move);
    }

    public void moveRequest(boolean turn, String message) {
        if(turn) {
            activate();
            Platform.runLater(() -> info.setText(message));
        } else {
            deactivate();
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

    private  void activate() {
        active = true;
    }

    private  void deactivate() {
        active = false;
    }

    public void fieldClicked(MouseEvent event) {
        if(active) {
            ImageView clicked = (ImageView)event.getSource();
            System.out.println("Clicked: "+clicked.getId());
            if(selected==null) {
                selected = clicked;
                // TODO visible selection
            } else if(selected==clicked) {
                selected = null;
                // TODO remove visible selection
            } else {
                sendMove(selected.getId(), clicked.getId());
                selected = null;
                // TODO remove visible selection
            }
            if(selected!=null) {
                System.out.println("Selected not null");
                System.out.println("Selected: "+selected.getId());
            }
            else {
                System.out.println("Selected is null");
            }
        }
    }
}
