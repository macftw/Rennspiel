package controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.*;
import view.GameView;
import events.RaceEvent;

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
        gameView.drawObstacles(gameModel.getObstacles());
        gameView.drawStartingLine();
        gameView.drawCheckpoint();
        gameView.addEventHandler(RaceEvent.START,event -> {
            gameModel.gamePaused = gameView.toggleMenu("Pause");
        });
        gameView.addEventHandler(RaceEvent.CRASH,event -> {
            gameModel.gamePaused = gameView.toggleMenu("Game over!");
        });
        gameModel.gamePaused = gameView.toggleMenu("Rennspiel");
    }

    /**
     * Updates all needed dependencies every frame
     *
     * @param timeDifferenceInSeconds the time passed since last frame
     */
    public void updateContinuously(double timeDifferenceInSeconds) {
        if (gameModel.gamePaused)
            return;
        gameModel.updateCar(gameView.setCarPosition(gameModel.getCarSpeed() * timeDifferenceInSeconds), timeDifferenceInSeconds);
        gameView.setCarRotation(gameModel.getCarRotation());
        gameView.checkForCollision();
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
            case P:
                gameModel.gamePaused = gameView.toggleMenu("Pause");
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
