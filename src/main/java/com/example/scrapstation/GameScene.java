package com.example.scrapstation;

import com.example.scrapstation.entities.*;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.ArrayList;

public class GameScene {

    private final GraphicsContext gc;
    private final Player player;
    private final List<Bullet> bullets = new ArrayList<>();
    private final List<Obstacle> obstacles = new ArrayList<>();

    private boolean upPressed = false;
    private boolean downPressed = false;

    private long lastSpawnTime = 0;
    private final long spawnInterval = 1_000_000_000L; // 1 second
    private int score = 0;


    public GameScene(GraphicsContext gc) {
        this.gc = gc;
        this.player = new Player(100, 300);
    }

    public void initInput(Scene scene) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W) upPressed = true;
            if (e.getCode() == KeyCode.S) downPressed = true;
            if (e.getCode() == KeyCode.SPACE) shoot();
            if (e.getCode() == KeyCode.R) player.startReload();
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.W) upPressed = false;
            if (e.getCode() == KeyCode.S) downPressed = false;
        });
    }

    public void update() {
        if (player.isDead()) return;

        player.updateReload();

        float SPEED = 3;
        if (upPressed) player.move(0, -SPEED);
        if (downPressed) player.move(0, SPEED);

        bullets.forEach(Bullet::update);
        bullets.removeIf(b -> !b.isAlive());

        long now = System.nanoTime();
        if (now - lastSpawnTime > spawnInterval) {
            spawnObstacle();
            lastSpawnTime = now;
        }

        obstacles.forEach(Obstacle::update);
        obstacles.removeIf(o -> !o.isAlive());

        for (Bullet b : bullets) {
            for (Obstacle o : obstacles) {
                if (b.getX() < o.getX() + o.getWidth() &&
                        b.getX() + b.getWidth() > o.getX() &&
                        b.getY() < o.getY() + o.getHeight() &&
                        b.getY() + b.getHeight() > o.getY()) {

                    o.takeDamage(b.getDamage());
                    b.setAlive(false);
                    if (!o.isAlive()) score += 10;
                }
            }
        }

        for (Obstacle o : obstacles) {
            if (player.getX() < o.getX() + o.getWidth() &&
                    player.getX() + 40 > o.getX() &&
                    player.getY() < o.getY() + o.getHeight() &&
                    player.getY() + 40 > o.getY()) {

                player.takeDamage(1);
                o.takeDamage(1);
            }
        }
    }

    public void render() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 800, 600);

        player.draw(gc);
        bullets.forEach(b -> b.draw(gc));
        obstacles.forEach(o -> o.draw(gc));

        gc.setFill(Color.WHITE);
        if (player.isReloading()) {
            gc.fillText("Reloading...", 10, 20);
        } else {
            gc.fillText("Ammo: " + player.getAmmo(), 10, 20);
        }
        gc.fillText("Health: " + player.getHealth(), 10, 40);
        gc.fillText("Score: " + score, 10, 60);

        if (player.isDead()) {
            gc.setFill(Color.RED);
            gc.fillText("GAME OVER", 350, 300);
        }
    }

    private void shoot() {
        if (player.canShoot()) {
            bullets.add(new Bullet(player.getX() + 40, player.getY() + 18, new Point2D(1, 0), 10, 1));
            player.useAmmo();
        }
    }

    private void spawnObstacle() {
        double width = 40 + Math.random() * 30;
        double height = 40 + Math.random() * 30;
        double y = Math.random() * (600 - height);
        double speed = 3 + Math.random() * 2;
        int health = 1;

        obstacles.add(new Obstacle(800, y, width, height, speed, health));
    }
}