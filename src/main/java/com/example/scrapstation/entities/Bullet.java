package com.example.scrapstation.entities;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet {

    private double x, y;
    private final double width = 8, height = 4;
    private final double speed;
    private final Point2D direction;
    private boolean alive = true;
    private final int damage;

    public Bullet(double x, double y, Point2D direction, double speed, int damage) {
        this.x = x;
        this.y = y;
        this.direction = direction.normalize();
        this.speed = speed;
        this.damage = damage;
    }

    public void update() {
        x += direction.getX() * speed;
        y += direction.getY() * speed;

        if (x > 800 || x < 0 || y > 600 || y < 0) alive = false;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        gc.fillRect(x, y, width, height);
    }

    public boolean isAlive() { return alive; }
    public void setAlive(boolean alive) { this.alive = alive; }
    public int getDamage() { return damage; }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}