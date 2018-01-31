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

    private final double CRASH_SPEED = 100;
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
    private Text fpsLabel, timeLabel;
    private Rectangle startingLine, checkpoint;
    private Pane gamePane;
    private double time;

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
        timeLabel = new Text(1100, 30,"00:00");
        timeLabel.setFont(new Font(20));
        timeLabel.setFill(Color.YELLOW);
        time = 0;
        gamePane.getChildren().add(fpsLabel);
        gamePane.getChildren().add(timeLabel);
        rootPane.getChildren().add(gamePane);
    }

    /**
     * Sets up the
     *
     * @param title
     * @return
     */
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

    /**
     *
     * @param obstacles
     */
    public void drawObstacles(Obstacle[] obstacles) {
        Rectangle upperSafeArea = new Rectangle(620, 50, 90, 100);
        Rectangle lowerSafeArea = new Rectangle(620, 650, 60, 100);
        upperSafeArea.setFill(Color.TRANSPARENT);
        lowerSafeArea.setFill(Color.TRANSPARENT);
        gamePane.getChildren().add(upperSafeArea);
        gamePane.getChildren().add(lowerSafeArea);
        this.obstacles = new Rectangle[obstacles.length];
        for (int i = 0; i < obstacles.length; i++) {
            Rectangle rect = new Rectangle(obstacles[i].getX(), obstacles[i].getY(), obstacles[i].getWidth(), obstacles[i].getHeight());
            rect.setFill(Paint.valueOf("#FFFF00"));
            this.obstacles[i] = rect;
            gamePane.getChildren().add(rect);
            Bounds upperBounds = upperSafeArea.getBoundsInParent();
            Bounds lowerBounds = lowerSafeArea.getBoundsInParent();
            Bounds bounds = rect.getBoundsInParent();
            if (upperBounds.intersects(bounds) || lowerBounds.intersects(bounds)){
                gamePane.getChildren().remove(rect);
            }

        }
    }

    /**
     *
     */
    public void drawStartingLine(){
        startingLine = new Rectangle(650, 50, 5, 100);
        startingLine.setFill(Paint.valueOf("FF00FF"));
        gamePane.getChildren().add(startingLine);
    }

    /**
     *
     */
    public void drawCheckpoint() {
        checkpoint = new Rectangle(650, 650, 5, 100);
        checkpoint.setFill(Paint.valueOf("#FF00FF"));
        gamePane.getChildren().add(checkpoint);
    }

    /**
     *
     * @param degrees
     */
    public void setCarRotation(double degrees) {
        this.carView.setRotation(degrees);
    }

    /**
     *
     * @param delta
     * @return
     */
    public Point2D setCarPosition(double delta) {
        Point2D p =  this.carView.setPosition(delta);
        return p;
    }

    /**
     *
     * @param carSpeed
     */
    public void checkForCollision(double carSpeed) {
        for (int i = 0; i < obstacles.length; i++) {
            if (obstacles[i].getParent() == null)
                continue;
            Bounds bounds = obstacles[i].getBoundsInParent();
            if (bounds.contains(carView.getTopLeft())
//                    || bounds.contains(carView.getTopRight())
                    || bounds.contains(carView.getBottomLeft())) {
//                    || bounds.contains(carView.getBottomRight())) {
                obstacles[i].setFill(Paint.valueOf("FF0000"));
                if (carSpeed > CRASH_SPEED)
                    fireEvent(new RaceEvent(RaceEvent.CRASH));
                else
                    fireEvent(new RaceEvent(RaceEvent.OBSTACLE));
                break;
            }
        }
    }

    /**
     *
     * @param checkpointPassed
     */
    public void checkLines(boolean checkpointPassed) {
        Bounds checkpointBounds = checkpoint.getBoundsInParent();
        if (checkpointBounds.intersects(carView.getBoundsInParent())) {
            checkpoint.setFill(Color.BLUEVIOLET);
            fireEvent(new RaceEvent(RaceEvent.CHECKPOINT));
        }
        Bounds startingLineBounds = startingLine.getBoundsInParent();
        if (checkpointPassed && startingLineBounds.intersects(carView.getBoundsInParent())) {
            startingLine.setFill(Color.BLUEVIOLET);
            fireEvent(new RaceEvent(RaceEvent.FINISH));
        }else if (startingLineBounds.intersects(carView.getBoundsInParent())) {
            startingLine.setFill(Color.WHITE);
            fireEvent(new RaceEvent(RaceEvent.STARTINGLINE));

        }
    }

    /**
     *
     * @param fps
     */
    public void updateFpsLabel(int fps) {
        fpsLabel.setText(fps + " FPS");
    }

    /**
     *
     * @param delta
     */
    public void updateTimeLabel(double delta) {
        time += delta;
        int secs = (int) time;
        int mins = (int) (time/60);
        timeLabel.setText(toDoubleDigits(mins) + ":" + toDoubleDigits(secs));

    }

    /**
     *
     * @param x
     * @return
     */
    private String toDoubleDigits(int x) {
        if (x > 9)
            return "" + x;
        return "0" + x;
    }

    /**
     *
     * @param eventType
     * @param eventHandler
     * @param <T>
     */
    public final <T extends Event> void addEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
        handlers.computeIfAbsent(eventType, (k) -> new ArrayList<>()).add(eventHandler);
    }

    /**
     *
     * @param tail
     * @return
     */
    @Override
    public final EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return tail.prepend(this::dispatchEvent);
    }

    /**
     *
     * @param event
     * @param handlers
     */
    private void handleEvent(Event event, Collection<EventHandler> handlers) {
        if (handlers != null) {
            handlers.forEach(handler -> handler.handle(event));
        }
    }

    /**
     *
     * @param event
     * @param tail
     * @return
     */
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

    /**
     *
     * @param event
     */
    public void fireEvent(Event event) {
        Event.fireEvent(this, event);
    }

    /**
     *
     */
    public void reset() {
        menu = null;
        gamePane.getChildren().clear();
        rootPane.getChildren().clear();
        obstacles = null;
        gamePane = null;

        setUpGameWindow();
    }
}