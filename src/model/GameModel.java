package model;

import com.sun.javafx.geom.Ellipse2D;
import com.sun.javafx.geom.Point2D;
import controller.GameController;

/**
 * The GameModel saves data about the game, including the racecar.
 * It handles most of the calculations for the racegame.
 */
public class GameModel {

    /**
     * The car that is driven on the racetrack
     */
    private Car car;

    private Ellipse2D raceTrackInner, raceTrackOuter;
    /**
     * Creates a gameModel, that handles most of the actions
     */
    public GameModel() {
        //initialize Car, default data in GameView
        car = initializeCar();
        raceTrackInner = new Ellipse2D(200, 150, 900, 500);
        raceTrackOuter = new Ellipse2D(100, 50, 1100, 700);
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

    public void updateCar(Point2D newPos) {
        car.updateValues(newPos);
        boolean _temp = car.isOnTrack;
        car.isOnTrack = raceTrackOuter.contains(car.getPosition()) && !raceTrackInner.contains(car.getPosition());
        if (_temp != car.isOnTrack)
            System.out.println("Car on track: " + car.isOnTrack);
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

    public double getCarSpeed(){
        return car.getSpeed();
    }

}
