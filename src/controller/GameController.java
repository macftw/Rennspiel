package controller;

import javafx.scene.Scene;
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

    }

    private void setUpInputHandler() {
        /*
         * Useful actions:
         * setOnKeyPressed, setOnKeyReleased
         */
    }
}
