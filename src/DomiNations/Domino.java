package DomiNations;

public class Domino {
    private final LandPiece landPiece1;
    private final LandPiece landPiece2;
    private final int number;
    private King king ;
    private boolean isDiscarded;


    public Domino(LandPiece landPiece1, LandPiece landPiece2, int number) {
        this.landPiece1 = landPiece1;
        this.landPiece2 = landPiece2;
        this.number = number;
        this.king = null;
        this.isDiscarded = false;
    }

    public void setKing(King king) {
        this.king = king;
    }

    public void setDiscarded(boolean discarded) {
        isDiscarded = discarded;
    }

    public LandPiece getLandPiece1() {
        return landPiece1;
    }

    public LandPiece getLandPiece2() {
        return landPiece2;
    }

    public int getNumber() {
        return number;
    }

    public King getKing() {
        return king;
    }

    public boolean isDiscarded() {
        return isDiscarded;
    }
}