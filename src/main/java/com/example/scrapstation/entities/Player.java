package com.example.scrapstation.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player {

    private double x, y;

    private int ammo = 20;
    private final int maxAmmo = 20;
    private boolean reloading = false;
    private long reloadStartTime;
    private final long reloadDuration = 2_000_000_000L;

    private int health = 3;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void move(double dx, double dy) {
        x += dx;
        y += dy;

        if (y < 0) y = 0;
        if (y > 600 - 40) y = 600 - 40;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(x, y, 40, 40);
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    // ammo/reload
    public boolean canShoot() { return ammo > 0 && !reloading; }
    public void useAmmo() { if (ammo > 0) ammo--; }
    public int getAmmo() { return ammo; }

    public void startReload() {
        if (!reloading && ammo < maxAmmo) {
            reloading = true;
            reloadStartTime = System.nanoTime();
        }
    }

    public void updateReload() {
        if (reloading) {
            long elapsed = System.nanoTime() - reloadStartTime;
            if (elapsed >= reloadDuration) {
                ammo = maxAmmo;
                reloading = false;
            }
        }
    }

    public boolean isReloading() {
        return reloading;
    }

    // health
    public void takeDamage(int damage) { health -= damage; }
    public int getHealth() { return health; }
    public boolean isDead() { return health <= 0; }
}