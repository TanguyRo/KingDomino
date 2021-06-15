package DomiNations;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("view/Start.fxml"));
        stage.setTitle("DomiNations");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();

        /*
        // Initialisation du jeu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/GameScene.fxml"));
        Parent root = loader.load();
        JavaFxGameController gameController = (JavaFxGameController) loader.getController();

        gameStage.setTitle("DomiNations");
        gameStage.setScene(new Scene(root, 1280, 720));
        gameStage.show();


         */

        // Début du jeu : configuration du premier tour
        // gameController.chooseDominosFirstRound();
        //gameController.actualiseBench();
        //System.out.println("Tous les dominos ont été sélectionnés par les joueurs. Celui avec la plus petite valeur commence en premier.");

    }


}
