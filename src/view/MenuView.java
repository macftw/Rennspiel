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

        if (drawStartButton) {
            // start game button
            HBox startBtnBox = new HBox();
            startBtnBox.setAlignment(Pos.CENTER);
            Button startBtn = new Button("Start Game");
            startBtn.setFont(new Font(50));
            startBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            startBtn.setTextFill(Color.WHITE);
            startBtnBox.getChildren().add(startBtn);
            setCenter(startBtnBox);
        }
    }
}

