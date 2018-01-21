package view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Contains every GUI element
 */
public class GameView {

    //The scene where all is stacked up
    private Scene scene;

    //Stackpane, where all dialogs are stacked
    private StackPane rootPane;

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
        Text text = new Text("Rennspiel");
        text.setLayoutX(100);
        text.setLayoutY(100);
        gamePane.getChildren().add(text);
        rootPane.getChildren().add(gamePane);
    }
}