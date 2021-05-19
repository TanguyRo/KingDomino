package DomiNations;

import java.util.ArrayList;

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
                for (int x=0; x<5; x++) {
                    for (int y = 0; y < 4; y++) {
                        newCells[y][x] = cells[y+1][x];
                    }
                }
                break;
            case "down":
                for (int x=0; x<5; x++) {
                    for (int y = 1; y < 5; y++) {
                        newCells[y][x] = cells[y-1][x];
                    }
                }
                break;
            case "left":
                for (int x=0; x<4; x++) {
                    for (int y = 0; y < 5; y++) {
                        newCells[y][x] = cells[y][x+1];
                    }
                }
                break;
            case "right":
                for (int x=1; x<5; x++) {
                    for (int y = 0; y < 5; y++) {
                        newCells[y][x] = cells[y][x - 1];
                    }
                }
                break;
        }
        cells = newCells;		// on remplace par le nouveau "board"
    }

    public boolean placeDomino(Domino domino, int orientation, int[] upLeftPosition){
        // domino : landpiece 1 et landpiece 2
        // orientation : 1 = horizontal 12, 2 = vertical 12, 3 = horizontal 21, 4 = vertical 21
        // upLeftPosition : {x,y} compris entre 0 et 4 de la case en haut à gauche
        // cells : attribut du kingdom, tableau 5x5 du joueur

        // TODO : Vérifications (à côté d'un domaine existant, ne dépasse pas la taille 5x5) -> return false si pas possible
        // TODO : Déplacement du board si besoin et si possible / décalage position ?

        // Calcul des positions pour poser
        int[][] landPiecesPositions = new int[2][];
        switch (orientation){
            case 1:
                landPiecesPositions[0] = upLeftPosition;
                landPiecesPositions[1] = new int[] {upLeftPosition[0]+1,upLeftPosition[1]};
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
                throw new IllegalStateException("Unexpected value: " + orientation);
        }

        // Boucle pour les 2 LandPiece
        for (int i=0; i<=1; i++){

            LandPiece landPiece = domino.getLandPiece(i+1);

            // Pose de la LandPiece dans les cells
            int[] position = landPiecesPositions[i];                // On récupère la position calculée au préalable pour cette LandPiece
            Cell cell = this.cells[position[1]][position[0]];       // On récupère la Cell associée (row y, column x)
            landPiece.setCurrentCell(cell);                         // On attribue à la LandPiece sa nouvelle Cell
            cell.setCurrentLandPiece(landPiece);                    // On attribue à la Cell sa nouvelle LandPiece
            cell.setState(false);                                   // On passe le isEmpty de la Cell à false

            // On regarde pour les 4 cases autour s'il y a une landPiece du même type, si oui on regroupe les domaines
            String typeLandPiece = landPiece.getType();
            boolean[] edgeCheck = {position[1]!=0,position[0]!=4,position[1]!=4,position[0]!=0};    // On parcourt dans le sens haut-droite-bas-gauche et on regarde si ça ne touche pas le bord
            Cell[] collateralCells = {cells[position[1]-1][position[0]],cells[position[1]][position[0]+1],cells[position[1]+1][position[0]],cells[position[1]][position[0]-1]}; // Cells en haut, à droite, en bas et à gauche
            ArrayList<Domain> collateralDomains = new ArrayList<>();
            for (int j=0; j<=3; j++){                                           // Pour chaque direction (haut-droite-bas-gauche)
                if(edgeCheck[j]){                                                   // Si la LandPiece ne touche pas le bord en question
                    Cell sideCell = collateralCells[j];
                    if (!sideCell.isEmpty()){                                           // S'il y a une LandPiece sur la case à côté
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
            }

            // Si la nouvelle LandPiece ne s'ajoute à aucun domaine existant, on crée un domaine
            if(collateralDomains.size()==0){
                Domain newDomain = new Domain(this,typeLandPiece, landPiece.getCrownNumber(), landPiece);   // On crée un nouveau domaine, composé d'une seul LandPiece
                this.domains.add(newDomain);            // On ajoute le nouveau domaine au royaume que l'on modifie
                landPiece.setParentDomain(newDomain);   // On indique dans LandPiece son nouveau domaine parent
            }

        }

        // Augmentation de la taille du royaume de 2 landpieces
        this.upSize(2);

        // Si tout s'est bien passé et qu'on a bien posé les 2 LandPieces, on renvoie true
        return true;

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

        // Le domain1 est maintenant la fusion des 2
    }

}