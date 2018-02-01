package tests;

import javafx.geometry.Point2D;
import model.GameModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    GameModel gameModel;

    @BeforeEach
    void setUp() {
        gameModel = new GameModel();
    }

    @AfterEach
    void tearDown() {
        gameModel = null;
    }

    @Test
    void constructor() {
        for (int i= 0; i < gameModel.getObstacles().length; i++) {
            assertEquals(gameModel.NUM_OBSTACLES, gameModel.getObstacles()[i].length);
        }
    }

    @Test
    void rotate() {
        double rotation = gameModel.getCarRotation();
        // rotation witout acceleration (speed = 0) -> no rotation
        gameModel.rotateLeft(false);
        gameModel.updateCar(new Point2D(666,13));
        assertEquals(rotation, gameModel.getCarRotation());
        // rotate while acceleration
        gameModel.accelerate(false);
        gameModel.rotateLeft(false);
        gameModel.updateCar(new Point2D(666,13));
        assertNotEquals(rotation, gameModel.getCarRotation());
        // let go of rotation while acceleration should not change current rotation
        gameModel.accelerate(false);
        gameModel.rotateLeft(true);
        gameModel.updateCar(new Point2D(666,13));
        assertNotEquals(rotation, gameModel.getCarRotation());
    }

    @Test
    void accelerate() {
        double speed = gameModel.getCarSpeed();
        gameModel.accelerate(false);
        gameModel.updateCar(new Point2D(666,13));
        assertNotEquals(speed, gameModel.getCarSpeed());
        // let go of acceleration -> car should slow down because of air and friction
        gameModel.accelerate(true);
        gameModel.updateCar(new Point2D(666,13));
        assertNotEquals(speed, gameModel.getCarSpeed());
    }

    @Test
    void brake() {
        // braking while standing has no effect
        double speed = gameModel.getCarSpeed();
        gameModel.brake(false);
        gameModel.updateCar(new Point2D(666,13));
        assertEquals(speed, gameModel.getCarSpeed());
        // braking while riving (or rollling slows down the car)
        gameModel.accelerate(false);
        gameModel.updateCar(new Point2D(666,13));
        speed = gameModel.getCarSpeed();
        gameModel.brake(false);
        gameModel.updateCar(new Point2D(666,13));
        assertNotEquals(speed, gameModel.getCarSpeed());
    }

    @Test
    void hitObstacle() {
        // hitting an obstacle stops the car immediately
        gameModel.accelerate(false);
        gameModel.updateCar(new Point2D(666,13));
        gameModel.hitObstacle();
        assertEquals(0, gameModel.getCarSpeed());
    }
}