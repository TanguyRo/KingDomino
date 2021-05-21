package DomiNations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Game {
    private Player[] players;
    private Player currentPlayer;
    private Kingdom[] kingdoms;
    private King[] kings;
    private LinkedList<Domino> drawPile;
    private int nbPlayers;

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

        
        // Affichage des royaumes :
        /*
        for (Kingdom kingdom: kingdoms){
            kingdom.print();
        }
        */

        // Tests de l'affichage et du déplacement d'un kingdom :
        /*
        Kingdom testKingdom = kingdoms[0];
        Cell[][] testKingdomCells = testKingdom.getCells();
        for (int x=0; x<5; x++){
            for (int y=0; y<5; y++){
                testKingdomCells[y][x] = new Cell(new int[] {x,y},true,null);
            }
        }
        testKingdomCells[2][2].setCurrentLandPiece(new LandPiece("Chateau",0));
        testKingdomCells[1][1].setCurrentLandPiece(new LandPiece("Montagne",2));
        testKingdomCells[1][2].setCurrentLandPiece(new LandPiece("Mer",0));
        testKingdomCells[1][3].setCurrentLandPiece(new LandPiece("Mer",1));
        testKingdomCells[2][3].setCurrentLandPiece(new LandPiece("Foret",1));
        testKingdom.print();
        testKingdom.move("up");
        testKingdom.print();
        testKingdom.move("left");
        testKingdom.print();
        */


        Domino DominoInPlay;
        List<Domino> ListDominoInPlay = new ArrayList();
        List<Integer> ListValueDomino = new ArrayList();

        //Piocher autant de dominos qu'il y a de rois en jeu
        for(int y=0; y<kings.length; y++){
            DominoInPlay = drawPile.getFirst();
            drawPile.remove();

            ListDominoInPlay.add(y, DominoInPlay);
            ListValueDomino.add(y, DominoInPlay.getNumber());
        }

        //Debut de Premier Tour
        for(int i=1; i<=kings.length; i++){
            Scanner scanner = new Scanner(System.in);
            LinkedList<King> GetKings = new LinkedList(Arrays.asList(kings));

            Collections.shuffle(GetKings);
            int affichageKing = GetKings.get(0).getPlayer().getColor();
            System.out.println("Le joueur " + affichageKing + " a été selectionné au hasard.");
            System.out.println("Le joueur " + affichageKing + " doit choisir un domino parmis " +
                    ListValueDomino + " (Entrer la valeur du domino) :");
            int domino = scanner.nextInt();

            for(int j=0; j<kings.length; j++){
                if(domino == ListDominoInPlay.get(j).getNumber()){
                    ListDominoInPlay.get(j).setKing(GetKings.get(0));
                    System.out.println("Le domino selectionné par le joueur " + affichageKing
                            +  " (" + ListDominoInPlay.get(j) + ") vaut "+ ListDominoInPlay.get(j).getNumber());
                }
            }

            GetKings.remove(0);
        }
        System.out.println("Tout les dominos ont été selectionné par les joueurs. Celui avec la plus petite valeur commence en premier.");

        /*do {
            //Debut de Tour
            Domino DominoInPlay;
            List<Domino> ListDominoInPlay = new ArrayList();
            List<Integer> ListValueDomino = new ArrayList();

            //Piocher autant de dominos qu'il y a de rois en jeu
            for(int i=0; i<kings.length; i++){
                DominoInPlay = drawPile.getFirst();
                DominoInPlay.setKing(kings[i]);
                System.out.println("Le domino pioché par le joueur " + kings[i].getPlayer().getColor()
                        +  " (" + DominoInPlay + ") vaut "+ DominoInPlay.getNumber());
                drawPile.remove();

                ListDominoInPlay.add(i, DominoInPlay);
                ListValueDomino.add(i, DominoInPlay.getNumber());
            }
            // TODO INTERFACE Disposer ces dominos face numérotée visible et rangés par ordre croissant
            Collections.sort(ListValueDomino);


            // TODO INTERFACE retourner ces dominos face paysage
            //System.out.println(ListValueDomino);

            List<Domino> TriDomino = new ArrayList();
            for(int x=0; x< kings.length; x++){
                for(int y=0; y< kings.length; y++){
                    if(ListValueDomino.get(x)==ListDominoInPlay.get(y).getNumber()){
                        TriDomino.add(ListDominoInPlay.get(y));
                    }
                }
            }
            System.out.println("Dominos restant : " + drawPile.size());
            //System.out.println("Dominos en jeu : " + TriDomino);

            for(int i=0; i<kings.length; i++) {
                TriDomino.get(i).getKing().getPlayer();
                System.out.println("Tour du joueur " + TriDomino.get(i).getKing().getPlayer().getColor());
                //Joue
                // TODO Récupérer le domino sur lequel son roi se trouvait
                // TODO Placer ce domino dans le royaume en respectant les règles de connexion.
                // -> L'user choisit les coordonnées de la position en haut à gauche (entre 0 et 4 pour chaque coordonnée) et l'orientation entre 1 et 4 (3 inputs ou interface graphique)
                // Bouton Move pour déplacer le board sur l'interface graphique (ou input 0 pour pas de déplacement ou direction et nombre)
                // TODO Sélectionner un domino de la ligne suivante en y plaçant son roi.
            }
        }
        while(!drawPile.isEmpty());*/
        //Tous les dominos ont été piochés et posés

        // TODO Calcul des points par joueur
        // n = nbCases * nbCouronnes pour chaque domaine
        //nbCases nombre de cases du domaine = domain.landPieces.size()
        //nbCouronnes nombre de couronnes sur le domaine = domain.landPieces.getCrownNumber()

        // TODO comparaison point entre joueur
        //si égalité -> plus grand domaine gagne
        //si égalité de domaine -> plus de couronnes gagne
        //sinon tous gagnant
    }

    public void createPlayers() {
        //Utilisation d'un dictionnaire pour proposer les couleurs
        String[] colors={"Rose", "Jaune", "Vert", "Bleu"};
        HashMap<String, Integer> colorsMap = new HashMap<>();
        for (int i=1; i<=4; i++){
            colorsMap.put(colors[i-1],i);
        }

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
            int color = 0;

            System.out.println("Saisir le nom du joueur " + i + " : ");
            String name = scanner.nextLine();

            // S'il y a un choix à faire parmi plusieurs couleurs
            if (colorsMap.size()>1) {
                do {
                    String listeCouleurs = String.join(", ", colorsMap.keySet()).replaceAll(", (?=[A-Za-z]*$)"," ou ");
                    System.out.println("Saisir la couleur du joueur " + i + " parmi " + listeCouleurs + " : ");
                    String color_input = scanner.nextLine();

                    if (colorsMap.containsKey(color_input)) {
                        color = colorsMap.get(color_input);
                        colorsMap.remove(color_input);
                    }

                } while (color == 0);
            }
            // S'il n'en reste qu'une
            else {
                String colorName = (String) colorsMap.keySet().toArray()[0];
                color = colorsMap.get(colorName);
                colorsMap.remove(colorName);
                System.out.println("Le joueur " + i + " aura la couleur " + colorName + ".");
            }

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
                    cells[y][x] = new Cell(new int[] {x,y},true,null);
                }
            }

            // Case du chateau
            cells[2][2].setEmpty(false);
            cells[2][2].setCurrentLandPiece(new LandPiece("Chateau",0));

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