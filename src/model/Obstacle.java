package model;

import javafx.scene.shape.Rectangle;

public class Obstacle {

    final double MIN_WIDTH = 10;
    final double MIN_HEIGHT = 10;
    final double MAX_WIDTH = 30;
    final double MAX_HEIGHT = 30;
    private double x, y, width, height;

    public Obstacle(int min_x, int max_x, int min_y, int max_y) {
        this.width = MIN_WIDTH + MAX_WIDTH * Math.random();
        this.height = MIN_HEIGHT + MAX_HEIGHT * Math.random();
        this.x = min_x + max_x * Math.random();
        this.y = min_y + max_y * Math.random();
    }

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
}
