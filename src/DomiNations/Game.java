package DomiNations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class Game {
    private Player[] players;
    private Player currentPlayer;
    private Kingdom[] kingdoms;
    private King[] kings;
    private LinkedList<Domino> drawPile;
    private int nbPlayers;
    private boolean piocheVide;


        public void play() {

        // Création des joueurs
        createPlayers();

        // Création des royaumes 5x5 pour chaque joueur
        initialiseKingdoms();

        // Création de la pioche, remplie du bon nombre de dominos et mélangée
        try {
            initialiseDrawPile(nbPlayers);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Premier Tour
        //Piocher autant de dominos qu'il y a de rois en jeu
        //Disposer ces dominos face numérotée visible et rangés par ordre croissant
        //retourner ces dominos face paysage
        /*do {
            //Récupérer le domino sur lequel son roi se trouvait
            //Placer ce domino dans le royaume en respectant les règles de connexion.
            //Sélectionner un domino de la ligne suivante en y plaçant son roi.
        }
        while(!piocheVide);*/
        //Tous les dominos ont été piochés et posés
        //calcul des points par joueur
        // n = nbCases * nbCouronnes
        //nbCases nombre de cases du domaine
        //nbCouronnes nombre de couronnes sur le domaine

        //comparaison point entre joueur
        //si égalité -> plus grand domaine gagne
        //si égalité de domaine -> plus de couronnes gagne
        //sinon tous gagnant
    }

    public void createPlayers() {
        int color = 0;

        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Saisir un nombre de joueurs (2, 3 ou 4) : ");
            nbPlayers = scanner.nextInt();
            scanner.nextLine();

        }while(nbPlayers<2 || nbPlayers>4);

        this.players = new Player[nbPlayers];
        this.kings = new King[countKings(nbPlayers)];   // Taille 3 si 3 joueurs, taille 4 si 2 ou 4 joueurs

        // Noms et couleurs des joueurs :
        for(int i = 1; i<=nbPlayers; i++) {
            System.out.println("Saisir nom joueur " + i + " : ");
            String name = scanner.nextLine();

            do {
                System.out.println("Saisir couleur joueur " + i + " (\"rose\", \"jaune\", \"vert\", \"bleu\") : ");
                String color_input = scanner.nextLine();

                switch (color_input) {
                    case "rose":
                        color = 1;
                        break;
                    case "jaune":
                        color = 2;
                        break;
                    case "vert":
                        color = 3;
                        break;
                    case "bleu":
                        color = 4;
                        break;
                    default:
                        color = 0;
                        System.out.println("Saisir une couleur valide : ");
                        break;
                }

            } while (color == 0);

            players[i-1] = new Player(name, color);

            // Création des rois en fonction du nombre de joueurs
            if (nbPlayers==3 || nbPlayers==4){
                kings[i-1] = new King(players[i-1]);
            }
            else if (nbPlayers == 2){
                if (i==1){
                    kings[0] = new King(players[i-1]);
                    kings[1] = new King(players[i-1]);
                }
                else {
                    kings[2] = new King(players[i-1]);
                    kings[3] = new King(players[i-1]);
                }
            }
        }

        currentPlayer = players[0];
        System.out.println("Les joueurs ont bien été créés.");
    }

    // Fonction qui renvoie le nombre de rois nécéessaires en fonction du nombre de joueurs
    public int countKings(int nbPlayers){
        int kingsNumber;
        switch (nbPlayers){
            case 3 :
                kingsNumber = 3;
                break;
            default :
                kingsNumber = 4;
                break;
        }
        return kingsNumber;
    }

    public void initialiseKingdoms() {
        this.kingdoms = new Kingdom[nbPlayers];

        for(int i = 0; i<nbPlayers; i++) {
            kingdoms[i] = new Kingdom(1,players[i]);	// taille 1 car uniquement le château au départ

            Cell[][] cells = new Cell[5][5];

            for (int x=0; x<5; x++){
                for (int y=0; y<5; y++){
                    cells[x][y] = new Cell(new Position(x,y),true,null);
                }
            }

            // Case du chateau
            cells[2][2].setState(false);
            cells[2][2].setCurrentLandPiece(new LandPiece("chateau",0));

            kingdoms[i].setCells(cells);

        }

        System.out.println("Les " + nbPlayers + " royaumes ont bien été créés.");
    }

    public void initialiseDrawPile(int nbPlayers) throws FileNotFoundException {
        // scanner va lire le contenu du fichier .csv
        Scanner scanner = new Scanner(new File("dominos.csv"));
        scanner.nextLine();		// On saute la ligne d'en-tête

        // stringBuilder va stocker le contenu du fichier
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine())
                    .append("\n");
        }
        scanner.close();

        // Les deux lignes suivantes vont ensuite produire un String[] contenant
        // les donneees du fichier CSV
        String data = stringBuilder.toString();
        String[] dominos = data.split("\n");

        // Création de la pile de dominos
        this.drawPile = new LinkedList<>();

        // Création de tous les dominos et placement dans l'ordre dans la pile drawPile
        for (int i=0; i < dominos.length; i++) {
            String[] infosDomino = dominos[i].split(",");
            LandPiece landPiece1 = new LandPiece(infosDomino[1],Integer.valueOf(infosDomino[0]));
            LandPiece landPiece2 = new LandPiece(infosDomino[3],Integer.valueOf(infosDomino[2]));
            Domino domino = new Domino(landPiece1, landPiece2, Integer.valueOf(infosDomino[4]));
            drawPile.add(domino);
        }

        // Mélange des dominos
        Collections.shuffle(drawPile);

        // Enlever les dominos en trop si 2 ou 3 joueurs
        switch(nbPlayers) {
            case 2:
                for(int i=1; i<=24; i++) { drawPile.remove(); }
                break;
            case 3:
                for(int i=1; i<=12; i++) { drawPile.remove(); }
                break;
        }

        System.out.println("La pioche a été mélangée et contient " + drawPile.size() + " dominos.");
    }

}