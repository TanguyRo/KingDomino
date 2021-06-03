package DomiNations;

import java.util.ArrayList;
import java.util.HashMap;

public class Kingdom {
    private int size;
    private final Player player;
    private Cell[][] cells;
    private ArrayList<Domain> domains;


    public Kingdom(int size, Player player) {
        this.size = size;
        this.player = player;
        this.cells = new Cell[5][5];
        this.domains = new ArrayList<>();
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void upSize(int up) {
        this.size += up;
    }

    public int getSize() {
        return size;
    }

    public Player getPlayer() {
        return player;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void setDomains(ArrayList<Domain> domains) {
        this.domains = domains;
    }

    public ArrayList<Domain> getDomains() {
        return domains;
    }

    public void move(String direction){

        Cell[][] newCells = new Cell[5][5];
        switch (direction) {
            case "up":
                // Pour monter, la première ligne (row 0) doit être vide.
                for (int x=0; x<5; x++){
                    if(!cells[0][x].isEmpty()){
                        throw new IllegalStateException("Le royaume ne peut pas être déplacé vers le haut.");
                    }
                }
                for (int x=0; x<5; x++) {
                    for (int y = 0; y < 4; y++) {
                        newCells[y][x] = cells[y+1][x];
                    }
                    newCells[4][x] = new Cell(new int[]{x,4},true,null);
                }
                break;
            case "down":
                // Pour descendre, la dernière ligne (row 4) doit être vide.
                for (int x=0; x<5; x++){
                    if(!cells[4][x].isEmpty()){
                        throw new IllegalStateException("Le royaume ne peut pas être déplacé vers le bas.");
                    }
                }
                for (int x=0; x<5; x++) {
                    for (int y = 1; y < 5; y++) {
                        newCells[y][x] = cells[y-1][x];
                    }
                    newCells[0][x] = new Cell(new int[]{x,0},true,null);
                }
                break;
            case "left":
                // Pour déplacer à gauche, la première colonne (column 0) doit être vide
                for (int y=0; y<5; y++){
                    if(!cells[y][0].isEmpty()){
                        throw new IllegalStateException("Le royaume ne peut pas être déplacé vers la gauche.");
                    }
                }
                for (int y = 0; y < 5; y++) {
                    for (int x=0; x<4; x++) {
                        newCells[y][x] = cells[y][x+1];
                    }
                    newCells[y][4] = new Cell(new int[]{4,y},true,null);
                }
                break;
            case "right":
                // Pour déplacer à droite, la dernière colonne (column 4) doit être vide
                for (int y=0; y<5; y++){
                    if(!cells[y][4].isEmpty()){
                        throw new IllegalStateException("Le royaume ne peut pas être déplacé vers la gauche.");
                    }
                }
                for (int y = 0; y < 5; y++) {
                    for (int x=1; x<5; x++) {
                        newCells[y][x] = cells[y][x - 1];
                    }
                    newCells[y][0] = new Cell(new int[]{0,y},true,null);
                }
                break;
        }
        cells = newCells;		// on remplace par le nouveau "board"
    }

    public void placeDomino(Domino domino, int orientation, int[] upLeftPosition){
        // domino : landpiece 1 et landpiece 2
        // orientation : 1 = horizontal 12, 2 = vertical 12, 3 = horizontal 21, 4 = vertical 21
        // upLeftPosition : {x,y} compris entre 0 et 4 de la case en haut à gauche
        // cells : attribut du kingdom, tableau 5x5 du joueur

        // Calcul des positions pour poser
        int[][] landPiecesPositions = new int[2][2];
        switch (orientation){
            case 1:
                landPiecesPositions[0] = upLeftPosition;                                        // LandPiece1
                landPiecesPositions[1] = new int[] {upLeftPosition[0]+1,upLeftPosition[1]};     // LandPiece2
                break;
            case 2:
                landPiecesPositions[0] = upLeftPosition;
                landPiecesPositions[1] = new int[] {upLeftPosition[0],upLeftPosition[1]+1};
                break;
            case 3:
                landPiecesPositions[0] = new int[] {upLeftPosition[0]+1,upLeftPosition[1]};
                landPiecesPositions[1] = upLeftPosition;
                break;
            case 4:
                landPiecesPositions[0] = new int[] {upLeftPosition[0],upLeftPosition[1]+1};
                landPiecesPositions[1] = upLeftPosition;
                break;
            default:
                throw new IllegalArgumentException("Argument impossible donné pour l'orientation : " + orientation);
        }

        // Vérifications :
            // Les dominos ne doivent pas sortir du board
            // (impossible de rentrer dans la fonction avec un domino complètement dehors mais si upLeftPosition est
            // contre un bord, une des LandPieces peut se retrouver dehors en fonction de l'orientation)
        for (int[] position : landPiecesPositions) {            // Pour les positions des 2 LandPieces
            for (int coordinate : position){                        // Pour chaque coordonnée x et y
                if (coordinate<0 || coordinate>4){
                    throw new IllegalArgumentException("Le domino ne peut pas sortir du terrain, veuillez changer de position ou déplacer votre royaume");
                }
            }
        }
            // Le domino ne peut pas être placé sur un autre : les deux cases doivent être vides
        int[] positionLP1 = landPiecesPositions[0];
        int[] positionLP2 = landPiecesPositions[1];
        if (!(this.cells[positionLP1[1]][positionLP1[0]].isEmpty() && this.cells[positionLP2[1]][positionLP2[0]].isEmpty())){
            throw new IllegalArgumentException("Le domino ne peut pas être placé sur un autre domino, veuillez changer de position");
        }
            // Le domino peut être placé que s'il touche directement un domaine existant du même type ou le château du royaume
        int countSameTypeLandPieces = 0;
        int countCastle = 0;
        Cell[][] collateralCells = new Cell[2][4];                                              // On stocke au fur et à mesure les Cells autour de chacun des 2 LandPiece
        for (int i=0; i<=1; i++) {                                                              // Pour chaque LandPiece
            int[] position = landPiecesPositions[i];                                                // On récupère la position demandée cette LandPiece
            String typeLandPiece = domino.getLandPiece(i + 1).getType();                      // On note son type

            // On prépare les tests et les positions des cells autour
            boolean[] edgeCheck = {position[1] != 0, position[0] != 4, position[1] != 4, position[0] != 0};    // On parcourt dans le sens haut-droite-bas-gauche et on regarde si ça ne touche pas le bord
            ArrayList<HashMap<Character, Integer>> collateralCellsPosition = getCollateralCellsPositions(position);

            // On regarde pour les 4 cases autour s'il y a une landPiece du même type ou le château
            for (int j = 0; j <= 3; j++) {                                                          // Pour chaque direction (haut-droite-bas-gauche)
                if (edgeCheck[j]) {                                                                     // Si la LandPiece ne touche pas le bord en question
                    HashMap<Character, Integer> sideCellPosition = collateralCellsPosition.get(j);                  // On note les coordonnées de la position à vérifier
                    Cell sideCell = cells[sideCellPosition.get('y')][sideCellPosition.get('x')];                    // On note la Cell du côté
                    collateralCells[i][j] = sideCell;                                                               // On la note (en dehors de la boucle) pour économiser la recherche ensuite (i pour le LandPiece, j pour le côté)
                    if (!sideCell.isEmpty()) {                                                              // S'il y a une LandPiece sur cette Cell
                        LandPiece sideLandPiece = sideCell.getCurrentLandPiece();                               // On la récupère
                        if (sideLandPiece.getType().equals(typeLandPiece)) {                                    // On incrémente le nombre de LandPieces du même type ou de château en fonction
                            countSameTypeLandPieces++;
                        } else if (sideLandPiece.getType().equals("Chateau")) {
                            countCastle++;
                        }
                    }
                }
                else {
                    collateralCells[i][j] = null;
                }
            }
        }
        // Après avoir vérifié chaque case autour du domino, on sait si un de ses LandPieces touche une LandPiece de même type
        if (!(countSameTypeLandPieces>=1 || countCastle>=1)){
            throw new IllegalArgumentException("Le domino doit être connecté à un domaine existant ou au château, veuillez changer de position");
        }

        // Pose des 2 LandPiece
        for (int i=0; i<=1; i++){

            LandPiece landPiece = domino.getLandPiece(i+1);

            // Pose de la LandPiece dans les cells
            int[] position = landPiecesPositions[i];                // On récupère la position calculée au préalable pour cette LandPiece
            Cell cell = this.cells[position[1]][position[0]];       // On récupère la Cell associée (row y, column x)
            landPiece.setCurrentCell(cell);                         // On attribue à la LandPiece sa nouvelle Cell
            cell.setCurrentLandPiece(landPiece);                    // On attribue à la Cell sa nouvelle LandPiece
            cell.setEmpty(false);                                   // On passe le isEmpty de la Cell à false

            // On regarde pour les 4 cases autour s'il y a une landPiece du même type, si oui on regroupe les domaines
            String typeLandPiece = landPiece.getType();
            ArrayList<Domain> collateralDomains = new ArrayList<>();
            for (int j=0; j<=3; j++){                                       // Pour chaque direction (haut-droite-bas-gauche)
                Cell sideCell = collateralCells[i][j];                          // On note la Cell à côté (notée en haut et non déplacée)
                if (sideCell != null && !sideCell.isEmpty()){                       // S'il y en a bien une (mise à null en haut si on touche le bord et donc qu'il n'y en a pas), et qu'il y a une LandPiece dessus
                    LandPiece sideLandPiece = sideCell.getCurrentLandPiece();
                    if (sideLandPiece.getType().equals(typeLandPiece)){                 // Si la LandPiece à côté est du même type que la LandPiece qu'on pose, alors on l'ajoute au domaine existant
                        Domain sideDomain = sideLandPiece.getParentDomain();                // On note le domaine à côté
                        if(collateralDomains.size()==1){                                    // Si le LandPiece est aussi en contact avec un premier domaine dont on s'est déjà occupé (possible au deuxième tour de boucle)
                            fusion(collateralDomains.get(0),sideDomain);                    // On fusionne ces deux domaines en un seul (celui de gauche)
                        }
                        else{                                                               // Si c'est le premier domaine collatéral qu'on vérifie, on ajoute landPiece1 à celui-ci
                            collateralDomains.add(sideDomain);                                  // On l'ajoute à la liste des domains de même type autour
                            sideDomain.getLandpieces().add(landPiece);                          // On ajoute la LandPiece au domaine existant
                            landPiece.setParentDomain(sideDomain);                              // On indique dans LandPiece son nouveau domaine parent
                            sideDomain.addCrowns(landPiece.getCrownNumber());                   // On met à jour le nombre total de Crowns du domaine
                        }
                    }

                }
            }

            // Si la nouvelle LandPiece ne s'ajoute à aucun domaine existant, on crée un domaine
            if(collateralDomains.size()==0){
                Domain newDomain = new Domain(this, typeLandPiece, landPiece.getCrownNumber(), landPiece);   // On crée un nouveau domaine, composé d'une seul LandPiece
                this.domains.add(newDomain);            // On ajoute le nouveau domaine au royaume que l'on modifie
                landPiece.setParentDomain(newDomain);   // On indique dans LandPiece son nouveau domaine parent
            }

        }

        // Augmentation de la taille du royaume de 2 landpieces
        this.upSize(2);

        // On a bien posé les 2 LandPieces
    }

    public void fusion(Domain domain1, Domain domain2){
        // Kingdom et Type non-modifié

        // LandPieces du domain2 ajoutées au domain1
        ArrayList<LandPiece> landPiecesDomain1 = domain1.getLandpieces();
        ArrayList<LandPiece> landPiecesDomain2 = domain2.getLandpieces();
        for (LandPiece landPieceToMove : landPiecesDomain2) {
            landPiecesDomain1.add(landPieceToMove);             // On l'ajoute à la liste des LandPieces du doimain1
            landPieceToMove.setParentDomain(domain1);           // On met à jour son domaine parent
        }

        // CrownNumber mis à jour
        domain1.addCrowns(domain2.getCrownNumber());

        // Suppression du domain2
        this.domains.remove(domain2);

        // Le domain1 correspond désormais à la fusion des 2
    }

    public void print(){
        System.out.println("Royaume de " + player.getName() + " " + player.getColorEmoji() + " :");
        System.out.println("┌───────────────────┐");
        for (int y=0; y<5; y++){
            StringBuilder ligne = new StringBuilder();
            ligne.append("│ ");
            for (int x=0; x<5; x++){
                LandPiece landPiece = cells[y][x].getCurrentLandPiece();
                if (landPiece == null){
                    ligne.append("⬜️0 ");
                }
                else {
                    ligne.append(landPiece.toString()+" ");
                }
            }
            ligne.append("│");
            System.out.println(ligne);
        }
        System.out.println("└───────────────────┘");
    }

    public static ArrayList<HashMap<Character, Integer>> getCollateralCellsPositions(int[] position) {
        ArrayList<HashMap<Character, Integer>> collateralCellsPosition = new ArrayList<>();
        HashMap<Character, Integer> line1 = new HashMap<>();
        line1.put('x', position[0]);
        line1.put('y', position[1] - 1);
        collateralCellsPosition.add(line1);
        HashMap<Character, Integer> line2 = new HashMap<>();
        line2.put('x', position[0] + 1);
        line2.put('y', position[1]);
        collateralCellsPosition.add(line2);
        HashMap<Character, Integer> line3 = new HashMap<>();
        line3.put('x', position[0]);
        line3.put('y', position[1] + 1);
        collateralCellsPosition.add(line3);
        HashMap<Character, Integer> line4 = new HashMap<>();
        line4.put('x', position[0] - 1);
        line4.put('y', position[1]);
        collateralCellsPosition.add(line4);
        return collateralCellsPosition;
    }

}