package model;

import com.sun.javafx.geom.Point2D;

/**
 * Class car represents the race-car in the race game.
 */
public class Car {

    final int weight = 1200;
    final double frontalArea = 2.19;
    final double airDragCoefficient = 0.28;
    final double  aerodynamicDrag = 1.204;
    final double minSpeed = 0;
    final double maxSpeed = 200;
    final double accelaration = 1;
    final double brakeSpeed = 1;
    final double rotationRadius = 1;

    private double rotation;
    private double speed;
    private Point2D position;

    public boolean isOnTrack;
    public boolean writeOff;

    public enum AccelerationStatus {
        ACCELERATING, NONE, BRAKING
    }

    public enum RotationStatus {
        LEFT, NONE, RIGHT
    }

    public AccelerationStatus accelerationStatus;
    public RotationStatus rotationStatus;

    public Car() {
        isOnTrack = true;
        rotation = 0;
        speed = 0;
        accelerationStatus = AccelerationStatus.NONE;
        rotationStatus = RotationStatus.NONE;
    }

    public void updateValues(Point2D position) {
        this.position = position;
        switch (accelerationStatus) {
            case ACCELERATING:
                accelerate();
                break;
            case BRAKING:
                brake();
                break;
        }
        physics();

        switch (rotationStatus) {
            case LEFT:
                rotateLeft();
                break;
            case RIGHT:
                rotateRight();
                break;
        }
    }

    private void physics() {

    }

    private void rotateLeft(){
        rotation -= rotationRadius;

    }

    private void rotateRight(){
        rotation += rotationRadius;
    }

    private void accelerate(){
        if (speed < maxSpeed)
            speed += accelaration;
    }
    private void brake(){
        speed -= brakeSpeed;
        if (speed < 0)
            speed = 0;
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
}
