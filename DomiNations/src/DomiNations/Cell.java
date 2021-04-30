package DomiNations;

public class Cell {
    private final Position position;
    private boolean isEmpty;
    private LandPiece currentLandPiece;

    public Cell(Position position, boolean isEmpty, LandPiece currentLandPiece) {
        this.position = position;
        this.isEmpty = isEmpty;
        this.currentLandPiece = currentLandPiece;
    }

    public void setState(boolean empty) {
        isEmpty = empty;
    }

    public void setCurrentLandPiece(LandPiece currentLandPiece) {
        this.currentLandPiece = currentLandPiece;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public LandPiece getCurrentLandPiece() {
        return currentLandPiece;
    }
}
