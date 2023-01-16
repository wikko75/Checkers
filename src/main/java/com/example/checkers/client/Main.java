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

/**
 * Main.class starts client app, initializes GUI and handles operations
 */
public class Main extends Application {
    /**
     * Object of Client.class used to commuicate with server
     */
    private Client client;
    /**
     * Main stage
     */
    private Stage stage;
    /**
     * Scenes used for selecting game mode
     */
    private Scene game_selection;
    /**
     * Scene used to display information
     */
    private Scene info_page;
    /**
     * Scenes displaying 8x8 and 10x10 playing boards
     */
    private Scene board8x8, board10x10;
    /**
     * Controller for info_page scene
     */
    private Controller infoPageController;
    /**
     * Controllers for board8x8 and board10x0 scenes
     */
    private BoardController board8x8Controller, board10x10Controller;
    /**
     * Pointer to controller of current board scene
     */
    private BoardController currentBoardController;
    /**
     * 1 - player one
     * 2 - player two
     * Used for handling server commands, which execution differs by player
     */
    private int player;

    /**
     * Start method, initializes the application
     * @param stage
     * @throws IOException
     */
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
        FXMLLoader game_selection_loader = new FXMLLoader(getClass().getResource("fxml/game_selection.fxml"));
        FXMLLoader info_page_loader = new FXMLLoader(getClass().getResource("fxml/info_page.fxml"));
        FXMLLoader board8x8_loader = new FXMLLoader(getClass().getResource("fxml/board8x8.fxml"));
        FXMLLoader board10x10_loader = new FXMLLoader(getClass().getResource("fxml/board10x10.fxml"));
        // initializing Scenes
        game_selection = new Scene(game_selection_loader.load());
        info_page = new Scene(info_page_loader.load());
        board8x8 = new Scene(board8x8_loader.load());
        board10x10 = new Scene(board10x10_loader.load());
        // getting Controllers
        Controller gameSelectionController = game_selection_loader.getController();
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
        stage.setResizable(false);
        stage.show();
        startReceiving();
    }

    /**
     * Main method only uses the launch() method
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Starts receiving from server daemon thread
     * calls {@link #handleServerCommand(String serverCommand)}
     */
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

    /**
     * Implements handling for commands sent by server
     * @param serverCommand
     */
    private void handleServerCommand(String serverCommand) {
        JSONObject json = new JSONObject(serverCommand);

        switch(json.getString("instruction")) {
            case "init" -> {
                player = json.getInt("value");
                board8x8Controller.setWhite(player==1);
                board10x10Controller.setWhite(player==1);
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
                boolean turn = json.getInt("player") == player;
                String message = json.getString("message");
                currentBoardController.moveRequest(turn, message);
            }
            case "create_board" -> {
                boolean leftDownCornerBlack = json.getBoolean("black");
                if(json.getInt("size")==8) {
                    changeScene(board8x8);
                    currentBoardController = board8x8Controller;
                }
                else {
                    changeScene(board10x10);
                    currentBoardController = board10x10Controller;
                }
                currentBoardController.setLeftDownCornerBlack(leftDownCornerBlack);
                currentBoardController.hideEndGameInfo();
            }
            case "draw_board" -> {
                String boardState = json.getString("board_state");
                currentBoardController.setBoardState(boardState);
            }
            case "end_game" -> {
                String winner = json.getString("winner");
                currentBoardController.endGame(json.getString(winner));
            }
            case "terminate" -> System.exit(0);
        }
    }

    /**
     * Changes scene using Platform.runLater()
     * @param scene scene to be set
     */
    private  void changeScene(Scene scene) {
        Platform.runLater(() -> stage.setScene(scene));
    }
}
