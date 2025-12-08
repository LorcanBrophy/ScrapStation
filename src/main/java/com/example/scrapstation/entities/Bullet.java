package com.example.scrapstation.entities;

import com.example.scrapstation.Collidable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet implements Collidable {
    private double x, y;
    private final double width = 12;
    private final double height = 8;
    private final double speed = 8;
    private boolean dead = false;

    public Bullet(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        x += speed;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        gc.fillRect(x, y, width, height);
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}
