package model;

/**
 * The GameModel saves data about the game, including the racecar.
 * It handles most of the calculations for the racegame.
 */
public class GameModel {

    /**
     * The car that is driven on the racetrack
     */
    private Car car;

    /**
     * Creates a gameModel, that handles most of the actions
     */
    public GameModel() {
        //initialize Car, default data in GameView
        car = initializeCar();
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
}
