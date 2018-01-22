package view;

import com.sun.javafx.geom.Point2D;
import javafx.beans.binding.Bindings;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class CarView extends ImageView {

    private final String imgUrl = "resources/car.png";
    private double imageWidth, imageHeight;
    private Rotate rotation;
    private Translate translate;

    public CarView(double startX, double startY) {
        super();
        Image carImg = new Image(imgUrl);
        this.setImage(carImg);
        imageWidth = carImg.getWidth();
        imageHeight = carImg.getHeight();
        translate = new Translate(startX, startY);
        this.getTransforms().add(translate);
        rotation = new Rotate(0);
        rotation.pivotXProperty().bind(Bindings.add(imageWidth / 2, xProperty()));
        rotation.pivotYProperty().bind(Bindings.add(imageHeight / 2, yProperty()));
        this.getTransforms().add(rotation);
    }

    public void setRotation(double degrees) {
        rotation.setAngle(degrees);
    }

    public Point2D setPosition(double delta) {
        double x = -Math.cos(rotation.getAngle() * Math.PI / 180) * delta;
        double y = -Math.sin(rotation.getAngle() * Math.PI / 180) * delta;
        translate.setX(translate.getX() + x);
        translate.setY(translate.getY() + y);
        return new Point2D((float)translate.getX(), (float)translate.getY());
    }
}
