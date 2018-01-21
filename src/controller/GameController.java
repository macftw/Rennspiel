package controller;

import javafx.scene.Scene;
import model.*;
import view.GameView;

import java.awt.event.KeyEvent;

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

    public void setOnKeyPressed(KeyEvent e){
        int keyCode = e.getKeyCode();
        switch ( keyCode ) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
                gameModel.rotateLeft();
            case KeyEvent.VK_RIGHT:
                gameModel.rotateRight();
        }
    }
    public void setOnKeyReleased(){
        /*TODO*/
    }
}
