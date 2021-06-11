package DomiNations;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ImageView king11;
    @FXML
    private ImageView king12;
    @FXML
    private ImageView king21;
    @FXML
    private ImageView king22;
    @FXML
    private ImageView king31;
    @FXML
    private ImageView king32;
    @FXML
    private ImageView king41;
    @FXML
    private ImageView king42;

    @FXML
    private Image blueKing;
    @FXML
    private Image pinkKing;
    @FXML
    private Image greenKing;
    @FXML
    private Image yellowKing;


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
        removePlayerTiles(nbPlayers);


        // Handle Button event.
        startButton.setOnAction((event) -> {
            System.out.println("Clic sur le bouton");
        });
    }

    public void setPlayersColorsOnInterface(Player[] players){
        for (int i = 0; i < players.length; i++) {
            HBox colorBar;
            ImageView[] kingsImageView;
            String imageURl;
            switch (i) {
                case 0 -> {
                    colorBar = colorBar1;
                    kingsImageView = new ImageView[]{king11, king12};
                }
                case 1 -> {
                    colorBar = colorBar2;
                    kingsImageView = new ImageView[]{king21, king22};
                }
                case 2 -> {
                    colorBar = colorBar3;
                    kingsImageView = new ImageView[]{king31, king32};
                }
                case 3 -> {
                    colorBar = colorBar4;
                    kingsImageView = new ImageView[]{king41, king42};
                }
                default -> throw new IllegalStateException("Unexpected value: " + i);
            }
            Color colorOfPlayer = players[i].getColor();

            // Color Bar change :
            String hexValueOfPlayer = colorOfPlayer.getHexValue();
            colorBar.setStyle("-fx-background-color: "+hexValueOfPlayer);

            // Kings color change :
            String playerColor = colorOfPlayer.getName();
            for (ImageView imageView: kingsImageView){
                switch (playerColor) {
                    case "Vert" -> imageView.setImage(greenKing);
                    case "Bleu" -> imageView.setImage(blueKing);
                    case "Jaune" -> imageView.setImage(yellowKing);
                    case "Rose" -> imageView.setImage(pinkKing);
                }
            }
        }
    }

    public void removePlayerTiles(int nbPlayers){
        if (nbPlayers <= 3){
            rightPanel.getChildren().remove(fourthPlayer);      // Si 2 ou 3 joueurs on enlève le 4ème joueur
            if (nbPlayers <= 2){
                leftPanel.getChildren().remove(thirdPlayer);    // Si 2 joueurs on enlève aussi le 3ème joueur
            }
            else {                                              // Si 3 on enlève le deuxième king
                firstPlayerKings.getChildren().remove(king12);
                secondPlayerKings.getChildren().remove(king22);
                thirdPlayerKings.getChildren().remove(king32);
            }
        }
        else {
            firstPlayerKings.getChildren().remove(king12);
            secondPlayerKings.getChildren().remove(king22);
            thirdPlayerKings.getChildren().remove(king32);
            fourthPlayerKings.getChildren().remove(king42);
        }
    }
}