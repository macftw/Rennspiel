package view;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import model.Obstacle;
import org.w3c.dom.css.Rect;

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
    private Rectangle[] obstacles;

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

    public void drawObstacles(Obstacle[] obstacles) {
        this.obstacles = new Rectangle[obstacles.length];
        for (int i = 0; i < obstacles.length; i++) {
            Rectangle rect = new Rectangle(obstacles[i].getX(), obstacles[i].getY(), obstacles[i].getWidth(), obstacles[i].getHeight());
            rect.setFill(Paint.valueOf("#FFFF00"));
            this.obstacles[i] = rect;
            gamePane.getChildren().add(rect);
        }
    }

    public void drawStartingLine(){
        Rectangle line = new Rectangle(650, 50, 5, 100);
        line.setFill(Paint.valueOf("FFFF00"));
        gamePane.getChildren().add(line);
    }

    public void drawCheckpoint() {
        Rectangle checkpoint = new Rectangle(650, 650, 5, 100);
        checkpoint.setFill(Paint.valueOf("#FFFF00"));
        gamePane.getChildren().add(checkpoint);
    }

    public void setCarRotation(double degrees) {
        this.carView.setRotation(degrees);
    }

    public Point2D setCarPosition(double delta) {
        return this.carView.setPosition(delta);
    }

    public void checkForCollision() {
        for (int i = 0; i < obstacles.length; i++) {
            Bounds bounds = obstacles[i].getBoundsInParent();
            if (bounds.intersects(carView.getBoundsInParent())) {
                obstacles[i].setFill(Paint.valueOf("FF0000"));
                System.out.println("CRASH");
                break;
            }
        }
    }

}