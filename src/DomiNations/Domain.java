package DomiNations;

import java.util.ArrayList;

public class Domain {
    private final String type;
    private int totalCrownNumber;
    private final ArrayList<LandPiece> landPieces;


    public Domain(String type, int totalCrownNumber, LandPiece firstLandPiece) {
        this.type = type;
        this.totalCrownNumber = totalCrownNumber;
        this.landPieces = new ArrayList<>();
        this.landPieces.add(firstLandPiece);
    }

    public void addCrowns(int crownsToAdd) {
        this.totalCrownNumber += crownsToAdd;
    }

    public int getCrownNumber() {
        return totalCrownNumber;
    }

    public String getType() {
        return type;
    }

    public ArrayList<LandPiece> getLandPieces() {
        return landPieces;
    }

}