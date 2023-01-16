package com.example.checkers.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

/**
 * handles game_selection scene
 */
public class GameSelectionController extends Controller {
    /**
     * handles buttons that select game mode and sends selection to server
     * @param event
     */
    @FXML
    public void selectGameMode(ActionEvent event) {
        switch(((Node)event.getSource()).getId()) {
            case "button_polish" -> sendToServer("polish");
            case "button_italian" -> sendToServer("italian");
            case "button_german" -> sendToServer("german");
            case "button_exit" -> sendToServer("exit");
        }
    }
}
