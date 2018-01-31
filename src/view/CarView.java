package view;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class CarView extends Region {

    private final String imgUrl = "resources/car.png";
    public double imageWidth, imageHeight;
    private Rotate rotation;
    private Translate translate;

    public CarView(double startX, double startY) {
        super();
        ImageView imgView = new ImageView();
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
    }

    public void setRotation(double degrees) {
//        rotation.setPivotX(getPosition().getX());
//        rotation.setPivotY(getPosition().getY());
        rotation.setAngle(degrees);
    }

    public Point2D setPosition(double delta) {
        double x = -Math.cos(rotation.getAngle() * Math.PI / 180) * delta;
        double y = -Math.sin(rotation.getAngle() * Math.PI / 180) * delta;
        translate.setX(translate.getX() + x);
        translate.setY(translate.getY() + y);
        return new Point2D((float)translate.getX(), (float)translate.getY());
    }

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
}
