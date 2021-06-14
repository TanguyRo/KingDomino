package DomiNations;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    // Getters

    public Player[] getPlayers() {
        return players;
    }

    public Kingdom[] getKingdoms() {
        return kingdoms;
    }

    public King[] getKings() {
        return kings;
    }

    public LinkedList<Domino> getDrawPile() {
        return drawPile;
    }

    public Bench getBench() {
        return bench;
    }

    public int getNbPlayers() {
        return nbPlayers;
    }

    public int getNbKings() {
        return nbKings;
    }

    // Functions

    /*

    public void play() {

        // Création des joueurs
        createPlayers();
        System.out.println("Les joueurs ont bien été créés.");

        // Création des royaumes 5x5 pour chaque joueur
        //initialiseKingdoms();
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

        // Début du jeu : création du banc et configuration du premier tour
        bench = new Bench(nbKings);
        bench.drawFirstLane(drawPile);        // On rempli la lane de gauche pour la première fois en piochant au hasard des dominos
        chooseDominosFirstRound();
        bench.print();
        System.out.println("Tous les dominos ont été sélectionnés par les joueurs. Celui avec la plus petite valeur commence en premier.");

        boolean firstDrawRight = true;
        boolean lastRound = false;
        boolean play = true;

        while (play) {
            LinkedHashMap<Integer, Domino> dominosToSelect = new LinkedHashMap<>();
            if (!lastRound){
                // Début de tour : on décale la lane de droite à gauche et on pioche des nouveaux dominos
                System.out.println("On pioche " + nbKings + " nouveaux dominos. Il en reste " + drawPile.size() + ".");
                if (firstDrawRight){
                    bench.drawSecondLane(drawPile);
                    firstDrawRight = false;
                }
                else {
                    bench.drawDominos(drawPile); // On actualise le banc en piochant des nouveaux dominos
                }
                // TODO Interface : décalage du banc + actualisation des nouveaux dominos (numéro puis face)

                // On prépare la liste des dominos disponibles sur la lane de droite
                Domino[] secondLane = bench.getLane(2);
                for (int j=1; j<=kings.length; j++){
                    Domino domino = secondLane[j-1];
                    dominosToSelect.put(domino.getNumber(), domino);
                }
            }

            // On prépare la liste des rois triée dans l'ordre de jeu
            LinkedList<King> kingsToPlay = new LinkedList<King>();
                // Les dominos du banc sont déjà triés, on reprend les rois correspondants à chaque domino
            for (Domino domino: bench.getLane(1)) {
                kingsToPlay.add(domino.getKing());
            }

            // Joueur par joueur, on choisi un domino et on place l'ancien
            for (int i=1; i<=nbKings; i++){

                // On récupère le roi et le joueur
                King king = kingsToPlay.getFirst();
                Player currentPlayer = king.getPlayer();
                Kingdom kingdom = currentPlayer.getKingdom();

                // On note le domino choisi au tour précédent
                Domino domino = king.getCurrentDomino();

                // On en choisit un nouveau dans la lane de droite (ou on l'attribue si c'est le dernier restant)
                bench.print();
                kingdom.print();
                    // On ne choisit pas de domino au dernier tour
                if (!lastRound) {
                    chooseDomino(dominosToSelect, currentPlayer, king);
                    // TODO Interface : déplacement du king du domino de gauche à celui choisi à droite
                }

                // On place le domino choisi au tour précédent dans le royaume en respectant les règles de connexion.
                System.out.println("Vous devez placer le domino " + domino.toString() + " .");
                if(!(currentPlayer instanceof NPC)){
                    // Si c'est un "vrai" joueur, on lui demande les coordonnées pour placer le domino
                    // Le joueur choisit les coordonnées de la position en haut à gauche (entre 0 et 4 pour chaque coordonnée) et l'orientation entre 1 et 4 (3 inputs ou interface graphique)

                    boolean placeDomino = currentPlayer.askKeepDomino();
                    if (placeDomino) {
                        int[] positionToPlace = null;
                        while (positionToPlace == null) {
                            try {
                                currentPlayer.askMove();
                                positionToPlace = currentPlayer.choosePosition();                                                                       // On demande des coordonnées valides
                                currentPlayer.getKingdom().placeDomino(domino, positionToPlace[0], new int[]{positionToPlace[1], positionToPlace[2]});   // On essaye de poser à ces coordonnées
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                                positionToPlace = null;
                            }
                        }
                    }
                    else {
                        System.out.println("Le domino sera défaussé.");
                    }
                }
                else {
                    // Si c'est un NPC, on choisit et on place directement
                    // On choisit des positions
                    int[][] positionsToPlace = ((NPC) currentPlayer).choosePosition(domino);
                    boolean possiblePlacement = true;
                    for (int a = 0; a < 2; a++) {
                        for (int b = 0; b < 2; b++) {
                            if (positionsToPlace[a][b] == -1) {
                                possiblePlacement = false;
                                break;
                            }
                        }
                    }
                    // Si le domino est placable on le place, sinon on le défausse
                    if (possiblePlacement) {
                        // On remplit le tableau des collateralCells pour chaque LandPiece
                        Cell[][] cells = currentPlayer.getKingdom().getCells();
                        Cell[][] collateralCells = new Cell[2][4];
                        // Pour chaque LandPiece
                        for (int j = 0; j < 2; j++) {
                            ArrayList<HashMap<Character, Integer>> collateralCellsPosition = Kingdom.getCollateralCellsPositions(positionsToPlace[j]);
                            // Pour chaque case
                            for (int k = 0; k < 4; k++) {
                                HashMap<Character, Integer> sideCellPosition = collateralCellsPosition.get(k);
                                int x = sideCellPosition.get('x');
                                int y = sideCellPosition.get('y');
                                if (x >= 0 && x <= 4 && y >= 0 && y <= 4) {
                                    collateralCells[j][k] = cells[y][x];
                                } else {
                                    collateralCells[j][k] = null;
                                }

                            }
                        }
                        // On pose
                        currentPlayer.getKingdom().placeLandPiecesFromDomino(domino, positionsToPlace, collateralCells);
                    }
                    else {
                        System.out.println("Le domino n'est plus plaçable, il sera défaussé.");
                    }
                }
                kingdom.print();

                // Le roi a joué donc on l'enlève
                kingsToPlay.removeFirst();
            }

            // Tout le monde a joué, c'est la fin du tour

            // Si c'était le dernier tour on arrête le jeu
            if (lastRound){
                play = false;
            }

            // Si on a pioché ici les derniers dominos, il reste un dernier tour pour les poser
            if (drawPile.isEmpty()){
                lastRound = true;
            }
        }

        // Calcul des scores et détermination des gagnants
        findWinners();


    }
    */

    public void createPlayersAndKings(ArrayList<ArrayList<Image>> kingsImages) {
        //Utilisation d'un dictionnaire pour proposer les couleurs
        String[] colors={"Rose", "Jaune", "Vert", "Bleu"};
        HashMap<String, Integer> colorsToSelect = new HashMap<>();
        for (int i=1; i<=4; i++){
            colorsToSelect.put(colors[i-1],i);
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

        // On demande le nombre de NPC parmi les nbPlayers joueurs
        int nbNPC;
        do {
            System.out.println("Combien de joueurs seront des ordinateurs ? (au maximum " + Integer.toString(nbPlayers-1) + ") :");
            nbNPC = scanner.nextInt();
            scanner.nextLine();
        }while(nbNPC<0 || nbNPC>nbPlayers);
        // TODO remettre nbNPC>nbPlayers-1

        // Noms et couleurs des joueurs "rééls":
        for(int i = 1; i<=nbPlayers-nbNPC; i++) {
            // On crée le joueur
            Player playerInCreation = new Player();

            // On définit son nom (input)
            playerInCreation.chooseName(i);

            // Pour la couleur
                // Si on a le choix entre plusieurs couleurs, on demande l'user input
            if (colorsToSelect.size()>1) {
                playerInCreation.chooseColor(i, colorsToSelect);     // On choisit la couleur (input de l'user)
            }
                // S'il ne reste qu'une couleur on lui attribue automatiquement
            else {
                String colorName = (String) colorsToSelect.keySet().toArray()[0];   // On récupère la dernière couleur
                int colorNumber = colorsToSelect.get(colorName);                    // On cherche le numéro correspondant
                playerInCreation.setColor(colorNumber);                             // On attribue la couleur au joueur
                colorsToSelect.remove(colorName);                                   // On enlève la couleur de la liste
                System.out.println("Le joueur " + i + " aura la couleur " + colorName + ".");
            }

            players[i-1] = playerInCreation;

            // Création des rois en fonction du nombre de joueurs
            createKing(playerInCreation, i, kingsImages.get(i-1));
        }
        // Noms et couleurs des NPC
        for(int i = nbPlayers-nbNPC+1; i<=nbPlayers; i++) {
            // On crée le joueur
            NPC playerInCreation = new NPC();

            // On définit son nom (aléatoire dans la liste) et sa couleur (aléatoire dans la liste des couleurs restantes)
            playerInCreation.chooseName();
            playerInCreation.chooseColor(colorsToSelect);
            System.out.println(playerInCreation.getName() + " sera le joueur " + i + ". Il/elle jouera en " + playerInCreation.getColorName().toLowerCase() + ".");

            players[i-1] = playerInCreation;

            // Création des rois en fonction du nombre de joueurs
            createKing(playerInCreation, i, kingsImages.get(i-1));
        }
    }

    // Fonction de base utilisée pour créer les kings dans createPlayersAndKings
    private void createKing(Player playerInCreation, int i, ArrayList<Image> kingsImagesOfPlayer){
        if (nbPlayers==3 || nbPlayers==4){
            King onlyKing = new King(players[i-1]);
            onlyKing.setImage(kingsImagesOfPlayer.get(0));
            kings[i-1] = onlyKing;
        }
        else if (nbPlayers == 2){
            King firstKing = new King(playerInCreation);
            firstKing.setImage(kingsImagesOfPlayer.get(0));
            kings[2*i-2] = firstKing;                           // 0 pour le joueur 1 et 3 pour le joueur 2
            King secondKing = new King(playerInCreation);
            secondKing.setImage(kingsImagesOfPlayer.get(1));
            kings[2*i-1] = secondKing;                          // 1 pour le joueur 1 et 2 pour le joueur 2
        }
    }

    public void initialiseKingdoms(ArrayList<ArrayList<ArrayList<ImageView>>> gridCells, ArrayList<Image> castleImages) {
        this.kingdoms = new Kingdom[nbPlayers];

        for(int i = 0; i<nbPlayers; i++) {
            Player player = players[i];
            kingdoms[i] = new Kingdom(1,player);	// taille 1 car uniquement le château au départ
            player.setKingdom(kingdoms[i]);

            ArrayList<ArrayList<ImageView>> playerGrid = gridCells.get(i);

            Cell[][] cells = new Cell[5][5];

            for (int x=0; x<5; x++){
                for (int y=0; y<5; y++){
                    Cell cell = new Cell(new int[] {x,y},true,null);
                    cell.setImageView(playerGrid.get(y).get(x));
                    cells[y][x] = cell;
                }
            }

            // Case du chateau
            LandPiece castle = new LandPiece("Chateau",0);
            castle.setCurrentCell(cells[2][2]);
            castle.setImage(castleImages.get(player.getColorNumber()-1));
            cells[2][2].setEmpty(false);
            cells[2][2].setCurrentLandPiece(castle);

            kingdoms[i].setCells(cells);
            kingdoms[i].setCastle(castle);
        }
    }

    public void initialiseDrawPile(int nbPlayers, ArrayList<ArrayList<Image>> dominosImages) throws FileNotFoundException {
        // scanner va lire le contenu du fichier .csv
        Scanner scanner = new Scanner(new File("src/ressources/dominos.csv"));
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

        // Itérateur pour les Image des dominos
        Iterator<ArrayList<Image>> dominosImagesIterator = dominosImages.iterator();

        // Création de tous les dominos et placement dans l'ordre dans la pile drawPile
        for (String line : dominos) {
            ArrayList<Image> dominoImages = dominosImagesIterator.next();       // On récupère les objets Image des 2 LandPieces du domino
            String[] infosDomino = line.split(",");
            int dominoNumber = Integer.parseInt(infosDomino[4]);
            LandPiece landPiece1 = new LandPiece(infosDomino[1], Integer.parseInt(infosDomino[0]));
            landPiece1.setImage(dominoImages.get(0));
            LandPiece landPiece2 = new LandPiece(infosDomino[3], Integer.parseInt(infosDomino[2]));
            landPiece2.setImage(dominoImages.get(1));
            Domino domino = new Domino(landPiece1, landPiece2, dominoNumber);
            drawPile.add(domino);
        }

        // Mélange des dominos
        for (int i = 0; i < 10; i++) {
            Collections.shuffle(drawPile);
        }

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

    public void chooseDominosFirstRound(){
        LinkedHashMap<Integer, Domino> dominosToSelect = new LinkedHashMap<>();
        LinkedList<King> kingsToPlay = new LinkedList<King>(Arrays.asList(kings));   // On récupère les rois

        // Au premier Tour l'ordre est au hasard -> mélange des rois.
        Collections.shuffle(kingsToPlay);

        // HashMap des dominos disponibles sur la lane
        int[] dominosValues = bench.getDominosValues(1);
        Domino[] lane1 = bench.getLane(1);
        for (int j=1; j<=kings.length; j++){
            dominosToSelect.put(dominosValues[j-1], lane1[j-1]);
        }

        // Pour chaque roi le joueur correspondant choisit un domino.
        for (int i=1; i<=nbKings; i++){

            bench.print();

            // Premier roi de la liste précédement mélangée puis le suivant à la prochaine boucle.
            King king = kingsToPlay.getFirst();
            Player currentPlayer = king.getPlayer();
            if (kingsToPlay.size()>1){
                System.out.println(currentPlayer.getName() + " " + currentPlayer.getColorEmoji() + " a été sélectionné au hasard.");
            }
            else {
                System.out.println("Il ne reste plus que " + currentPlayer.getName() + " " + currentPlayer.getColorEmoji() + ".");
            }

            // Si on a le choix entre les dominos, on demande l'user input
            if (dominosToSelect.size()>1) {
                int dominoNumber = currentPlayer.chooseDominoNumber(dominosToSelect);     // On choisit le numéro du domino (input de l'user si vrai ou choix aléatoire si NPC)
                Domino domino = dominosToSelect.get(dominoNumber);
                domino.setKing(king);                                               // On pose le King sur le domino
                king.setCurrentDomino(domino);                                      // On update le domino sur le King
                dominosToSelect.remove(dominoNumber);                               // On l'enlève des dominos à sélectionner
            }
            // S'il ne reste qu'un domino on lui attribue automatiquement
            else {
                int dominoNumber = (int) dominosToSelect.keySet().toArray()[0];     // On prend le domino restant
                Domino domino = dominosToSelect.get(dominoNumber);
                domino.setKing(king);                                               // On pose le King sur le domino
                king.setCurrentDomino(domino);                                      // On update le domino sur le King
                System.out.println("Le domino " + dominoNumber + " : " +domino.toString() + " est attribué automatiquement à " + currentPlayer.getName() + " " + currentPlayer.getColorEmoji() + ".");
                dominosToSelect.remove(dominoNumber);
            }

            kingsToPlay.removeFirst();
        }
    }

    public void chooseDomino(LinkedHashMap<Integer, Domino> dominosToSelect, Player currentPlayer, King king){
        // Si on a le choix entre les dominos, on demande l'user input
        if (dominosToSelect.size()>1) {
            int dominoNumber = currentPlayer.chooseDominoNumber(dominosToSelect);     // On choisit le numéro du domino (input de l'user si vrai ou choix aléatoire si NPC)
            Domino domino = dominosToSelect.get(dominoNumber);
            domino.setKing(king);                                               // On pose le King sur le domino
            king.setCurrentDomino(domino);                                      // On update le domino sur le King
            dominosToSelect.remove(dominoNumber);                               // On l'enlève des dominos à sélectionner
        }
        // S'il ne reste qu'un domino on lui attribue automatiquement
        else {
            int dominoNumber = (int) dominosToSelect.keySet().toArray()[0];   // On prend le domino restant
            Domino domino = dominosToSelect.get(dominoNumber);
            domino.setKing(king);                                               // On pose le King sur le domino
            king.setCurrentDomino(domino);                                      // On update le domino sur le King
            System.out.println("Le domino " + dominoNumber + " : " +domino.toString() + " est attribué automatiquement à " + currentPlayer.getName() + " " + currentPlayer.getColorEmoji() + ".");
            dominosToSelect.remove(dominoNumber);
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
                playerScore += domain.getLandPieces().size() * domain.getCrownNumber();     // Chaque domaine rapporte n = nbCases x nbCouronnes
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
                    int domainSize = domain.getLandPieces().size();
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