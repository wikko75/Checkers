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
    protected final Image bf, bf_wm, bf_wk, bf_bm, bf_bk, wf, wf_wm, wf_wk, wf_bm, wf_bk;
    private final Image bf_wm_sel, bf_wk_sel, bf_bm_sel, bf_bk_sel, wf_wm_sel, wf_wk_sel, wf_bm_sel, wf_bk_sel;
    protected boolean leftDownCornerBlack;
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

        bf_wm_sel = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/checkers/client/controllers/icons/black_field-white_man-sel.png")));
        bf_wk_sel = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/checkers/client/controllers/icons/black_field-white_king-sel.png")));
        bf_bm_sel = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/checkers/client/controllers/icons/black_field-black_man-sel.png")));
        bf_bk_sel = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/checkers/client/controllers/icons/black_field-black_king-sel.png")));
        wf_wm_sel = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/checkers/client/controllers/icons/white_field-white_man-sel.png")));
        wf_wk_sel = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/checkers/client/controllers/icons/white_field-white_king-sel.png")));
        wf_bm_sel = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/checkers/client/controllers/icons/white_field-black_man-sel.png")));
        wf_bk_sel = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/checkers/client/controllers/icons/white_field-black_king-sel.png")));
    }

    private void sendMove(String id1, String id2) {
        int x1 = id1.charAt(1) - '0';
        int y1 = id1.charAt(2) - '0';
        int x2 = id2.charAt(1) - '0';
        int y2 = id2.charAt(2) - '0';
        if(!white) {
            x1 = 9 - x1;
            y1 = 9 - y1;
            x2 = 9 - x2;
            y2 = 9 - y2;
        }
        String move = x1+" "+y1+" "+x2+" "+y2;
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

    protected void updateIcon(int x, int y) {
        boolean blackField = (leftDownCornerBlack && (x+y)%2==0) || (!leftDownCornerBlack && (x+y)%2==1);
        switch(boardState[x][y]) {
            case "00" -> board[x][y].setImage(blackField ? bf : wf);
            case "WM" -> board[x][y].setImage(blackField ? bf_wm : wf_wm);
            case "WK" -> board[x][y].setImage(blackField ? bf_wk : wf_wk);
            case "BM" -> board[x][y].setImage(blackField ? bf_bm : wf_bm);
            case "BK" -> board[x][y].setImage(blackField ? bf_bk : wf_bk);
        }
    }

    private void selectIcon(int x, int y) {
        boolean blackField = (leftDownCornerBlack && (x+y)%2==0) || (!leftDownCornerBlack && (x+y)%2==1);
        switch(boardState[x][y]) {
            case "WM" -> board[x][y].setImage(blackField ? bf_wm_sel : wf_wm_sel);
            case "WK" -> board[x][y].setImage(blackField ? bf_wk_sel : wf_wk_sel);
            case "BM" -> board[x][y].setImage(blackField ? bf_bm_sel : wf_bm_sel);
            case "BK" -> board[x][y].setImage(blackField ? bf_bk_sel : wf_bk_sel);
        }
    }
}
