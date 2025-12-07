package com.example.scrapstation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        GameScene game = new GameScene(gc);

        Scene scene = new Scene(new StackPane(canvas));
        stage.setScene(scene);
        stage.setTitle("Scrap Station");
        stage.show();

        game.initInput(scene);

        GameLoop loop = new GameLoop(game);
        loop.start();
    }

    public static void main(String[] args) {
        launch();
    }
}
