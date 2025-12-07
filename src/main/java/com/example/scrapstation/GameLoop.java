package com.example.scrapstation;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {

    private final GameScene game;

    public GameLoop(GameScene game) {
        this.game = game;
    }

    @Override
    public void handle(long now) {
        game.update();
        game.render();
    }
}