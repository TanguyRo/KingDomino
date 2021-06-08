package DomiNations;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
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
    private VBox leftPanel;
    @FXML
    private VBox centerPanel;
    @FXML
    private VBox rightPanel;

    @FXML
    private VBox firstPlayer;
    @FXML
    private VBox secondPlayer;
    @FXML
    private VBox thirdPlayer;
    @FXML
    private VBox fourthPlayer;

    @FXML
    private HBox colorBar1;
    @FXML
    private HBox colorBar2;
    @FXML
    private HBox colorBar3;
    @FXML
    private HBox colorBar4;

    @FXML
    private VBox firstPlayerKings;
    @FXML
    private VBox secondPlayerKings;
    @FXML
    private VBox thirdPlayerKings;
    @FXML
    private VBox fourthPlayerKings;



    
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

        removePlayerTiles(nbPlayers);
        setPlayersColorsOnInterface(players);


        // Handle Button event.
        startButton.setOnAction((event) -> {
            System.out.println("Clic sur le bouton");
        });
    }

    public void removePlayerTiles(int nbPlayers){
        if (nbPlayers <= 3){
            rightPanel.getChildren().remove(fourthPlayer);      // Si 2 ou 3 joueurs on enlève le 4ème joueur
            if (nbPlayers <= 2){
                leftPanel.getChildren().remove(thirdPlayer);    // Si 2 joueurs on enlève aussi le 3ème joueur
            }
            else {                                              // Si 3 on enlève le deuxième king
                firstPlayerKings.getChildren().remove(0);
                secondPlayerKings.getChildren().remove(0);
                thirdPlayerKings.getChildren().remove(0);
            }
        }
        else {
            firstPlayerKings.getChildren().remove(0);
            secondPlayerKings.getChildren().remove(0);
            thirdPlayerKings.getChildren().remove(0);
            fourthPlayerKings.getChildren().remove(0);
        }
    }

    public void setPlayersColorsOnInterface(Player[] players){
        for (int i = 0; i < players.length; i++) {
            HBox colorBar = switch (i) {
                case 0 -> colorBar1;
                case 1 -> colorBar2;
                case 2 -> colorBar3;
                case 3 -> colorBar4;
                default -> throw new IllegalStateException("Unexpected value: " + i);
            };
            String hexValueOfPlayer = players[i].getColor().getHexValue();
            colorBar.setStyle("-fx-background-color: "+hexValueOfPlayer);
        }
    }
}