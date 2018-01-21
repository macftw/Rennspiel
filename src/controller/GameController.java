package controller;

import javafx.event.EventHandler;
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
        gameView.setCarRotation(gameModel.getCarRotation());
    }

    private void setUpInputHandler() {
        /*
         * Useful actions:
         * setOnKeyPressed, setOnKeyReleased
         */
        gameView.getScene().addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                setOnKeyPressed(event);
            }
        });
    }

    public void setOnKeyPressed(KeyEvent e){
        KeyCode keyCode = e.getCode();
        switch ( keyCode ) {
            case UP:
                gameModel.accelerate();
                break;
            case DOWN:
                gameModel.brake();
                break;
            case LEFT:
                gameModel.rotateLeft();
                break;
            case RIGHT:
                gameModel.rotateRight();
                break;
        }
    }
    public void setOnKeyReleased(KeyEvent e){
        /*TODO*/
    }
}
