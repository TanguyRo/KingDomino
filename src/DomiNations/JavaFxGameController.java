package DomiNations;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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

    // Tuiles de chaque joueur
    @FXML
    private ArrayList<VBox> playersTiles;

    // Barres de couleurs pour chaque joueur
    @FXML
    private ArrayList<HBox> colorBars;

    // Zones de texte pour les noms des joueurs
    @FXML
    private ArrayList<Text> playerNamesTextZones;

    // Conteneur des Kings pour chaque joueur
    @FXML
    private ArrayList<VBox> kingsVbox;

    // Images des 4 Kings
    @FXML
    private ArrayList<Image> kingsImages;

    // ImageView des Kings (2 spots par joueur)
    @FXML
    private ArrayList<ArrayList<ImageView>> kingsImageViews;

    // Images des châteaux
    @FXML
    private ArrayList<Image> castleImages;

    // Images des châteaux
    @FXML
    private ArrayList<ArrayList<Image>> dominosImages;

    // ImageView des cells des grids
    @FXML
    private ArrayList<ArrayList<ArrayList<ImageView>>> gridCells;

    @FXML
    private void initialize() {
        // Création des joueurs
        createPlayers();

        // Création des royaumes 5x5 pour chaque joueur
        initialiseKingdoms();

        // Paramètres d'affichage en fonction des joueurs (nombre de tuiles, noms et couleurs)
        setPlayersParametersOnInterface(players);
        removePlayerTiles(nbPlayers);

        // Création de la pioche, remplie du bon nombre de dominos et mélangée
        try {
            initialiseDrawPile();
            System.out.println("La pioche a été mélangée et contient " + drawPile.size() + " dominos.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

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
        game.initialiseKingdoms(gridCells, castleImages); // Crée les royaumes, avec les ImageView de chaque cell et les images des châteaux
        players = game.getPlayers();
        kingdoms = game.getKingdoms();

        actualiseKingdoms(kingdoms);    // On met à jour l'affichage

        System.out.println("Les " + nbPlayers + " royaumes ont bien été créés.");
    }

    private void setPlayersParametersOnInterface(Player[] players){
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            Color playerColor = player.getColor();

            // Name change :
            Text playerNameTextZone = playerNamesTextZones.get(i);
            playerNameTextZone.setText(player.getName());

            // Color Bar change :
            HBox colorBar = colorBars.get(i);
            String hexValueOfPlayer = playerColor.getHexValue();
            colorBar.setStyle("-fx-background-color: "+hexValueOfPlayer);

            // Kings color change :
            ArrayList<ImageView> playerKingsImageViews = kingsImageViews.get(i);
            for (ImageView imageView: playerKingsImageViews){
                imageView.setImage(kingsImages.get(playerColor.getNumber()-1));
            }
        }
    }

    private void removePlayerTiles(int nbPlayers){
        if (nbPlayers <= 3){
            rightPanel.getChildren().remove(playersTiles.get(3));      // Si 2 ou 3 joueurs on enlève le 4ème joueur
            if (nbPlayers <= 2){
                leftPanel.getChildren().remove(playersTiles.get(2));    // Si 2 joueurs on enlève aussi le 3ème joueur
            }
            else {                                              // Si 3 on enlève le deuxième king
                kingsVbox.get(0).getChildren().remove(kingsImageViews.get(0).get(1));
                kingsVbox.get(1).getChildren().remove(kingsImageViews.get(1).get(1));
                kingsVbox.get(2).getChildren().remove(kingsImageViews.get(2).get(1));
            }
        }
        else {
            kingsVbox.get(0).getChildren().remove(kingsImageViews.get(0).get(1));
            kingsVbox.get(1).getChildren().remove(kingsImageViews.get(1).get(1));
            kingsVbox.get(2).getChildren().remove(kingsImageViews.get(2).get(1));
            kingsVbox.get(3).getChildren().remove(kingsImageViews.get(3).get(1));
        }
    }

    private void actualiseKingdom(Kingdom kingdom){
        for (int x=0; x<5; x++) {
            for (int y = 0; y < 5; y++) {
                Cell cell = kingdom.getCells()[y][x];
                ImageView imageView = cell.getImageView();
                // Si la cell a une landpiece
                if (!cell.isEmpty()) {
                    LandPiece landPiece = cell.getCurrentLandPiece();
                    imageView.setVisible(true);
                    imageView.setImage(landPiece.getImage());
                } else {
                    imageView.setVisible(false);
                }
            }
        }
    }

    private void actualiseKingdoms(Kingdom[] kingdoms){
        for (int i = 0; i < nbPlayers; i++) {
            actualiseKingdom(kingdoms[i]);
        }
    }

    public void initialiseDrawPile() throws FileNotFoundException {
        game.initialiseDrawPile(nbPlayers, dominosImages);
        drawPile = game.getDrawPile();
    }
}