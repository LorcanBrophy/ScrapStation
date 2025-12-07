package com.example.scrapstation.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Obstacle {

    private double x, y;
    private final double width, height;
    private final double speed;
    private int health;
    private boolean alive = true;

    public Obstacle(double x, double y, double width, double height, double speed, int health) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.health = health;
    }

    public void update() {
        x -= speed;
        if (x + width < 0) alive = false;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.GRAY);
        gc.fillRect(x, y, width, height);
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) alive = false;
    }

    public boolean isAlive() { return alive; }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}
