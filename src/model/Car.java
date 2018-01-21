package model;

/**
 * Class car represents the race-car in the race game.
 */
public class Car {

    final int weight = 1200;
    final double frontalArea = 2.19;
    final double airDragCoefficient = 0.28;
    final double  aerodynamicDrag = 1.204;
    final double minSpeed = 0;
    final double maxSpeed = 100;
    final double rotationRadius = 0.5;

    private double rotation;

    private int positionX;
    private int positionY;

    public double speed;
    public boolean writeOff;

    public Car() {

        rotation = 0;
        speed = 0;

    }

    public double getRotation() {
        return rotation;
    }

    public void rotateLeft(){
        rotation -= rotationRadius;
    }

    public void rotateRight(){
        rotation += rotationRadius;
    }
}
