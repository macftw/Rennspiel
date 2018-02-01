package tests;

import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import view.GameView;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TestFX wirft Fehler
 */
@ExtendWith(ApplicationExtension.class)
class GameViewTest{

    GameView gameView;

    @Start
    void onStart(Stage stage) {
        gameView = new GameView(stage);
    }

    @AfterEach
    void tearDown() {
        gameView = null;
    }

//    @Test
//    void toggleMenu() {
//        assertNotEquals(gameView.toggleMenu("", ""), gameView.toggleMenu("",""));
//    }
}