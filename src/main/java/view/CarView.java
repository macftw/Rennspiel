package view;

import javafx.animation.Transition;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of the RaceCar
 */
public class CarView extends Region {

    private final String imgUrl = "car.png";
    public double imageWidth, imageHeight;
    private Rotate rotation;
    private Translate translate;
    private List<Image> explosionImages;
    private Transition explosionAnimation;
    private ImageView imgView, explosionView;

    /**
     * Instantiates a new carView at the specified position
     *
     * @param startX x coordinate of the position
     * @param startY y coordinate of the position
     */
    public CarView(double startX, double startY) {
        super();
        imgView = new ImageView();
        Image carImg = new Image(imgUrl);
        imgView.setImage(carImg);
        imageWidth = carImg.getWidth();
        imageHeight = carImg.getHeight();

        getChildren().add(imgView);


        translate = new Translate(startX, startY);
        getTransforms().add(translate);

        rotation = new Rotate(0);
        rotation.setPivotX(imageWidth / 2);
        rotation.setPivotY(imageHeight / 2);
//        rotation.pivotXProperty().bind(Bindings.add(imageWidth / 2, collisionRect.xProperty()));
//        rotation.pivotYProperty().bind(Bindings.add(imageHeight / 2, collisionRect.yProperty()));
        getTransforms().add(rotation);

        explosionView = new ImageView();
        explosionImages = new ArrayList<>();
        String baseUrl = "explosion/expl_";
        for (int i = 0; i < 15; i++) {
            explosionImages.add(new Image(baseUrl + i + ".png"));
        }
        explosionAnimation = new Transition() {
            {
                setCycleDuration(Duration.millis(1000)); // total time for animation
            }

            @Override
            protected void interpolate(double fraction) {
                int index = (int) (fraction*(explosionImages.size()-1));
                explosionView.setImage(explosionImages.get(index));
            }
        };
    }
    /**
     * Rotates the carView by the specified angle
     *
     * @param degrees angle
     */
    public void setRotation(double degrees) {
//        rotation.setPivotX(getPosition().getX());
//        rotation.setPivotY(getPosition().getY());
        rotation.setAngle(degrees);
    }

    /**
     * Translates the carView into its current direction
     *
     * @param delta the magnitude of the translation vector
     * @return the new position
     */
    public Point2D setPosition(double delta) {
        double x = -Math.cos(rotation.getAngle() * Math.PI / 180) * delta;
        double y = -Math.sin(rotation.getAngle() * Math.PI / 180) * delta;
        translate.setX(translate.getX() + x);
        translate.setY(translate.getY() + y);
        return new Point2D((float)translate.getX(), (float)translate.getY());
    }

    /**
     * Gets the corner points of the car for CollisionDetection
     *
     * @return corner points of the car for CollisionDetection
     */
    public Point2D getTopLeft() {
        return localToScene(Point2D.ZERO);
    }

    public Point2D getTopRight() {
        return localToScene(new Point2D(imageWidth / 2, 0));
    }

    public Point2D getBottomLeft() {
        return localToScene(new Point2D(0, imageHeight));
    }

    public Point2D getBottomRight() {
        return localToScene(new Point2D(imageWidth / 2, imageHeight));
    }

    /**
     * Removes the image of the car and sets up an animation with an explosion
     */
    public void explode() {
        getChildren().remove(imgView);
        rotation.setAngle(0);
        getChildren().add(explosionView);
        explosionView.setX(-50);
        explosionView.setY(-50);

        explosionAnimation.play();
    }
}
