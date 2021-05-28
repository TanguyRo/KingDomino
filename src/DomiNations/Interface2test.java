package DomiNations;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Interface2test extends Application {
    private int nbPlayers;

    public static void start() {
        launch(); // set up program as a javafx applicaton
    }

    Scene scene1, scene2;

    @Override
    public void start(Stage primaryStage) {
    //======================== Choix nombre joueurs =========================================

        /*primaryStage.setTitle("Interface2test");

//Scene 1
        ToggleGroup group = new ToggleGroup();
        RadioButton button2 = new RadioButton("2");
        button2.setToggleGroup(group);
        button2.setSelected(true);
        RadioButton button3 = new RadioButton("3");
        button3.setToggleGroup(group);
        RadioButton button4 = new RadioButton("4");
        button4.setToggleGroup(group);

        Label label1= new Label("Choix du nombre de joueurs");
        Button buttonValider= new Button("Valider");
        buttonValider.setOnAction(e -> primaryStage.setScene(scene2));
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, buttonValider);
        scene1= new Scene(layout1, 300, 250);



//Scene 2
        Label label2= new Label("This is the second scene");
        Button button2scene1= new Button("Go to scene 1");
        button2scene1.setOnAction(e -> primaryStage.setScene(scene1));
        VBox layout2= new VBox(20);
        layout2.getChildren().addAll(label2, button2scene1);
        scene2= new Scene(layout2,300,250);


        primaryStage.setScene(scene1);
        primaryStage.show();*/

        primaryStage.setTitle("Déclaration des joueurs");
        BorderPane borderPaneRoot = new BorderPane();                       //créer le groupe root border pane qui structure le contenu en 5 zones
        Scene scene = new Scene(borderPaneRoot, 800, 600);           //créer le composant scene utilisant borderpane comme racine
        primaryStage.setScene(scene);                                       //afficher la scene dans le stage


        Stage otherStage = new Stage();
        otherStage.setTitle("Nombre de joueurs");
        otherStage.setWidth(400);
        otherStage.setHeight(300);
        otherStage.setResizable(false);
        otherStage.show();
        otherStage.centerOnScreen();
        Label joueurs = new Label("Nombre de joueurs : ");
        //group
        ToggleGroup group = new ToggleGroup();

        //Radio 1 : 2 joueurs : One
        RadioButton button2 = new RadioButton("2");
        button2.setToggleGroup(group);
        button2.setSelected(true);
        RadioButton button3 = new RadioButton("3");
        button3.setToggleGroup(group);
        RadioButton button4 = new RadioButton("4");
        button4.setToggleGroup(group);

        Button buttonValider = new Button("Valider");
        HBox HBoxNbJoueurs = new HBox();
        HBoxNbJoueurs.setPadding(new Insets(10));
        HBoxNbJoueurs.setSpacing(5);
        HBoxNbJoueurs.getChildren().addAll(joueurs, button2, button3, button4, buttonValider);

        Scene sceneChoixNbJoueurs = new Scene(HBoxNbJoueurs, 400, 150);
        otherStage.setScene(sceneChoixNbJoueurs);
        otherStage.setTitle("Choix nombre de joueurs");
        sceneChoixNbJoueurs.setRoot(HBoxNbJoueurs);
        otherStage.show();


        /*group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                // Has selection.
                if (group.getSelectedToggle() != null) {

                    System.out.println("Button: " + button.getText());
                    joueurs.setText("You are " + button.getText());

                }
            }
        });*/
        //lors de la validation du bouton chox enregiqtré
        buttonValider.setOnAction(e->{
            RadioButton buttonChoixNbPlayers = (RadioButton) group.getSelectedToggle();
            nbPlayers = Integer.parseInt(buttonChoixNbPlayers.getText());                                       //ajouter le contenu de nom à la liste ListView
            System.out.println(nbPlayers + " players selected");
            otherStage.close();
        });
    // ========================================================================================




    }

    public static void main(String[] args) {
        launch(args);
    }

}

