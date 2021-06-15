package DomiNations.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PlayerNamesColorsController {
    private int nbPlayers;
    private int nbNPC;
    private String[] colorsNames = new String[]{"Rose", "Jaune", "Vert", "Bleu"};

    @FXML
    private Label consigne;
    @FXML
    private VBox linesVBox;
    @FXML
    private ArrayList<GridPane> playerLines;
    @FXML
    private ArrayList<TextField> nameChoices;
    @FXML
    private ArrayList<ChoiceBox> colorChoices;

    public void setNbPlayers(int nbPlayers, int nbNPC) {
        this.nbPlayers = nbPlayers;
        this.nbNPC = nbNPC;
    }

    @FXML
    public void initDisplay() {
        // Remplissage des select
        for (ChoiceBox choiceBox: colorChoices){
            choiceBox.getItems().addAll(colorsNames);
            choiceBox.setValue("Couleur");
            choiceBox.setStyle("-fx-font-size : 20px;");
        }

        // Ajustement du nombre de champs en fonction du nombre de joueur et de NPC
        int nbRealPlayers = nbPlayers - nbNPC;
        if (nbRealPlayers <= 3) {
            linesVBox.getChildren().remove(playerLines.get(3));  // On enlève le choix pour le joueur 4
            consigne.setText("Choisir les noms et les couleurs des 3 joueurs :");
            if (nbRealPlayers <= 2) {
                linesVBox.getChildren().remove(playerLines.get(2));  // On enlève le choix pour le joueur 3
                consigne.setText("Choisir les noms et les couleurs des 2 joueurs :");
                if (nbRealPlayers <= 1) {
                    linesVBox.getChildren().remove(playerLines.get(1));  // On enlève le choix pour le joueur 2
                    consigne.setText("Choisir le nom et la couleur du joueur :");
                    if (nbRealPlayers <= 0) {
                        consigne.setText("Il n'y a pas de nom à choisir.");
                        linesVBox.getChildren().remove(playerLines.get(0));  // On enlève le choix pour le joueur 1
                    }
                }
            }
        }

        // Set des event listeners
        for (int i=0; i<nbPlayers-nbNPC; i++) {
            ChoiceBox choiceBox = colorChoices.get(i);
            choiceBox.setOnMouseEntered((event) -> { checkColorsChoices(); });
            choiceBox.setOnMouseExited((event) -> { checkColorsChoices(); });
        }
    }

    public void checkColorsChoices(){
        ArrayList<String> availableColors = new ArrayList<String>(Arrays.asList(colorsNames));
        for (int i=0; i<nbPlayers-nbNPC; i++){
            ChoiceBox choiceBox = colorChoices.get(i);
            String choice = (String) choiceBox.getSelectionModel().getSelectedItem();
            if (availableColors.contains(choice)){
                availableColors.remove(choice);
            }
            else if (!choice.equals("Couleur")){
                choiceBox.valueProperty().set(null);
                choiceBox.setValue("Couleur");
            }
        }
    }

    public void nextScene(ActionEvent event) throws IOException {

        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int i=0; i<nbPlayers-nbNPC; i++) {
            names.add(nameChoices.get(i).getText());
            String color = (String) colorChoices.get(i).getSelectionModel().getSelectedItem();
            colors.add(Arrays.asList(colorsNames).indexOf(color));
        }

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/DomiNations/view/GameScene.fxml")));
        Parent root = loader.load();

        GameController nextSceneController = loader.getController();
        nextSceneController.setAll(nbPlayers, nbNPC, names, colors);
        nextSceneController.initializeDisplay();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

}