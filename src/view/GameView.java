package view;

import events.RaceEvent;
import javafx.event.*;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
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
        carView = new CarView(carStartX, carStartY);

        gamePane.setBackground(new Background(bg));
        gamePane.getChildren().add(carView);
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
                fireEvent(new RaceEvent(RaceEvent.CRASH));
                break;
            }
        }
    }

    public final <T extends Event> void addEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
        handlers.computeIfAbsent(eventType, (k) -> new ArrayList<>())
                .add(eventHandler);
    }

    public final <T extends Event> void removeEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
        handlers.computeIfPresent(eventType, (k, v) -> {
            v.remove(eventHandler);
            return v.isEmpty() ? null : v;
        });
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