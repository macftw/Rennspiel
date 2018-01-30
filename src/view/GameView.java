package view;

import events.RaceEvent;
import javafx.event.*;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Obstacle;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains every GUI element
 */
public class GameView implements EventTarget {

    private final double SCREEN_WIDTH = 1300;
    private final double SCREEN_HEIGHT = 800;
    private final double carStartX = 660;
    private final double carStartY = 91;

    //The scene where all is stacked up
    private Scene scene;

    // event handlers
    private final Map<EventType, Collection<EventHandler>> handlers = new HashMap<>();

    //Stackpane, where all dialogs are stacked
    private StackPane rootPane;
    private CarView carView;
    private Rectangle[] obstacles;
    private MenuView menu;
    private Text fpsLabel;
    private Rectangle startingLine, checkpoint;
    private Pane gamePane;
    private Rectangle tl, tr, bl, br;

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
        scene = new Scene(rootPane, SCREEN_WIDTH, SCREEN_HEIGHT);

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

        drawStartingLine();
        drawCheckpoint();

        carView = new CarView(carStartX, carStartY);

        gamePane.setBackground(new Background(bg));
        gamePane.getChildren().add(carView);

        fpsLabel = new Text(1200, 30,"0 FPS");
        fpsLabel.setFont(new Font(20));
        fpsLabel.setFill(Color.YELLOW);
        gamePane.getChildren().add(fpsLabel);
        rootPane.getChildren().add(gamePane);
    }

    public boolean toggleMenu(String title) {
        if (menu != null) {
            System.out.println("Hide menu");
            gamePane.getChildren().remove(menu);
            menu = null;
            return false;
        } else {
            System.out.println("Show menu");
            menu = new MenuView(SCREEN_WIDTH, SCREEN_HEIGHT, title);
            menu.addEventHandler(ActionEvent.ACTION, event -> {
                fireEvent(new RaceEvent(RaceEvent.START));
            });
            gamePane.getChildren().add(menu);
            return true;
        }
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
        startingLine = new Rectangle(650, 50, 5, 100);
        startingLine.setFill(Paint.valueOf("FF00FF"));
        gamePane.getChildren().add(startingLine);
    }

    public void drawCheckpoint() {
        checkpoint = new Rectangle(650, 650, 5, 100);
        checkpoint.setFill(Paint.valueOf("#FF00FF"));
        gamePane.getChildren().add(checkpoint);
    }

    public void setCarRotation(double degrees) {
        this.carView.setRotation(degrees);
    }

    public Point2D setCarPosition(double delta) {
        Point2D p =  this.carView.setPosition(delta);
        return p;
    }

    public void checkForCollision(boolean checkpointPassed) {
        for (int i = 0; i < obstacles.length; i++) {
            Bounds bounds = obstacles[i].getBoundsInParent();
            if (bounds.contains(carView.getTopLeft())
                    || bounds.contains(carView.getTopRight())
                    || bounds.contains(carView.getBottomLeft())
                    || bounds.contains(carView.getBottomRight())) {
                obstacles[i].setFill(Paint.valueOf("FF0000"));
                fireEvent(new RaceEvent(RaceEvent.CRASH));
                break;
            }
//            if (bounds.intersects(carView.getBoundsInParent())) {
//                obstacles[i].setFill(Paint.valueOf("FF0000"));
//                fireEvent(new RaceEvent(RaceEvent.CRASH));
//                break;
//            }
        }
        Bounds checkpointBounds = checkpoint.getBoundsInParent();
        if (checkpointBounds.intersects(carView.getBoundsInParent())) {
            checkpoint.setFill(Color.BLUEVIOLET);
            fireEvent(new RaceEvent(RaceEvent.CHECKPOINT));
        }
        Bounds startingLineBounds = startingLine.getBoundsInParent();
        if (checkpointPassed && startingLineBounds.intersects(carView.getBoundsInParent())) {
            startingLine.setFill(Color.BLUEVIOLET);
            fireEvent(new RaceEvent(RaceEvent.FINISH));
        }
    }

    public void updateFpsLabel(int fps) {
        fpsLabel.setText(fps + " FPS");
    }

    public final <T extends Event> void addEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
        handlers.computeIfAbsent(eventType, (k) -> new ArrayList<>()).add(eventHandler);
    }


    @Override
    public final EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return tail.prepend(this::dispatchEvent);
    }

    private void handleEvent(Event event, Collection<EventHandler> handlers) {
        if (handlers != null) {
            handlers.forEach(handler -> handler.handle(event));
        }
    }

    private Event dispatchEvent(Event event, EventDispatchChain tail) {
        // go through type hierarchy and trigger all handlers
        EventType type = event.getEventType();
        while (type != Event.ANY) {
            handleEvent(event, handlers.get(type));
            type = type.getSuperType();
        }
        handleEvent(event, handlers.get(Event.ANY));
        return event;
    }

    public void fireEvent(Event event) {
        Event.fireEvent(this, event);
    }
}