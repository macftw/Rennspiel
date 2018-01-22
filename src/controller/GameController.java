package controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.*;
import view.GameView;

public class GameController {

    private GameModel gameModel;
    private GameView gameView;
    private Scene scene;

    public GameController(GameModel gameModel, GameView gameView) {
        this.gameView = gameView;
        this.gameModel = gameModel;
        this.scene = gameView.getScene();
        //Set up keylistener
        setUpInputHandler();
    }

    /**
     * Updates all needed dependencies every frame
     *
     * @param timeDifferenceInSeconds the time passed since last frame
     */
    public void updateContinuously(double timeDifferenceInSeconds) {
        gameModel.updateCar(gameView.setCarPosition(gameModel.getCarSpeed() * timeDifferenceInSeconds));
        gameView.setCarRotation(gameModel.getCarRotation());
    }

    private void setUpInputHandler() {
        /*
         * Useful actions:
         * setOnKeyPressed, setOnKeyReleased
         */
        this.scene.addEventHandler(KeyEvent.KEY_PRESSED, this::setOnKeyPressed);

        this.scene.addEventHandler(KeyEvent.KEY_RELEASED, this::setOnKeyReleased);
    }

    private void setOnKeyPressed(KeyEvent e){
        KeyCode keyCode = e.getCode();
        switch ( keyCode ) {
            case UP:
                gameModel.accelerate(false);
                break;
            case DOWN:
                gameModel.brake(false);
                break;
            case LEFT:
                gameModel.rotateLeft(false);
                break;
            case RIGHT:
                gameModel.rotateRight(false);
                break;
        }
    }
    private void setOnKeyReleased(KeyEvent e){
        KeyCode keyCode = e.getCode();
        switch ( keyCode ) {
            case UP:
                gameModel.accelerate(true);
                break;
            case DOWN:
                gameModel.brake(true);
                break;
            case LEFT:
                gameModel.rotateLeft(true);
                break;
            case RIGHT:
                gameModel.rotateRight(true);
                break;
        }
    }
}
