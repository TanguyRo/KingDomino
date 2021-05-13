package DomiNations;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class Interface extends Application {
    public static void start() {
        launch(); // set up program as a javafx applicaton
    }

    @Override
    public void start(Stage primaryStage) throws Exception { // main javafx code
        /*primaryStage.setTitle("DomiNations"); // set the window title

        Button button = new Button(); // create a button
        button.setText("Click me");

        StackPane layout = new StackPane(); // create a layout
        layout.getChildren().add(button); // add the button into the layout

        Scene scene = new Scene(layout, 300,255); // create a new scene
        primaryStage.setScene(scene); // add the scene to the Stage
        primaryStage.show(); // display*/

        primaryStage.setTitle("Déclaration des joueurs");
        BorderPane borderPaneRoot = new BorderPane();                       //créer le groupe root border pane qui structure le contenu en 5 zones
                                                                            //top bottom left right center
        Scene scene = new Scene(borderPaneRoot, 800, 600);           //créer le composant scene utilisant borderpane comme racine
        primaryStage.setScene(scene);                                       //afficher la scene dans le stage

        HBox hBox1 = new HBox();                                            //créer le layout Hbox qui permet de structurer les éléments horizontalement
        hBox1.setPadding(new Insets(10));                               //définir la marge entre le contenu et les bords du conteneur hbox1
        hBox1.setSpacing(10);                                               //définir l'espace entre les éléments contenus dans hbox1'
        Label labelNom = new Label("Nom des joueurs :");                //Créer le label "nomd des joueur"
        labelNom.setPadding(new Insets(5));                             //défninir la marge entre le texte du lable et les bords du lable
        TextField textFieldNom = new TextField();                           //creer le compasant de saisie de TextField
        Button buttonAdd = new Button("Ajouter");                       //créer le cmposant Button Ajouter
        hBox1.getChildren().addAll(labelNom, textFieldNom, buttonAdd);      //ajouter les composants label textField et Button dans hbox1

        VBox vBox1 = new VBox();                                            //créer le layout vbox qui permet de structuerer les elements verticalement
        vBox1.setPadding(new Insets(10));
        ObservableList<String> observableList = FXCollections.observableArrayList();
        ListView<String> listView1 = new ListView<>();                      //créer le composant listViem
        listView1.getItems().addAll("Tanguy", "Paul");                    //ajouter deux elements a listView1
        vBox1.getChildren().add(listView1);                                 //mettre listView1 dans VBox1
        borderPaneRoot.setCenter(vBox1);                                    //mettre hbox1 dans BorderPane au centre
        borderPaneRoot.setTop(hBox1);                                       //metre border panel en haut
        primaryStage.show();                                                //afficher primaryStage

        Stage otherStage = new Stage();
        otherStage.setTitle("Deuxieme page");
        otherStage.setWidth(400);
        otherStage.setHeight(300);
        otherStage.setResizable(false);
        otherStage.show();
        otherStage.centerOnScreen();

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            otherStage.hide();
                        }
                    });

                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
        premiere puis deuxieme version (utilisation des lambda expressions
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        otherStage.hide();
                    }
                });

            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }).start();*/
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                Platform.runLater(() -> otherStage.hide());
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }).start();



        //quand on clique sur le bouton Ajouter, on effectue un traitement
        buttonAdd.setOnAction(e->{
            String nom = textFieldNom.getText();                            //lire le contenu saisi dans la zone de texte
            listView1.getItems().add(nom);
            observableList.add(nom);                                        //ajouter le contenu de nom à la liste ListView
        });

    }

    public void afficheur(ObservableList<String> observableList)
    {
        for(String name : observableList)
        {
            System.out.println(name);
        }
    }
}
