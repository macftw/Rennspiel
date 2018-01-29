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
    final double minSpeed = 0;
    final double maxSpeed = 400;
    final double maxAcceleration = 4;

    final double brakeSpeed = 1;
    final double rotationRadius = 1;
    final double g = 9.81;
    final double c1 = 0.015;
    final double c2 = 0.05;

    private double rotation;
    private double speed;
    private double acceleration;
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
        acceleration = 0;
        accelerationStatus = AccelerationStatus.NONE;
        rotationStatus = RotationStatus.NONE;
    }

    public void updateValues(Point2D position, double timeDifference) {
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
        physics(timeDifference);
        updateSpeed();

        switch (rotationStatus) {
            case LEFT:
                rotateLeft();
                break;
            case RIGHT:
                rotateRight();
                break;
        }
    }

    private void physics(double timeDifference) {
        double cR = isOnTrack ? c1 : c2;
//        double aMotor = accelerationStatus == AccelerationStatus.ACCELERATING ? 1 : 0;
//        speed -= aMotor -(((cR *g) + (airDragCoefficient * frontalArea * 0.5 *aerodynamicDrag * speed * speed))/weight)*timeDifference;


        double Fcar = weight * acceleration;
        double Fair = (airDragCoefficient * frontalArea * 0.5 * aerodynamicDrag * speed * speed);
        double Fr = weight * g * cR;
        acceleration = (Fcar - Fr - Fair) / weight;
    }

    private void rotateLeft(){
        rotation -= rotationRadius;

    }

    private void rotateRight(){
        rotation += rotationRadius;
    }

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
}
