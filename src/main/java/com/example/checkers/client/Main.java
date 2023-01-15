package com.example.checkers.client;

import com.example.checkers.client.controllers.BoardController;
import com.example.checkers.client.controllers.Controller;
import com.example.checkers.client.controllers.InfoPageController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;

public class Main extends Application {
    private Client client;
    private Stage stage;
    private Scene game_selection, info_page, board8x8, board10x10;
    private Controller gameSelectionController, infoPageController, board8x8Controller, board10x10Controller;
    private int player;
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        // initializing Client
        try {
            client = new Client(new Socket("localhost", 4444));
        } catch (IOException ex) {
            System.err.println("IOException in socket");
            System.exit(1);
        }
        // initializing Loaders
        FXMLLoader game_selection_loader = new FXMLLoader(getClass().getResource("game_selection.fxml"));
        FXMLLoader info_page_loader = new FXMLLoader(getClass().getResource("info_page.fxml"));
        FXMLLoader board8x8_loader = new FXMLLoader(getClass().getResource("board8x8.fxml"));
        FXMLLoader board10x10_loader = new FXMLLoader(getClass().getResource("board10x10.fxml"));
        // initializing Scenes
        game_selection = new Scene(game_selection_loader.load(), 600, 400);
        info_page = new Scene(info_page_loader.load(), 600, 400);
        board8x8 = new Scene(board8x8_loader.load(), 600, 400);
        board10x10 = new Scene(board10x10_loader.load(), 600, 400);
        // getting Controllers
        gameSelectionController = game_selection_loader.getController();
        infoPageController = info_page_loader.getController();
        board8x8Controller = board8x8_loader.getController();
        board10x10Controller = board10x10_loader.getController();
        // passing client to Controllers
        gameSelectionController.setClient(client);
        infoPageController.setClient(client);
        board8x8Controller.setClient(client);
        board10x10Controller.setClient(client);
        // setting GUI
        stage.setTitle("Checkers client");
        stage.setScene(info_page);
        ((InfoPageController)infoPageController).setInfo("Wait for server response");
        stage.show();
        startReceiving();
    }

    public static void main(String[] args) {
        launch();
    }

    private void startReceiving() {
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
                player = json.getInt("value");
            }
            case "select_game_mode" -> {
                if (json.getInt("player")==player) {
                    changeScene(game_selection);
                } else {
                    changeScene(info_page);
                    ((InfoPageController)infoPageController).setInfo("Wait for the other player to select game mode");
                }
            }
            case "move" -> {
                if(json.getInt("player")==player) {

                } else {

                }
            }
            case "create_board" -> {
                // TODO: reverse numeration for player 2
                boolean leftDownCornerBlack = json.getBoolean("black");
                if(json.getInt("size")==8) {
                    changeScene(board8x8);
                    ((BoardController)board8x8Controller).setLeftDownCornerBlack(leftDownCornerBlack);
                }
                else {
                    changeScene(board10x10);
                    ((BoardController)board10x10Controller).setLeftDownCornerBlack(leftDownCornerBlack);
                }
            }
            case "draw_board" -> {
                // TODO: reverse board for player 2

            }
            case "end_game" -> {

            }
            case "terminate" -> {

                try{ Thread.sleep(1000); }
                catch(InterruptedException ex) {}
                System.exit(0);
            }
        }
    }

    private  void changeScene(Scene scene) {
        Platform.runLater(() -> stage.setScene(scene));
    }
}
