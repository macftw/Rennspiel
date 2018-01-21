package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class CarView extends ImageView {

    private final String imgUrl = "resources/car.png";
    private Rotate rotation;
    private Translate translate;

    public CarView(double startX, double startY) {
        super();
        Image carImg = new Image(imgUrl);
        this.setImage(carImg);
        rotation = new Rotate(0);
        this.getTransforms().add(rotation);
        translate = new Translate(startX, startY);
        this.getTransforms().add(translate);
    }

    public void setRotation(double degrees) {
        rotation.setAngle(degrees);
    }

    public void setPosition(double x, double y) {
        translate.setX(x);
        translate.setY(y);
    }
}
