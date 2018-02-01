package model;

import javafx.stage.Screen;
import view.GameView;

/**
 * Represents the position and size of the Obstacle on the screen
 */
public class Obstacle {

    private static final double MIN_SIZE = 10;
    public static final double MAX_SIZE = 30;
    private double x, y, width, height;
    private final double mid_x = GameView.SCREEN_WIDTH / 2;
    private final double mid_y = GameView.SCREEN_HEIGHT / 2;
    private final double x_padding = 50;
    private final double y_padding = 50;

    /**
     * Sets up the random size and position of the Obstacles
     *
     * @param area area of the screen in which the obstacle is being placed
     *             0 || 1
     *             2 || 3
     */
    Obstacle(int area) {
        this.width = MIN_SIZE + MAX_SIZE * Math.random();
        this.height = MIN_SIZE + MAX_SIZE * Math.random();
        double min_x =  area % 2 == 1 ? mid_x : x_padding;
        double min_y =  area < 2 ? y_padding : mid_y;
        double max_x = (mid_x + (area % 2 == 1 ? mid_x : 0)) - x_padding;
        double max_y = (mid_y + (area < 2 ? 0 : mid_y)) - y_padding;
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
