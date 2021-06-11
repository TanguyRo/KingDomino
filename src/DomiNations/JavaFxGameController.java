package DomiNations;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

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

    // 3 grandes tuiles
    @FXML
    private VBox leftPanel;
    @FXML
    private VBox centerPanel;
    @FXML
    private VBox rightPanel;

    // Tuile pour chaque joueur
    @FXML
    private VBox firstPlayerTile;
    @FXML
    private VBox secondPlayerTile;
    @FXML
    private VBox thirdPlayerTile;
    @FXML
    private VBox fourthPlayerTile;

    // Barres de couleurs pour chaque joueur
    @FXML
    private HBox colorBar1;
    @FXML
    private HBox colorBar2;
    @FXML
    private HBox colorBar3;
    @FXML
    private HBox colorBar4;

    // Zones de texte pour les noms des joueurs
    @FXML
    private Text playerName1;
    @FXML
    private Text playerName2;
    @FXML
    private Text playerName3;
    @FXML
    private Text playerName4;

    // Conteneur des Kings pour chaque joueur
    @FXML
    private VBox firstPlayerKings;
    @FXML
    private VBox secondPlayerKings;
    @FXML
    private VBox thirdPlayerKings;
    @FXML
    private VBox fourthPlayerKings;

    // Images des 4 Kings
    @FXML
    private Image blueKing;
    @FXML
    private Image pinkKing;
    @FXML
    private Image greenKing;
    @FXML
    private Image yellowKing;

    // ImageView des Kings (2 spots par joueur)
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
    private void initialize() {
        // Création des joueurs
        createPlayers();

        // Création des royaumes 5x5 pour chaque joueur
        initialiseKingdoms();

        // Paramètres d'affichage en fonction des joueurs (nombre de tuiles et couleurs)
        setPlayersParametersOnInterface(players);
        removePlayerTiles(nbPlayers);


        // Handle Button event.
        startButton.setOnAction((event) -> {
            System.out.println("Clic sur le bouton");
        });
    }

    private void createPlayers(){
        game.createPlayers();
        nbPlayers = game.getNbPlayers();        // On actualise
        players = game.getPlayers();
        nbKings = game.getNbKings();
        kings = game.getKings();
        System.out.println("Les joueurs ont bien été créés.");
    }

    private void initialiseKingdoms(){
        game.initialiseKingdoms();
        players = game.getPlayers();
        kingdoms = game.getKingdoms();
        System.out.println("Les " + nbPlayers + " royaumes ont bien été créés.");
    }

    public void setPlayersParametersOnInterface(Player[] players){
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            Color playerColor = player.getColor();
            HBox colorBar;
            ImageView[] kingsImageView;
            Text playerNameTextZone;
            switch (i) {
                case 0 -> {
                    colorBar = colorBar1;
                    kingsImageView = new ImageView[]{king11, king12};
                    playerNameTextZone = playerName1;
                }
                case 1 -> {
                    colorBar = colorBar2;
                    kingsImageView = new ImageView[]{king21, king22};
                    playerNameTextZone = playerName2;
                }
                case 2 -> {
                    colorBar = colorBar3;
                    kingsImageView = new ImageView[]{king31, king32};
                    playerNameTextZone = playerName3;
                }
                case 3 -> {
                    colorBar = colorBar4;
                    kingsImageView = new ImageView[]{king41, king42};
                    playerNameTextZone = playerName4;
                }
                default -> throw new IllegalStateException("Unexpected value: " + i);
            }

            // Name change :
            playerNameTextZone.setText(player.getName());

            // Color Bar change :
            String hexValueOfPlayer = playerColor.getHexValue();
            colorBar.setStyle("-fx-background-color: "+hexValueOfPlayer);

            // Kings color change :
            String playerColorName = playerColor.getName();
            for (ImageView imageView: kingsImageView){
                switch (playerColorName) {
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
            rightPanel.getChildren().remove(fourthPlayerTile);      // Si 2 ou 3 joueurs on enlève le 4ème joueur
            if (nbPlayers <= 2){
                leftPanel.getChildren().remove(thirdPlayerTile);    // Si 2 joueurs on enlève aussi le 3ème joueur
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