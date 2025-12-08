package com.example.scrapstation.entities;

import com.example.scrapstation.Collidable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Ore implements Collidable {

    // fields
    private double x, y;
    private double width, height;
    private double speed;
    private double hp;
    private double value;
    private Image sprite;

    private boolean dead = false;

    // constructor
    public Ore(double x, double y, double width, double height, double speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    // getters
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
    public boolean isDead() {
        return dead;
    }

    // setters
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
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    // update
    public void update() {
        x -= speed;
    }

    // draw
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillRect(x, y, width, height);
    }

    public boolean isOffScreen() {
        return x + width < 0;
    }

}
