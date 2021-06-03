package DomiNations;

// NPC -> Non-playable Character

import java.util.*;

public class NPC extends Player {
    private static final ArrayList<String> namesList = new ArrayList<>(Arrays.asList("Léa", "Thomas", "Manon", "Lucas", "Camille", "Théo", "Chloé", "Hugo", "Emma", "Maxime", "Marie", "Nicolas", "Océane", "Quentin", "Sarah", "Alexandre", "Laura", "Antoine", "Mathilde", "Clément", "Julie", "Alexis", "Marine", "Valentin", "Pauline", "Julien", "Lucie", "Romain", "Anaïs", "Florian", "Inès", "Louis", "Clara", "Benjamin", "Justine", "Paul", "Lisa", "Pierre", "Maeva", "Baptiste", "Juliette", "Enzo", "Émilie", "Kevin", "Morgane", "Adrien", "Charlotte", "Tom", "Eva", "Guillaume"));
    private static final Random random = new Random();

    // Le nom est directement atttribué au Player et retiré de la liste
    // @Override (paramètres différents donc pas d'Override)
    public void chooseName(){
        int randomIndex = random.nextInt(namesList.size());
        String randomName = namesList.get(randomIndex);
        namesList.remove(randomIndex);                    // Pour ne pas prendre 2 fois le même prénom
        name = randomName;
    }

    // La couleur est directement attribuée au Player et retirée du HashMap
    // @Override (paramètres différents donc pas d'Override)
    public void chooseColor(HashMap<String, Integer> colorsToSelect) {
        ArrayList<String> possibleColors = new ArrayList<>(colorsToSelect.keySet());    // On choisit une couleur au hasard dans la liste des restantes
        String chosenColor = possibleColors.get(random.nextInt(possibleColors.size()));
        color = new Color(colorsToSelect.get(chosenColor));     // On cherche le nombre correspondant à la couleur (différent de l'indice)
        colorsToSelect.remove(chosenColor);
    }

    // Le numéro du domino est renvoyé, mais le domino n'est pas supprimé du HashMap
    @Override
    public int chooseDominoNumber(HashMap<Integer, Domino> dominosToSelect) {
        ArrayList<Integer> possibleDominos = new ArrayList<>(dominosToSelect.keySet());    // On choisit une couleur au hasard dans la liste des restantes
        Integer chosenDominoNumber = possibleDominos.get(random.nextInt(possibleDominos.size()));
        System.out.println(this.getName() + " " + this.getColorEmoji() + " a choisi le domino " + chosenDominoNumber + ".");
        return chosenDominoNumber;
    }

    // @Override (paramètres différents donc pas d'Override)
    public int[][] choosePosition(Domino domino){

        // Fonctionnement :
        // On fonctionne par rapport aux types des 2 landpieces du Domino. On va chercher dans le Kingdom des landPieces du même type
        // et quand on en a une, on essaye de placer le domino : on cherche une case vide autour (ne sortant pas du tableau), puis
        // on tente les différentes positions pour poser la deuxième case

        LandPiece[] dominoLandPieces = new LandPiece[]{domino.getLandPiece(1),domino.getLandPiece(2)};

        String typeLandPiece1 = dominoLandPieces[0].getType();
        String typeLandPiece2 = dominoLandPieces[1].getType();
        String[] landPiecesTypes = (typeLandPiece1.equals(typeLandPiece2) ? new String[]{typeLandPiece1} : new String[]{typeLandPiece1,typeLandPiece2});    // Si les deux cases sont du même type on ne fera qu'un seul parcours

        int bestScore = -1;
        int[][] positionsToPlace = new int[2][];

        // On peut connecter le domino d'un côté ou de l'autre
        for (int i=0; i<landPiecesTypes.length; i++){
            String type = landPiecesTypes[i];
            // On parcourt les domaines de ce type

            for (Domain domain : this.kingdom.getDomains()){

                if (domain.getType().equals(type)){
                    // On parcourt les landPieces du domaine en question pour en trouver une ou l'on peut se coller
                    for (LandPiece landPiece : domain.getLandpieces()){
                        int[] landPiecePosition = landPiece.getCurrentCell().getPosition();

                        // On prépare les tests et les positions des cells autour
                        boolean[] edgeCheck = {landPiecePosition[1] != 0, landPiecePosition[0] != 4, landPiecePosition[1] != 4, landPiecePosition[0] != 0};    // On parcourt dans le sens haut-droite-bas-gauche et on regarde si ça ne touche pas le bord
                        ArrayList<HashMap<Character, Integer>> collateralCellsPosition = Kingdom.getCollateralCellsPositions(landPiecePosition);

                        // On regarde pour les 4 cases autour si l'une d'elles est vide (pour pouvoir poser la première landpiece)
                        for (int j = 0; j <= 3; j++) {                                                                  // Pour chaque direction (haut-droite-bas-gauche)
                            if (edgeCheck[j]) {                                                                             // Si la LandPiece ne touche pas le bord en question
                                HashMap<Character, Integer> sideCellPosition = collateralCellsPosition.get(j);                  // On note les coordonnées de la position à vérifier
                                int x = sideCellPosition.get('x');
                                int y = sideCellPosition.get('y');

                                Cell sideCell = this.kingdom.getCells()[y][x];  // On note la Cell du côté
                                if (sideCell.isEmpty()) {
                                    // Si la Cell est vide, on peut y placer notre première case
                                    // On cherche alors à placer la deuxième landPiece
                                    int[] firstEmptyCellPosition = new int[]{x,y};
                                    boolean[] newEdgeCheck = {firstEmptyCellPosition[1] != 0, firstEmptyCellPosition[0] != 4, firstEmptyCellPosition[1] != 4, firstEmptyCellPosition[0] != 0};    // On parcourt dans le sens haut-droite-bas-gauche et on regarde si ça ne touche pas le bord
                                    ArrayList<HashMap<Character, Integer>> newCollateralCellsPosition = Kingdom.getCollateralCellsPositions(firstEmptyCellPosition);

                                    // On regarde pour les 3 autres cases autour (4 - celle dont on vient) si l'une d'elles est vide (pour pouvoir poser la deuxième landpiece)
                                    for (int k = 0; k <= 3; k++) {                                                                  // Pour chaque direction (haut-droite-bas-gauche)
                                        // On saute le cas de la case de laquelle on vient : (j+2) modulo 4
                                        if ((k != (j + 2) % 4) && newEdgeCheck[j]) {
                                            HashMap<Character, Integer> newSideCellPosition = newCollateralCellsPosition.get(k);
                                            int x2 = newSideCellPosition.get('x');
                                            int y2 = newSideCellPosition.get('y');

                                            Cell newSideCell;
                                            if (x2 >= 0 && x2 <= 4 && y2 >= 0 && y2 <= 4) {
                                                newSideCell = this.kingdom.getCells()[y2][x2];
                                            }
                                            else {
                                                newSideCell = null;
                                            }
                                            if (newSideCell!= null && newSideCell.isEmpty()) {
                                                // Si la Cell est également vide, on a notre deuxième case et on peut alors placer le domino
                                                int[] secondEmptyCellPosition = new int[]{x2,y2};
                                                System.out.println("On a une position pour poser");

                                                // Calcul du nombre de points en plus (on ne fait la vérification que d'un côté, ce sera suffisant pour une première estimation)(si les deux sont du même type alors prendre en compte les deux est facile)
                                                int actualDomainScore = domain.getLandpieces().size() * domain.getCrownNumber();        // domain étant le domaine auquel on se colle
                                                int newDomainScore;
                                                if (landPiecesTypes.length == 2){
                                                    // Si les deux types sont différents on ne regarde que pour la pièce qui touche
                                                    newDomainScore = (domain.getLandpieces().size() + 1) * (domain.getCrownNumber() + dominoLandPieces[i].getCrownNumber());    // La taille serait +1 & le nombre de couronnes serait +le nombre de couronnes sur la pièce que l'on pose
                                                }
                                                else {
                                                    // Si les deux types sont identiques on compte les deux
                                                    newDomainScore = (domain.getLandpieces().size() + 2) * (domain.getCrownNumber() + dominoLandPieces[0].getCrownNumber() + dominoLandPieces[1].getCrownNumber());
                                                }
                                                int deltaDomainScore = newDomainScore - actualDomainScore;

                                                if (deltaDomainScore > bestScore){
                                                    System.out.println("Nouveau meilleur score");
                                                    // Ce placement est mieux que tous les précédents
                                                    bestScore = deltaDomainScore;
                                                    if (i==0){
                                                        // La landPiece de gauche est la première Cell
                                                        positionsToPlace[0] = firstEmptyCellPosition;
                                                        positionsToPlace[1] = secondEmptyCellPosition;
                                                    }
                                                    else {
                                                        // La landPiece de droite est la première Cell
                                                        positionsToPlace[0] = secondEmptyCellPosition;
                                                        positionsToPlace[1] = firstEmptyCellPosition;
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Si on n'a rien du tout, on retrouve la case du chateau pour s'y coller
        if (bestScore == -1) {
            int[] castlePosition = this.kingdom.getCastle().getCurrentCell().getPosition();
            ArrayList<int[][]> possiblePositions = new ArrayList<>();     // Ici toutes les positions collées au château sont équivalentes, la détermination se fera donc de manière aléatoire
            // On prépare les tests et les positions des cells autour
            boolean[] edgeCheck = {castlePosition[1] != 0, castlePosition[0] != 4, castlePosition[1] != 4, castlePosition[0] != 0};
            ArrayList<HashMap<Character, Integer>> collateralCellsPosition = Kingdom.getCollateralCellsPositions(castlePosition);
            // On regarde pour les 4 cases autour si l'une d'elles est vide (pour pouvoir poser la première landpiece)
            for (int j = 0; j <= 3; j++) {                                                                  // Pour chaque direction (haut-droite-bas-gauche)
                if (edgeCheck[j]) {                                                                             // Si la LandPiece ne touche pas le bord en question
                    HashMap<Character, Integer> sideCellPosition = collateralCellsPosition.get(j);                  // On note les coordonnées de la position à vérifier
                    int x = sideCellPosition.get('x');
                    int y = sideCellPosition.get('y');
                    Cell sideCell = this.kingdom.getCells()[y][x];  // On note la Cell du côté
                    if (sideCell.isEmpty()) {
                        // Si la Cell est vide, on peut y placer notre première case
                        // On cherche alors à placer la deuxième landPiece
                        int[] firstEmptyCellPosition = new int[]{x, y};
                        boolean[] newEdgeCheck = {firstEmptyCellPosition[1] != 0, firstEmptyCellPosition[0] != 4, firstEmptyCellPosition[1] != 4, firstEmptyCellPosition[0] != 0};    // On parcourt dans le sens haut-droite-bas-gauche et on regarde si ça ne touche pas le bord
                        ArrayList<HashMap<Character, Integer>> newCollateralCellsPosition = Kingdom.getCollateralCellsPositions(firstEmptyCellPosition);

                        // On regarde pour les 3 autres cases autour (4 - celle dont on vient) si l'une d'elles est vide (pour pouvoir poser la deuxième landpiece)
                        for (int k = 0; k <= 3; k++) {                                                                  // Pour chaque direction (haut-droite-bas-gauche)
                            // On saute le cas de la case de laquelle on vient : (j+2) modulo 4
                            if ((k != (j + 2) % 4) && newEdgeCheck[j]) {
                                HashMap<Character, Integer> newSideCellPosition = newCollateralCellsPosition.get(k);
                                int x2 = newSideCellPosition.get('x');
                                int y2 = newSideCellPosition.get('y');
                                Cell newSideCell = this.kingdom.getCells()[y2][x2];
                                if (newSideCell.isEmpty()) {
                                    // Si la Cell est également vide, on a notre deuxième case et on peut alors placer le domino
                                    int[] secondEmptyCellPosition = new int[]{x2, y2};
                                    possiblePositions.add(new int[][]{firstEmptyCellPosition, secondEmptyCellPosition});
                                }
                            }
                        }
                    }
                }
            }
            if (possiblePositions.size()>0){
                positionsToPlace = possiblePositions.get(random.nextInt(possiblePositions.size()));
            }
            else {
                positionsToPlace = new int[2][];
                positionsToPlace[0] = new int[]{-1,-1};
                positionsToPlace[1] = new int[]{-1,-1};
            }

        }

        // On arrive ici avec soit une position parmi les "meilleures" en terme de points gagnés, ou alors une position aléatoire autour du chateau s'il n'a aucun domaine existant à agrandir grâce au domino, ou alors que des -1 si le domino est implaçable
        return positionsToPlace;
    }
}
