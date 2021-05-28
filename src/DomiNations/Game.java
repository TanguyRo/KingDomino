package DomiNations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Game {
    private Player[] players;
    private Kingdom[] kingdoms;
    private King[] kings;
    private LinkedList<Domino> drawPile;
    private Bench bench;
    private int nbPlayers;
    private int nbKings;

    // Rules :
    private final boolean EmpireDuMilieu;
    private final boolean Harmonie;

    // Constructors :
    public Game(){
        this.EmpireDuMilieu = false;
        this.Harmonie = false;
    }
    public Game(boolean EmpireDuMilieu, boolean Harmonie){
        this.EmpireDuMilieu = EmpireDuMilieu;
        this.Harmonie = Harmonie;
    }

    public void play() {

        // Création des joueurs
        createPlayers();
        System.out.println("Les joueurs ont bien été créés.");

        // Création des royaumes 5x5 pour chaque joueur
        initialiseKingdoms();
        System.out.println("Les " + nbPlayers + " royaumes ont bien été créés.");

        // Affichage des royaumes :
        for (Kingdom kingdom: kingdoms){
            kingdom.print();
        }

        // Création de la pioche, remplie du bon nombre de dominos et mélangée
        try {
            initialiseDrawPile(nbPlayers);
            System.out.println("La pioche a été mélangée et contient " + drawPile.size() + " dominos.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


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


        // Début du jeu : création du banc et configuration du premier tour
        bench = new Bench(nbKings);
        bench.drawFirstLane(drawPile);        // On rempli la lane de gauche pour la première fois en piochant au hasard des dominos
        ChooseDominosFirstRound();
        bench.print();
        System.out.println("Tous les dominos ont été selectionnés par les joueurs. Celui avec la plus petite valeur commence en premier.");
        boolean firstDrawRight = true;

        do {
            //Debut de Tour
            System.out.println("On pioche " + nbKings + " nouveaux dominos.");
            if (firstDrawRight){
                bench.drawSecondLane(drawPile);
                firstDrawRight = false;
            }
            else {
                bench.drawDominos(drawPile); // On actualise le banc en piochant des nouveaux dominos
            }
            ChooseDominos();
            System.out.println("Dominos restants : " + drawPile.size());

            // TODO INTERFACE Disposer ces dominos face numérotée visible et rangés par ordre croissant
            for (int i=0; i<kings.length; i++) {
                //Joue
                // TODO Récupérer le domino sur lequel son roi se trouvait
                // TODO Placer ce domino dans le royaume en respectant les règles de connexion.
                // -> L'user choisit les coordonnées de la position en haut à gauche (entre 0 et 4 pour chaque coordonnée) et l'orientation entre 1 et 4 (3 inputs ou interface graphique)
                // Bouton Move pour déplacer le board sur l'interface graphique (ou input 0 pour pas de déplacement ou direction et nombre)
                // TODO Sélectionner un domino de la ligne suivante en y plaçant son roi.
            }
        } while(!drawPile.isEmpty());
        //Tous les dominos ont été piochés et posés

        // Calcul des scores et détermination des gagnants
        findWinners();
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
        this.nbKings = (nbPlayers==3 ? 3 : 4);   // Taille 3 si 3 joueurs, taille 4 si 2 ou 4 joueurs
        this.kings = new King[nbKings];

        // TODO créer fonctions Player.chooseName et Player.chooseColor

        // Noms et couleurs des joueurs :
        for(int i = 1; i<=nbPlayers; i++) {
            int colorNumber = 0;

            System.out.println("Saisir le nom du joueur " + i + " : ");
            String name = scanner.nextLine();

            // S'il y a un choix à faire parmi plusieurs couleurs
            if (colorsMap.size()>1) {
                do {
                    String listeCouleurs = String.join(", ", colorsMap.keySet()).replaceAll(", (?=[A-Za-z]*$)"," ou ");
                    System.out.println("Saisir la couleur du joueur " + i + " parmi " + listeCouleurs + " : ");
                    String color_input = scanner.nextLine();
                    color_input = color_input.substring(0, 1).toUpperCase() + color_input.substring(1).toLowerCase();   // On met la première lettre en majuscules et le reste en minsucules

                    if (colorsMap.containsKey(color_input)) {
                        colorNumber = colorsMap.get(color_input);
                        colorsMap.remove(color_input);
                    }

                } while (colorNumber == 0);
            }
            // S'il n'en reste qu'une
            else {
                String colorName = (String) colorsMap.keySet().toArray()[0];
                colorNumber = colorsMap.get(colorName);
                colorsMap.remove(colorName);
                System.out.println("Le joueur " + i + " aura la couleur " + colorName + ".");
            }

            players[i-1] = new Player(name, colorNumber);

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
    }

    public void initialiseKingdoms() {
        this.kingdoms = new Kingdom[nbPlayers];

        for(int i = 0; i<nbPlayers; i++) {
            kingdoms[i] = new Kingdom(1,players[i]);	// taille 1 car uniquement le château au départ
            players[i].setKingdom(kingdoms[i]);

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
        for (String line : dominos) {
            String[] infosDomino = line.split(",");
            LandPiece landPiece1 = new LandPiece(infosDomino[1], Integer.parseInt(infosDomino[0]));
            LandPiece landPiece2 = new LandPiece(infosDomino[3], Integer.parseInt(infosDomino[2]));
            Domino domino = new Domino(landPiece1, landPiece2, Integer.parseInt(infosDomino[4]));
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
    }

    public void ChooseDominosFirstRound(){
        LinkedHashMap<Integer, Domino> dominosToSelect = new LinkedHashMap<>();
        LinkedList<King> kingsToPlay = new LinkedList(Arrays.asList(kings));   //On récupère les rois.

        // Au premier Tour l'ordre est au hasard -> mélange des rois.
        Collections.shuffle(kingsToPlay);

        // HashMap des dominos disponibles sur la lane
        for (int j=1; j<=kings.length; j++){
            dominosToSelect.put(bench.getDominosValues(1)[j-1], bench.getLane(1)[j-1]);
        }

        // Pour chaque roi le joueur correspondant choisit un domino.
        for (int i=1; i<=nbKings; i++){

            bench.print();

            // Premier roi de la liste précédement mélangée puis le suivant à la prochaine boucle.
            King king = kingsToPlay.get(0);
            Player currentPlayer = king.getPlayer();
            System.out.println(currentPlayer.getName() + " " + currentPlayer.getColorEmoji() + " a été sélectionné au hasard.");

            // Si on a le choix entre les dominos, on demande l'user input
            if (dominosToSelect.size()>1) {
                int dominoNumber = currentPlayer.chooseDomino(dominosToSelect);     // On choisit le numéro du domino (input de l'user)
                Domino domino = dominosToSelect.get(dominoNumber);
                domino.setKing(king);                                               // On pose le King sur le domino
                king.setCurrentDomino(domino);                                      // On update le domino sur le King
                dominosToSelect.remove(dominoNumber);                               // On l'enlève des dominos à sélectionner
            }
            // S'il ne reste qu'un domino on lui attribue automatiquement
            else {
                int dominoNumber = (int) dominosToSelect.keySet().toArray()[0];   // On prend le domino restant
                dominosToSelect.get(dominoNumber).setKing(king);   // On pose le King sur le domino
                System.out.println("Le domino " + dominoNumber + " est attribué automatiquement à " + currentPlayer.getName() + " " + currentPlayer.getColorEmoji() + ".");
                dominosToSelect.remove(dominoNumber);
            }

            kingsToPlay.remove(0);
        }
    }

    public void ChooseDominos(){
        LinkedHashMap<Integer, Domino> dominosToSelect = new LinkedHashMap<>();
        LinkedList<King> kingsToPlay = new LinkedList(Arrays.asList(kings));   //On récupère les rois.

        // HashMap des dominos disponibles sur la lane
        for (int j=1; j<=kings.length; j++){
            dominosToSelect.put(bench.getDominosValues(2)[j-1], bench.getLane(2)[j-1]);
        }

        // Pour chaque roi le joueur correspondant choisit un domino.
        for (int i=1; i<=nbKings; i++){

            bench.print();

            // TODO ordre joueur en fonction des rois sur les dominos
            King king = kingsToPlay.get(0);
            Player currentPlayer = king.getPlayer();

            // Si on a le choix entre les dominos, on demande l'user input
            if (dominosToSelect.size()>1) {
                int dominoNumber = currentPlayer.chooseDomino(dominosToSelect);     // On choisit le numéro du domino (input de l'user)
                Domino domino = dominosToSelect.get(dominoNumber);
                domino.setKing(king);                                               // On pose le King sur le domino
                king.setCurrentDomino(domino);                                      // On update le domino sur le King
                dominosToSelect.remove(dominoNumber);                               // On l'enlève des dominos à sélectionner
            }
            // S'il ne reste qu'un domino on lui attribue automatiquement
            else {
                int dominoNumber = (int) dominosToSelect.keySet().toArray()[0];   // On prend le domino restant
                dominosToSelect.get(dominoNumber).setKing(king);   // On pose le King sur le domino
                System.out.println("Le domino " + dominoNumber + " est attribué automatiquement à " + currentPlayer.getName() + " " + currentPlayer.getColorEmoji() + ".");
                dominosToSelect.remove(dominoNumber);
            }

            kingsToPlay.remove(0);
        }
    }

    public void findWinners(){
        // Calcul du score final par joueur
        ArrayList<Integer> scoreByPlayer = new ArrayList<>();
        int longestName = 0;
        int highscore = 0;
        for (int i=0; i<nbPlayers; i++){
            // Partie recherche du nom le plus long pour l'affichage
            String PlayerName = players[i].getName();
            int lengthPlayerName = PlayerName.length();
            if (lengthPlayerName > longestName){
                longestName = lengthPlayerName;
            }
            // Partie calcul du score
            Kingdom playerKingdom = kingdoms[i];
            int playerScore = 0;
            for (Domain domain: playerKingdom.getDomains()){
                playerScore += domain.getLandpieces().size() * domain.getCrownNumber();     // Chaque domaine rapporte n = nbCases x nbCouronnes
            }
            // Bonus si activés
            if (EmpireDuMilieu){
                if(playerKingdom.getCells()[2][2].getCurrentLandPiece().getType().equals("Chateau")){
                    System.out.println(PlayerName + " : bonus Empire du milieu !");
                    playerScore += 10;
                }
            }
            if (Harmonie){
                int countEmpty = 0;
                Cell[][] cells = playerKingdom.getCells();
                for (int x=0; x<5; x++) {
                    for (int y = 0; y < 5; y++) {
                        if (cells[y][x].isEmpty()){
                            countEmpty++;
                        }
                    }
                }
                if (countEmpty==0){
                    System.out.println(PlayerName + " : bonus Harmonie !");
                    playerScore+=5;
                }
            }
            scoreByPlayer.add(playerScore);
            // Partie recherche du meilleur score
            if (playerScore > highscore){
                highscore = playerScore;
            }
        }
        // Affichage des scores
        System.out.println();
        System.out.println("Les scores finaux sont :");
        String format = "%" + (longestName+3) + "s%s%n";
        for (int i=0; i<nbPlayers; i++){
            System.out.printf(format, players[i].getName() + " : ", scoreByPlayer.get(i));       // On aligne les noms à droite
        }
        System.out.println();
        // Détermination du ou des gagnants potentiels à ce stade
        ArrayList<Player> potentialWinners = new ArrayList<>();
        for (int i=0; i<nbPlayers; i++){
            if (scoreByPlayer.get(i)==highscore){
                potentialWinners.add(players[i]);
            }
        }
        // S'il y a un seul gagnant, on l'affiche tout de suite, sinon on essaye de les départager
        if (potentialWinners.size()==1){
            System.out.println(potentialWinners.get(0).getName() + " a gagné !");
        }
        else{
            System.out.println("Il y a égalité entre " + potentialWinners.size() + " joueurs !");
            System.out.println("On regarde quel joueur a construit le domaine le plus étendu.");
            // On calcule la taille du plus grand domaine de chaque joueur
            ArrayList<Integer> maxDomainSizeByPlayer = new ArrayList<>();
            int biggestDomain = 0;
            for (Player player : potentialWinners){
                ArrayList<Domain> playersDomains = player.getKingdom().getDomains();
                int maxDomainSize = 0;
                for (Domain domain : playersDomains){
                    int domainSize = domain.getLandpieces().size();
                    if (domainSize > maxDomainSize){
                        maxDomainSize = domainSize;
                    }
                }
                maxDomainSizeByPlayer.add(maxDomainSize);
                if (maxDomainSize > biggestDomain){
                    biggestDomain = maxDomainSize;
                }
            }
            // Détermination du ou des gagnants potentiels à ce nouveau stade
            ArrayList<Player> potentialWinners2 = new ArrayList<>();
            for (int i=0; i<potentialWinners.size(); i++){
                if (maxDomainSizeByPlayer.get(i)==biggestDomain){
                    potentialWinners2.add(potentialWinners.get(i));
                }
            }
            // S'il y a un seul gagnant, on l'affiche tout de suite, sinon on essaye de les départager
            if (potentialWinners2.size()==1){
                System.out.println(potentialWinners2.get(0).getName() + " a gagné !");
            }
            else {
                System.out.println("Il y a égalité entre " + potentialWinners2.size() + " joueurs pour la construction du domaine le plus étendu !");
                System.out.println("On regarde le nombre de couronnes par joueur.");
                // On calcule le nombre de couronnes de chaque joueur
                ArrayList<Integer> crownNumberByPlayer = new ArrayList<>();
                int biggestCrownNumber = 0;
                for (Player player : potentialWinners2){
                    ArrayList<Domain> playersDomains = player.getKingdom().getDomains();
                    int crownNumber = 0;
                    for (Domain domain : playersDomains){
                        crownNumber += domain.getCrownNumber();
                    }
                    crownNumberByPlayer.add(crownNumber);
                    if (crownNumber > biggestCrownNumber){
                        biggestCrownNumber = crownNumber;
                    }
                }
                // Détermination du ou des gagnants potentiels à ce dernier stade
                ArrayList<Player> potentialWinners3 = new ArrayList<>();
                for (int i=0; i<potentialWinners2.size(); i++){
                    if (crownNumberByPlayer.get(i)==biggestCrownNumber){
                        potentialWinners3.add(potentialWinners2.get(i));
                    }
                }
                // S'il y a un seul gagnant, on l'affiche, sinon on affiche l'égalité
                if (potentialWinners3.size()==1){
                    System.out.println(potentialWinners3.get(0).getName() + " a gagné !");
                }
                else {
                    StringBuilder winnerList = new StringBuilder();
                    for (Player winner:potentialWinners3){
                        winnerList.append(winner.getName()).append(", ");
                    }
                    String winnerListStr = winnerList.toString().replaceAll(", (?=[A-Za-z]*$)","");
                    winnerListStr = winnerListStr.replaceAll(", (?=[A-Za-z]*$)"," et ");
                    System.out.println("Impossible de déterminer un gagnant ! Il y a égalité entre " + winnerListStr + ".");
                }
            }

        }
    }
}