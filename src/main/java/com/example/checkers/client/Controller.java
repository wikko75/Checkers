package com.example.checkers.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Label status;
    @FXML
    private TextField textField;
    @FXML
    private Button send;
    @FXML
    private Label output;
    private Client client;
    private int player;

    @FXML
    void sendAction(ActionEvent event) {
        System.out.println("Button action! "+textField.getText());
        client.sendMessageToServer("{\"message\":\""+textField.getText()+"\"}");
        status.setText("Waiting for server response...");
        textField.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            client = new Client(new Socket("localhost", 4444));
        } catch (IOException ex) {
            System.err.println("IOException in socket");
            System.exit(1);
        }
        status.setText("Waiting for server...");
        receive();
    }

    private void _receive() {
        new Thread(() -> {
            while(client.isConnected()) {
                String serverCommand = client.receiveMessageFromServer();
                System.out.println("New system message! "+serverCommand);
                if(serverCommand!=null) {
                    handleServerCommand(serverCommand);
                }
            }
        }).start();
    }

    private void receive() {
        Thread receiverThread = new Thread(() -> {
            while (client.isConnected()) {
                String serverCommand = client.receiveMessageFromServer();
                System.out.println("New system message! " + serverCommand);
                if (serverCommand != null) {
                    handleServerCommand(serverCommand);
                }
            }
        });
        receiverThread.setDaemon(true);
        receiverThread.start();

    }

    private void handleServerCommand(String serverCommand) {
        JSONObject json = new JSONObject(serverCommand);

        switch(json.getString("instruction")) {
            case "init" -> {
                send.setDisable(true);
                player = json.getInt("value");
            }
            case "select_game_mode" -> {
                if (json.getInt("player")==player) {
                    send.setDisable(false);
                    setLabel("Select the game mode", status);
                } else {
                    send.setDisable(true);
                    setLabel("Wait for the other player to select the game mode", status);
                }
            }
            case "move" -> {
                if(json.getInt("player")==player) {
                    send.setDisable(false);
                    setLabel(json.getString("message"), status);
                } else {
                    send.setDisable(true);
                    setLabel("Wait for opponent's move", status);
                }
            }
            case "create_board" -> {
                send.setDisable(true);
            }
            case "draw_board" -> {
                // TODO: reverse board for player 2
                setLabel(json.getString("board_state"), output);
            }
            case "end_game" -> {
                send.setDisable(player != 1);
                setLabel(json.getString("winner"), status);
            }
            case "terminate" -> {
                send.setDisable(true);
                setLabel("Shutting down...", status);
                try{ Thread.sleep(1000); }
                catch(InterruptedException ex) {}
                System.exit(0);
            }
        }
    }

    private  void setLabel(String message, Label label) {
        Platform.runLater(() -> label.setText(message));
    }
}
