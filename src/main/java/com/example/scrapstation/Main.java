package com.example.scrapstation;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        GameScene game = new GameScene();

        stage.setScene(game.getScene());
        stage.setTitle("Scrap Station");
        stage.show();

        GameLoop loop = new GameLoop(game);
        loop.start();
    }

    public static void main(String[] args) {
        launch();
    }
}