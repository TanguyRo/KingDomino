package DomiNations.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class PlayerNumberController {

    @FXML
    private RadioButton twoPlayersChoice;
    @FXML
    private RadioButton threePlayersChoice;
    @FXML
    private RadioButton fourPlayersChoice;

    @FXML
    private Label subtext;

    @FXML
    private RadioButton zeroNPCChoice;
    @FXML
    private RadioButton oneNPCChoice;
    @FXML
    private RadioButton twoNPCChoice;
    @FXML
    private RadioButton threeNPCChoice;

    public void firstRowAction(){
        if (fourPlayersChoice.isSelected()){
            twoNPCChoice.setVisible(true);
            threeNPCChoice.setVisible(true);
            subtext.setText("(maximum 3)");
        }
        else if (threePlayersChoice.isSelected()){
            twoNPCChoice.setVisible(true);
            threeNPCChoice.setVisible(false);
            subtext.setText("(maximum 2)");
            if (threeNPCChoice.isSelected()){
                twoNPCChoice.setSelected(true);
            }
        }
        else if (twoPlayersChoice.isSelected()){
            twoNPCChoice.setVisible(false);
            threeNPCChoice.setVisible(false);
            subtext.setText("(maximum 1)");
            if (twoNPCChoice.isSelected() || threeNPCChoice.isSelected()){
                oneNPCChoice.setSelected(true);
            }
        }
    }

    public void nextScene(ActionEvent event) throws IOException {
        int nbPlayers = -1;
        if (twoPlayersChoice.isSelected()){
            nbPlayers = 2;
        }
        else if (threePlayersChoice.isSelected()){
            nbPlayers = 3;
        }
        else if (fourPlayersChoice.isSelected()){
            nbPlayers = 4;
        }

        int nbNPC = -1;
        if (zeroNPCChoice.isSelected()){
            nbNPC = 0;
        }
        else if (oneNPCChoice.isSelected()){
            nbNPC = 1;
        }
        else if (twoNPCChoice.isSelected()){
            nbNPC = 2;
        }
        else if (threeNPCChoice.isSelected()){
            nbNPC = 3;
        }

        if (nbPlayers == -1 || nbNPC == -1){
            throw new IllegalStateException("Une valeur est incorrecte");
        }

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/DomiNations/view/PlayerNamesColors.fxml")));
        Parent root = loader.load();

        PlayerNamesColorsController nextSceneController = loader.getController();
        nextSceneController.setNbPlayers(nbPlayers, nbNPC);
        nextSceneController.initDisplay();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

}