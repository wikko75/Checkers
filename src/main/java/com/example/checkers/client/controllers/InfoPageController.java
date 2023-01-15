package com.example.checkers.client.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InfoPageController extends Controller {
    @FXML
    private Label info;

    public void setInfo(String message) {
        Platform.runLater(() -> info.setText(message));
    }
}
