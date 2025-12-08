package com.example.scrapstation.entities;

import com.example.scrapstation.Collidable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player implements Collidable {

    private double x, y;
    private double speed = 2.5;

    private double width, height;

    private double hp = 3;

    public Player(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
    }

    public void move(double dx, double dy, double maxWidth, double maxHeight) {
        x += dx * speed;
        y += dy * speed;

        if (x < 0) x = 0;
        if (x > maxWidth - width) x = maxWidth - width;
        if (y < 0) y = 0;
        if (y > maxHeight - height) y = maxHeight - height;
    }

    public void update() {
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.CYAN);
        gc.fillRect(x, y, width, height);
    }

    // getters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getHp() {
        return hp;
    }

    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public void setHeight(double height) {
        this.height = height;
    }


    // methods
    public void takeDamage() {
        hp--;
        if (hp < 0) hp = 0;
    }
}