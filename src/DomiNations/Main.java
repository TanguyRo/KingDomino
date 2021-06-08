package DomiNations;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedList;

public class Main extends Application {

    public static void main(String[] args) {

        Game game = new Game(true,true);
        Player[] players;
        Kingdom[] kingdoms;
        King[] kings ;
        LinkedList<Domino> drawPile;
        Bench bench;
        int nbPlayers;
        int nbKings;

        // Création des joueurs
        game.createPlayers();
        nbPlayers = game.getNbPlayers();        // On actualise
        players = game.getPlayers();
        nbKings = game.getNbKings();
        kings = game.getKings();
        System.out.println("Les joueurs ont bien été créés.");

        // Création des royaumes 5x5 pour chaque joueur
        game.initialiseKingdoms();
        players = game.getPlayers();
        kingdoms = game.getKingdoms();
        System.out.println("Les " + nbPlayers + " royaumes ont bien été créés.");

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
