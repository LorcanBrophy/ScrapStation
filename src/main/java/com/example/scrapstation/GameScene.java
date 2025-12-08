package com.example.scrapstation;

import com.example.scrapstation.entities.Bullet;
import com.example.scrapstation.entities.Ore;
import com.example.scrapstation.entities.Player;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScene {
    private Scene scene;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private Player player;

    private final ArrayList<Ore> ores = new ArrayList<>();

    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private boolean shooting = false;
    private long lastShotTime = 0;
    private final long shotCooldown = 400;

    private boolean up, down, left, right;

    public GameScene() {
        this.canvas = new Canvas();
        this.gc = canvas.getGraphicsContext2D();

        setupScene();

        initPlayer();
        initInput();

        addResizeListeners();
    }

    private void setupScene() {
        StackPane root = new StackPane(canvas);
        scene = new Scene(root, 1400, 800);

        canvas.widthProperty().bind(scene.widthProperty());
        canvas.heightProperty().bind(scene.heightProperty());
    }

    private void addResizeListeners() {
        canvas.widthProperty().addListener((_, _, _) -> scalePlayer());
        canvas.heightProperty().addListener((_, _, _) -> scalePlayer());
    }

    public Scene getScene() {
        return scene;
    }

    private void initPlayer() {
        player = new Player(100, 100, 60, 60);
        scalePlayer();
    }

    private void scalePlayer() {
        if (player == null) return;

        double playerWidth = canvas.getWidth() / 20;
        double playerHeight = playerWidth * 0.8;

        double playerX = canvas.getWidth() * 0.05;
        double playerY = (canvas.getHeight() - playerHeight) / 2;

        player.setWidth(playerWidth);
        player.setHeight(playerHeight);

        player.setX(playerX);
        player.setY(playerY);
    }

    private void initInput() {

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W) up = true;
            if (e.getCode() == KeyCode.S) down = true;
            if (e.getCode() == KeyCode.A) left = true;
            if (e.getCode() == KeyCode.D) right = true;
            if (e.getCode() == KeyCode.SPACE) shooting = true;
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.W) up = false;
            if (e.getCode() == KeyCode.S) down = false;
            if (e.getCode() == KeyCode.A) left = false;
            if (e.getCode() == KeyCode.D) right = false;
            if (e.getCode() == KeyCode.SPACE) shooting = false;
        });
    }

    // update
    private boolean running = true;

    public void update() {
        if (!running) return;
        updatePlayer();
        spawnOres();
        updateOres();
        updateBullets();
        checkCollisions();
    }

    private void updatePlayer() {
        double dx = 0, dy = 0;
        if (up) dy -= 1;
        if (down) dy += 1;
        if (left) dx -= 1;
        if (right) dx += 1;

        double baseSpeed = 1.5;
        double scaledSpeed = baseSpeed * (canvas.getWidth() / 1400.0);

        player.move(dx * scaledSpeed, dy * scaledSpeed, canvas.getWidth(), canvas.getHeight());
        player.update();

        if (player.getHp() <= 0) {
            handlePlayerDeath();
        }
    }

    private void updateOres() {
        for (int i = ores.size() - 1; i >= 0; i--) {
            Ore ore = ores.get(i);
            ore.update();

            if (ore.isOffScreen()) {
                ores.remove(i);
            }
        }
    }

    private void updateBullets() {
        if (shooting && System.currentTimeMillis() - lastShotTime > shotCooldown) {
            bullets.add(new Bullet(player.getX() + player.getWidth(), player.getY() + player.getHeight() / 2));
            lastShotTime = System.currentTimeMillis();
        }

        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update();
            if (bullet.getX() > canvas.getWidth()) bullets.remove(i);

        }
    }

    // render
    public void render() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        player.draw(gc);

        for (Ore ore : ores) {
            ore.draw(gc);
        }

        for (Bullet bullet : bullets) {
            bullet.draw(gc);
        }
    }

    public void handlePlayerDeath() {
        System.out.println("You Died");
        running = false;

        gc.setFill(Color.RED);
        gc.fillText("GAME OVER", canvas.getWidth() / 2 - 50, canvas.getHeight() / 2);
    }

    private long lastSpawnTime = 0;

    private void spawnOres() {
        long now = System.currentTimeMillis();

        long spawnInterval = 800; // ms
        if (now - lastSpawnTime >= spawnInterval) {
            double oreWidth = 50 + Math.random() * 100;   // 50–150
            double oreHeight = 50 + Math.random() * 120;  // 50–170

            double x = canvas.getWidth();
            double y = Math.random() * (canvas.getHeight() - oreHeight);

            double minSpeed = canvas.getWidth() * 0.003;
            double maxSpeed = canvas.getWidth() * 0.005;
            double speed = minSpeed + Math.random() * (maxSpeed - minSpeed);

            ores.add(new Ore(x, y, oreWidth, oreHeight, speed));
            lastSpawnTime = now;
        }
    }

    private void checkCollisions() {
        for (Ore ore : ores) {
            if (aabb(player, ore)) {
                player.takeDamage();
                ore.setDead(true);
            }
        }

        for (Bullet bullet : bullets) {
            for (Ore ore : ores) {
                if (aabb(bullet, ore)) {
                    ore.setDead(true);
                    bullet.setDead(true);
                }
            }
        }

        bullets.removeIf(Bullet::isDead);
        ores.removeIf(Ore::isDead);
    }

    private boolean aabb(Collidable a, Collidable b) {
        return a.getX() < b.getX() + b.getWidth() &&
                a.getX() + a.getWidth() > b.getX() &&
                a.getY() < b.getY() + b.getHeight() &&
                a.getY() + a.getHeight() > b.getY();
    }
}