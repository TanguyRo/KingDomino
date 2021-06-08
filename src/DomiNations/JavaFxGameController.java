package DomiNations;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.LinkedList;

public class JavaFxGameController {
    private final Game game = new Game(true,true);
    private Player[] players;
    private Kingdom[] kingdoms;
    private King[] kings ;
    private LinkedList<Domino> drawPile;
    private Bench bench;
    private int nbPlayers;
    private int nbKings;

    @FXML
    private Button startButton;

    @FXML
    private HBox colorBar1;
    @FXML
    private HBox colorBar2;
    @FXML
    private HBox colorBar3;
    @FXML
    private HBox colorBar4;
    
    @FXML
    private void initialize() {
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

        setPlayersColorsOnInterface(players);


        // Handle Button event.
        startButton.setOnAction((event) -> {
            System.out.println("Clic sur le bouton");
        });
    }

    public void setPlayersColorsOnInterface(Player[] players){
        for (int i = 0; i < 4; i++) {
            HBox colorBar;
            switch (i) {
                case 0 -> colorBar = colorBar1;
                case 1 -> colorBar = colorBar2;
                case 2 -> colorBar = colorBar3;
                case 3 -> colorBar = colorBar4;
                default -> throw new IllegalStateException("Unexpected value: " + i);
            }
            System.out.println(i + colorBar.toString());
            String hexValueOfPlayer = players[i].getColor().getHexValue();
            colorBar.setStyle("-fx-background-color: "+hexValueOfPlayer);
        }
    }
}