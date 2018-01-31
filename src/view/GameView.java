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
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Obstacle;

import java.util.*;

/**
 * Contains every GUI element
 */
public class GameView implements EventTarget {

    private final double CRASH_SPEED = 100;
    public static final double SCREEN_WIDTH = 1300;
    public static final double SCREEN_HEIGHT = 800;
    private final double carStartX = 660;
    private final double carStartY = 91;

    //The scene where all is stacked up
    private Scene scene;

    // event handlers
    private final Map<EventType, Collection<EventHandler>> handlers = new HashMap<>();

    //Stackpane, where all dialogs are stacked
    private StackPane rootPane;
    private CarView carView;
    private Rectangle[][] obstacles;
    private Rectangle[] areas;
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

        areas = new Rectangle[4];
        for (int i = 0; i < 4; i++) {
            areas[i] = new Rectangle(
                    SCREEN_WIDTH / 2 * (i % 2),
                    SCREEN_HEIGHT / 2 * (i < 2 ? 0 : 1),
                    SCREEN_WIDTH / 2 + Obstacle.MAX_SIZE,
                    SCREEN_HEIGHT / 2 + Obstacle.MAX_SIZE);
            areas[i].setFill(Color.TRANSPARENT);
            areas[i].setStrokeWidth(5);
            gamePane.getChildren().add(areas[i]);
        }
//        areas[0].setStroke(Color.GREEN);
//        areas[1].setStroke(Color.RED);
//        areas[2].setStroke(Color.BLUE);
//        areas[3].setStroke(Color.ORANGE);
    }

    public boolean toggleMenu(String title, String message) {
        if (menu != null) {
            System.out.println("Hide menu");
            gamePane.getChildren().remove(menu);
            menu = null;
            return false;
        } else {
            System.out.println("Show menu");
            menu = new MenuView(SCREEN_WIDTH, SCREEN_HEIGHT, title, message);
            EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    event.consume();
                    menu.removeEventHandler(ActionEvent.ACTION,this);
                    fireEvent(new RaceEvent(RaceEvent.START));
                }
            };
            menu.addEventHandler(ActionEvent.ACTION, handler);
            gamePane.getChildren().add(menu);
            return true;
        }
    }

    public void drawObstacles(Obstacle[][] obstacles) {
        Rectangle upperSafeArea = new Rectangle(620, 50, 90, 100);
        Rectangle lowerSafeArea = new Rectangle(620, 650, 60, 100);
        Rectangle middleSafeArea = new Rectangle(300, 250, 700, 300);
        upperSafeArea.setFill(Color.TRANSPARENT);
        lowerSafeArea.setFill(Color.TRANSPARENT);
        middleSafeArea.setFill(Color.TRANSPARENT);
        gamePane.getChildren().add(upperSafeArea);
        gamePane.getChildren().add(lowerSafeArea);
        gamePane.getChildren().add(middleSafeArea);
        this.obstacles = new Rectangle[obstacles.length][];
//        Color[] colors = {Color.MAGENTA, Color.CYAN, Color.GREEN, Color.YELLOW};
        for (int i = 0; i < obstacles.length; i++) {
            List<Rectangle> _temp = new ArrayList<>();
            for (int j = 0; j < obstacles[i].length; j++) {
                Rectangle rect = new Rectangle(obstacles[i][j].getX(), obstacles[i][j].getY(), obstacles[i][j].getWidth(), obstacles[i][j].getHeight());
                rect.setFill(Color.CORAL);
                rect.setStrokeDashOffset(2);
                rect.setStrokeWidth(2);
                rect.setStroke(Color.RED);
                gamePane.getChildren().add(rect);
                Bounds upperBounds = upperSafeArea.getBoundsInParent();
                Bounds lowerBounds = lowerSafeArea.getBoundsInParent();
                Bounds middleBounds = middleSafeArea.getBoundsInParent();
                Bounds bounds = rect.getBoundsInParent();
                if (upperBounds.intersects(bounds) || lowerBounds.intersects(bounds) ||middleBounds.intersects(bounds)) {
                    gamePane.getChildren().remove(rect);
                } else {
                    _temp.add(rect);
                }
            }
            this.obstacles[i] = _temp.toArray(new Rectangle[_temp.size()]);
        }
    }

    public void drawStartingLine(){
        startingLine = new Rectangle(655, 50, 1, 100);
        startingLine.setFill(Color.TRANSPARENT);
        gamePane.getChildren().add(startingLine);
    }

    public void drawCheckpoint() {
        checkpoint = new Rectangle(655, 650, 1, 100);
        checkpoint.setFill(Color.TRANSPARENT);
        gamePane.getChildren().add(checkpoint);
    }

    public void setCarRotation(double degrees) {
        this.carView.setRotation(degrees);
    }

    public Point2D setCarPosition(double delta) {
        Point2D p =  this.carView.setPosition(delta);
        return p;
    }

    public void checkForCollision(double carSpeed) {
        boolean[] activeArea = new boolean[areas.length];
//        for(int k = 0; k < 4; k++) {
//            areas[k].setStroke(Color.WHITE);
//        }
        for (int area = 0; area < areas.length; area++) {
            //                areas[area].setStroke(Color.MAGENTA);
            activeArea[area] = areas[area].getBoundsInParent().contains(carView.getTopLeft())
                    || areas[area].getBoundsInParent().contains(carView.getTopRight())
                    || areas[area].getBoundsInParent().contains(carView.getBottomLeft())
                    || areas[area].getBoundsInParent().contains(carView.getBottomRight());
        }
        for (int i = 0; i < areas.length; i++) {
            if (!activeArea[i])
                continue;
            for (int j = 0; j < obstacles[i].length; j++) {
                if (obstacles[i][j].getParent() == null)
                    continue;
                Bounds bounds = obstacles[i][j].getBoundsInParent();
                if (bounds.contains(carView.getTopLeft())
                        || bounds.contains(carView.getTopRight())
                        || bounds.contains(carView.getBottomLeft())
                        || bounds.contains(carView.getBottomRight())) {
                    obstacles[i][j].setFill(Color.RED);
                    if (carSpeed > CRASH_SPEED) {
                        carView.explode();
                        fireEvent(new RaceEvent(RaceEvent.CRASH));
                    }
                    else
                        fireEvent(new RaceEvent(RaceEvent.OBSTACLE));
                    break;
                }
            }
        }
    }

    public void checkLines(boolean checkpointPassed) {
        Bounds checkpointBounds = checkpoint.getBoundsInParent();
        if (checkpointBounds.intersects(carView.getBoundsInParent())) {
            checkpoint.setFill(Color.BLUEVIOLET);
            fireEvent(new RaceEvent(RaceEvent.CHECKPOINT));
        }
        Bounds startingLineBounds = startingLine.getBoundsInParent();
        if (checkpointPassed && startingLineBounds.intersects(carView.getBoundsInParent())) {
            startingLine.setFill(Color.BLUEVIOLET);
            fireEvent(new RaceEvent(RaceEvent.FINISH, timeToFormattedString(time)));
        }else if (startingLineBounds.intersects(carView.getBoundsInParent())) {
            startingLine.setFill(Color.WHITE);
            fireEvent(new RaceEvent(RaceEvent.STARTINGLINE));

        }
    }

    public void updateFpsLabel(int fps) {
        fpsLabel.setText(fps + " FPS");
    }

    public void updateTimeLabel(double delta) {
        time += delta;
        timeLabel.setText(timeToFormattedString(time));
    }

    private String timeToFormattedString(double seconds) {
        int secs = (int) seconds;
        int mins = (int) (seconds/60);
        return toDoubleDigits(mins) + ":" + toDoubleDigits(secs);
    }

    private String toDoubleDigits(int x) {
        if (x > 9)
            return "" + x;
        return "0" + x;
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

    public void reset() {
        menu = null;
        gamePane.getChildren().clear();
        rootPane.getChildren().clear();
        obstacles = null;
        gamePane = null;

        setUpGameWindow();
    }
}