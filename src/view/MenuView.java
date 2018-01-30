package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MenuView extends BorderPane {

    public MenuView(double width, double height, String title) {
        super();
        // setup pane
        setBackground(new Background(new BackgroundFill(Color.grayRgb(0, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
        setPrefSize(width, height);

        boolean drawStartButton = title.equals("Rennspiel");

        // Menu title
        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER);
        Text titleTxt = new Text(title);
        titleTxt.setFont(new Font(150));
        titleTxt.setFill(Color.WHITE);
        titleTxt.setTextAlignment(TextAlignment.CENTER);
        titleBox.getChildren().add(titleTxt);
        setTop(titleBox);

        HBox centerBox = new HBox();
        centerBox.setAlignment(Pos.CENTER);
        Text helpTxt = new Text();
        helpTxt.setFont(new Font(24));
        helpTxt.setFill(Color.WHITE);
        helpTxt.setText("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, \n" +
                "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, \n" +
                "sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. \n" +
                "Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. \n" +
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor \n" +
                "invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. \n" +
                "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, \n" +
                "no sea takimata sanctus est Lorem ipsum dolor sit amet.");
        centerBox.getChildren().add(helpTxt);
        setCenter(centerBox);

        if (drawStartButton) {
            // start game button
            HBox startBtnBox = new HBox();
            startBtnBox.setAlignment(Pos.CENTER);
            Button startBtn = new Button("Start Game");
            startBtn.setFont(new Font(50));
            startBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            startBtn.setTextFill(Color.WHITE);
            startBtnBox.getChildren().add(startBtn);
            setBottom(startBtnBox);
        }
    }
}

