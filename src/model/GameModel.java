package model;

import com.sun.javafx.geom.Ellipse2D;
import javafx.geometry.Point2D;

/**
 * The GameModel saves data about the game, including the racecar.
 * It handles most of the calculations for the racegame.
 */
public class GameModel {
    final int NUM_OBSTACLES = 100;
    /**
     * The car that is driven on the racetrack
     */
    private Car car;
    private Obstacle[] obstacles;
    private Ellipse2D raceTrackInner, raceTrackOuter;

    public boolean checkpointPassed;
    public boolean startingLinePassed;
    public boolean gamePaused;
    /**
     * Creates a gameModel, that handles most of the actions
     */
    public GameModel() {
        //initialize Car, default data in GameView
        car = initializeCar();
        raceTrackInner = new Ellipse2D(200, 150, 900, 500);
        raceTrackOuter = new Ellipse2D(100, 50, 1100, 700);
        generateObstacles();
        checkpointPassed = false;
        startingLinePassed = false;
    }

    /**
     * Initializes a car with the initial values
     *
     * @return the initialized car
     */
    private Car initializeCar() {
        //initialize a new car and give it the init values set in the static variables
        car = new Car();
        return car;
    }

    public void updateCar(Point2D newPos, double timeDifference) {
        car.updateValues(newPos, timeDifference);
        car.isOnTrack = raceTrackOuter.contains((float)car.getPosition().getX(), (float)car.getPosition().getY())
                && !raceTrackInner.contains((float)car.getPosition().getX(), (float)car.getPosition().getY());
//        car.isOnTrack = raceTrackOuter.contains(car.getPosition()) && !raceTrackInner.contains(car.getPosition());
    }

    public void rotateLeft(boolean release){
        car.rotationStatus = release ? Car.RotationStatus.NONE : Car.RotationStatus.LEFT;
    }
    public void rotateRight(boolean release){
        car.rotationStatus = release ? Car.RotationStatus.NONE : Car.RotationStatus.RIGHT;
    }
    public double getCarRotation(){
        return car.getRotation();
    }

    public void accelerate(boolean release){
        car.accelerationStatus = release ? Car.AccelerationStatus.NONE : Car.AccelerationStatus.ACCELERATING;
    }

    public void brake(boolean release){
        car.accelerationStatus = release ? Car.AccelerationStatus.NONE : Car.AccelerationStatus.BRAKING;
    }

    public void hitObstacle() {
        car.hitObstacle();
    }

    public double getCarSpeed(){
        return car.getSpeed();
    }

    /**
     *
     */
    private void generateObstacles() {
        obstacles = new Obstacle[NUM_OBSTACLES];
        for (int i = 0; i < NUM_OBSTACLES; i++) {
            obstacles[i] = new Obstacle(50, 1250, 50, 750);
        }
    }

    public Obstacle[] getObstacles() {
        return obstacles;
    }

    public void reset() {
        car = initializeCar();
        raceTrackInner = new Ellipse2D(200, 150, 900, 500);
        raceTrackOuter = new Ellipse2D(100, 50, 1100, 700);
        generateObstacles();
        checkpointPassed = false;
        startingLinePassed = false;
    }
}
