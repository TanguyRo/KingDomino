package DomiNations;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

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
