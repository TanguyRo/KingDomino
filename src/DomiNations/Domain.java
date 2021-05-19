package DomiNations;

import java.util.ArrayList;

public class Domain {
    private final Kingdom kingdom;
    private final String type;
    private int totalCrownNumber;
    private ArrayList<LandPiece> landPieces;


    public Domain(Kingdom kingdom, String type, int totalCrownNumber, LandPiece firstLandPiece) {
        this.kingdom = kingdom;
        this.type = type;
        this.totalCrownNumber = totalCrownNumber;
        this.landPieces = new ArrayList<>();
        this.landPieces.add(firstLandPiece);
    }

    public void setCrownNumber(int totalCrownNumber) {
        this.totalCrownNumber = totalCrownNumber;
    }

    public void addCrowns(int crownsToAdd) {
        this.totalCrownNumber += crownsToAdd;
    }

    public int getCrownNumber() {
        return totalCrownNumber;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public String getType() {
        return type;
    }

    public void setLandpieces(ArrayList<LandPiece> landpieces) {
        this.landPieces = landpieces;
    }

    public ArrayList<LandPiece> getLandpieces() {
        return landPieces;
    }

}