package com.example.checkers.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

/**
 * Implements most of board8x8 and board10x10 functionality
 */
public abstract class BoardController extends Controller implements Initializable{
    /**
     * Label that displays information about current move
     */
    @FXML
    private Label info;
    /**
     * Label that tells player two to wait for player one to start new game
     */
    @FXML
    private Label end_game_info;
    /**
     * Button that let's player one start new game
     */
    @FXML
    private Button new_game;
    /**
     * Pointers to ImageView objects displaying board fields and pieces
     */
    protected ImageView[][] board;
    /**
     * Table storing state of the board received from server
     */
    protected String[][] boardState;
    /**
     * True - board records moves and sends them to server
     * False - board is read-only
     */
    private boolean active;
    /**
     * Pointer to selected field, used to select move
     */
    private ImageView selected;
    /**
     * Images for fields containing all pieces
     */
    protected final Image bf, bf_wm, bf_wk, bf_bm, bf_bk, wf, wf_wm, wf_wk, wf_bm, wf_bk;
    /**
     * Images of selected fields
     */
    private final Image bf_wm_sel, bf_wk_sel, bf_bm_sel, bf_bk_sel, wf_wm_sel, wf_wk_sel, wf_bm_sel, wf_bk_sel;
    /**
     * True - left down corner is black
     * False - left down corner is white
     */
    protected boolean leftDownCornerBlack;
    /**
     * True - player plays white
     * False - player plays black
     */
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

    /**
     * Sends move to server
     * @param id1 field to start move
     * @param id2 field to end move
     */
    protected abstract void sendMove(String id1, String id2);

    /**
     * Changes board to move or read-only state with appropriate message
     * @param turn
     * @param message
     */
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

    /**
     * Sets active to true
     */
    private void activate() {
        active = true;
    }

    /**
     * sets active to false
     */
    private void deactivate() {
        active = false;
    }

    /**
     * handles MouseClicked events on playing fields
     * @param event
     */
    public void fieldClicked(MouseEvent event) {
        if(active) {
            ImageView clicked = (ImageView)event.getSource();
            System.out.println("Clicked: "+clicked.getId());
            if(selected==null) {
                if(select(clicked)) {
                    selected = clicked;
                }
            } else if(selected==clicked) {
                updateIcon(selected);
                selected = null;
            } else {
                sendMove(selected.getId(), clicked.getId());
                updateIcon(selected);
                selected = null;
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

    /**
     * Updates boardState
     * @param state
     */
    public abstract void setBoardState(String state);

    /**
     * Updates icon of field at given coordinates (unselected icon)
     * @param x
     * @param y
     */
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

    /**
     * Updates icon of given field (unselected icon)
     * calls {@link #updateIcon(int, int)}
     * @param field
     */
    private void updateIcon(ImageView field) {
        int x = field.getId().charAt(2) - '0';
        int y = field.getId().charAt(1) - '0';
        updateIcon(x, y);
    }

    /**
     * Checks, if the field can be selected and changes icon to selected
     * @param field
     * @return
     */
    private boolean select(ImageView field) {
        int x = field.getId().charAt(2) - '0';
        int y = field.getId().charAt(1) - '0';
        boolean blackField = (leftDownCornerBlack && (x+y)%2==0) || (!leftDownCornerBlack && (x+y)%2==1);
        boolean correct = false;
        switch(boardState[x][y]) {
            case "WM" -> {
                if(white) {
                    board[x][y].setImage(blackField ? bf_wm_sel : wf_wm_sel);
                    correct = true;
                }
            }
            case "WK" -> {
                if(white) {
                    board[x][y].setImage(blackField ? bf_wk_sel : wf_wk_sel);
                    correct = true;
                }
            }
            case "BM" -> {
                if(!white) {
                    board[x][y].setImage(blackField ? bf_bm_sel : wf_bm_sel);
                    correct = true;
                }
            }
            case "BK" -> {
                if(!white) {
                    board[x][y].setImage(blackField ? bf_bk_sel : wf_bk_sel);
                    correct = true;
                }
            }
        }
        return correct;
    }

    /**
     * Displays end results, changes both players to read-only and allows player one to start new game
     * @param winner
     */
    public void endGame(String winner) {
        deactivate();
        switch(winner) {
            case "WHITE" -> Platform.runLater(() -> info.setText("White won"));
            case "BLACK" -> Platform.runLater(() -> info.setText("Black won"));
            case "DRAW" -> Platform.runLater(() -> info.setText("Draw"));
        }
        if(white) {
            new_game.setVisible(true);
            new_game.setDisable(false);
        } else {
            end_game_info.setVisible(true);
        }
    }

    /**
     * Hides information about game end and hides button that start a new game
     */
    public void hideEndGameInfo() {
        new_game.setDisable(true);
        new_game.setVisible(false);
        end_game_info.setVisible(false);
    }

    /**
     * Sends information to server that client one wants to start a new game
     * @param event
     */
    public void newGame(ActionEvent event) {
        sendToServer("new_game");
    }
}
