package model;


import javafx.geometry.Point2D;

/**
 * Class car represents the race-car in the race game.
 */
public class Car {

    final int weight = 1200;
    final double frontalArea = 2.19;
    final double airDragCoefficient = 0.28;
    final double  aerodynamicDrag = 1.204;
    final double maxSpeed = 200;
    final double maxAcceleration = 5;

    final double brakeSpeed = 1;
    final double rotationRadius = 1;
    final double g = 9.81;
    final double c1 = 0.015;
    final double c2 = 0.25;

    private double rotation;
    private double speed;
    private double acceleration;
    private Point2D position;


    public boolean isOnTrack;

    public enum AccelerationStatus {
        ACCELERATING, NONE, BRAKING
    }

    public enum RotationStatus {
        LEFT, NONE, RIGHT
    }

    public AccelerationStatus accelerationStatus;
    public RotationStatus rotationStatus;
    /**
     * Represents a race car
     */
    public Car() {
        isOnTrack = true;
        rotation = 0;
        speed = 0;
        acceleration = 0;
        accelerationStatus = AccelerationStatus.NONE;
        rotationStatus = RotationStatus.NONE;
    }
    /**
     * Is called on eace frame to calculate the cars speed and rotation angle based
     * on the current acceleration and rotation.
     *
     * @param position the current position of the car on the screen
     */
    public void updateValues(Point2D position) {
        this.position = position;
        switch (accelerationStatus) {
            case ACCELERATING:
                acceleration = maxAcceleration;
                break;
            case BRAKING:
                acceleration = -maxAcceleration;
                break;
            case NONE:
                acceleration = 0;
                break;
        }
        physics();
        updateSpeed();
        if (speed == 0)
            return;

        switch (rotationStatus) {
            case LEFT:
                rotateLeft();
                break;
            case RIGHT:
                rotateRight();
                break;
        }
    }

    /**
     * Sets up a Physics Class to calculate the Air Resistance and the Rolling Resistanse on the track.
     * and off the track with the speed
     */
    private void physics() {
        double cR = isOnTrack ? c1 : c2;
//        double aMotor = accelerationStatus == AccelerationStatus.ACCELERATING ? 1 : 0;
//        speed -= aMotor -(((cR *g) + (airDragCoefficient * frontalArea * 0.5 *aerodynamicDrag * speed * speed))/weight)*timeDifference;


        double Fcar = weight * acceleration;
        double Fair = (airDragCoefficient * frontalArea * 0.5 * aerodynamicDrag * speed * speed);
        double Fr = weight * g * cR;
        acceleration = (Fcar - Fr - Fair) / weight;
    }

    /**
     * Calculates the Rotation with a negative Rotation Radius for a left Turn of the Car
     */
    private void rotateLeft(){
        rotation -= rotationRadius;
    }

    /**
     * Calculates the Rotation with a negative Rotation Radius for a right Turn of the Car
     */
    private void rotateRight(){
        rotation += rotationRadius;
    }

    /**
     * Calculates the Speed with the Accelaration. It can not get lower 0 and above maxSpeed.
     */
    private void updateSpeed(){
        speed += acceleration;
        if (speed < 0)
            speed = 0;
        if (speed > maxSpeed)
            speed = maxSpeed;
    }

    public double getSpeed(){
        return speed;
    }

    public double getRotation() {
        return rotation;
    }

    public Point2D getPosition() {
        return position;
    }

    /**
     * Sets speed to 0 every time a obstacle is being hit
     */
    public void hitObstacle() {
        speed = 0;
    }
}
