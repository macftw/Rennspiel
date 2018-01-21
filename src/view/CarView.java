package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class CarView extends ImageView {

    private final String imgUrl = "resources/car.png";

    public CarView() {
        super();
        Image carImg = new Image(imgUrl);
        this.setImage(carImg);
    }

    public void setRotation(double degrees) {
        this.getTransforms().add(new Rotate(degrees));
    }

    public void setPosition(double x, double y) {
        this.getTransforms().add(new Translate(x, y));
    }
}
