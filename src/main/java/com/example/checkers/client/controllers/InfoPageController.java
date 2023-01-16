package com.example.checkers.client.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Mainly used to set Label on info_page scene
 */
public class InfoPageController extends Controller {
    @FXML
    private Label info;

    /**
     * Sets Label info to given message
     * @param message
     */
    public void setInfo(String message) {
        Platform.runLater(() -> info.setText(message));
    }
}
