package DomiNations;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.LinkedList;

public class Main extends Application {

    private static JavaFxGameController controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage gameStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("GameScene.fxml"));
        gameStage.setTitle("DomiNations");
        gameStage.setScene(new Scene(root, 1280, 720));
        gameStage.show();
    }

}
