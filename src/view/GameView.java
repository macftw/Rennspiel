package view;

import com.sun.javafx.geom.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Contains every GUI element
 */
public class GameView {

    private final double carStartX = 660;
    private final double carStartY = 91;

    //The scene where all is stacked up
    private Scene scene;

    //Stackpane, where all dialogs are stacked
    private StackPane rootPane;
    private CarView carView;

    private Pane gamePane;
    public Scene getScene() {
        return scene;
    }


    /**
     * GameView object for setting up the GUI
     *
     * @param stage the primary stage
     */
    public GameView(Stage stage) {

        stage.setTitle("Rennspiel");
        stage.setResizable(false);
        stage.sizeToScene();

        rootPane = new StackPane();
        scene = new Scene(rootPane, 1300, 800);

        setUpGameWindow();

        stage.setScene(scene);
    }

    /**
     * Sets up the main game window with the course as panebackground,
     * the car in the initial Position
     */
    private void setUpGameWindow() {
        gamePane = new Pane();

        Image raceTrackImg = new Image("resources/race-track.png");
        BackgroundImage bg = new BackgroundImage(raceTrackImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        carView = new CarView(carStartX, carStartY);

        gamePane.setBackground(new Background(bg));
        gamePane.getChildren().add(carView);
        rootPane.getChildren().add(gamePane);
    }

    public void setCarRotation(double degrees) {
        this.carView.setRotation(degrees);
    }

    public Point2D setCarPosition(double delta) {
        return this.carView.setPosition(delta);
    }
}