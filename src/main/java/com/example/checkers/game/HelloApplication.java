package com.example.checkers.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //launch();

        Variant variant = new Variant(8, true, true, true, false, true);

        System.out.println("Board created!\n");
        System.out.println("Board state: " + variant.getBoardState());

        if (variant.doMove(0, 0, 0, 1, Color.WHITE, Type.MAN)) {
            System.out.println("White made move!");
            System.out.println("Board state: " + variant.getBoardState());
        }

        System.out.println(variant.checkForWinningConditions());
        System.out.println("LeftDownCorner: " + variant.getLeftDownCornerBlack());

        if (variant.checkForWinningConditions() == Variant.Winner.NONE) {
            System.out.println("Winner: NONE");
        }
    }
}