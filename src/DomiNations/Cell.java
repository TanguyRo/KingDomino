package DomiNations;

public class Cell {
    private final int[] position;
    private boolean isEmpty;
    private LandPiece currentLandPiece;


    public Cell(int[] position, boolean isEmpty, LandPiece currentLandPiece) {
        this.position = position;
        this.isEmpty = isEmpty;
        this.currentLandPiece = currentLandPiece;
    }

    public void setState(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public void setCurrentLandPiece(LandPiece currentLandPiece) {
        this.currentLandPiece = currentLandPiece;

    }

    public int[] getPosition() {
        return position;
    }

    public boolean getState() {
        return isEmpty;

    }

    public LandPiece getCurrentLandPiece() {
        return currentLandPiece;
    }
}