package com.example.checkers.client.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public abstract class BoardController extends Controller implements Initializable{
    @FXML
    private Label info;
    protected ImageView[][] board;
    protected String[][] boardState;
    private boolean active;
    private ImageView selected;
    private final Image bf, bf_wm, bf_wk, bf_bm, bf_bk, wf, wf_wm, wf_wk, wf_bm, wf_bk;
    private boolean leftDownCornerBlack;
    protected boolean white;

    public BoardController() {
        bf = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/checkers/client/controllers/icons/black_field-empty.png")));
        bf_wm = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/checkers/client/controllers/icons/black_field-white_man.png")));
        bf_wk = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/checkers/client/controllers/icons/black_field-white_king.png")));
        bf_bm = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/checkers/client/controllers/icons/black_field-black_man.png")));
        bf_bk = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/checkers/client/controllers/icons/black_field-black_king.png")));
        wf = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/checkers/client/controllers/icons/white_field-empty.png")));
        wf_wm = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/checkers/client/controllers/icons/white_field-white_man.png")));
        wf_wk = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/checkers/client/controllers/icons/white_field-white_king.png")));
        wf_bm = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/checkers/client/controllers/icons/white_field-black_man.png")));
        wf_bk = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/checkers/client/controllers/icons/white_field-black_king.png")));
    }

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

    public void setWhite(boolean white) {
        this.white = white;
    }

    public abstract void setBoardState(String state);
}
