package DomiNations;

public class Domino {
    private final LandPiece landPiece1;
    private final LandPiece landPiece2;
    private final int number;
    private King king ;


    public Domino(LandPiece landPiece1, LandPiece landPiece2, int number) {
        this.landPiece1 = landPiece1;
        this.landPiece2 = landPiece2;
        this.number = number;
        this.king = null;
    }

    public void setKing(King king) {
        this.king = king;
    }

    public LandPiece getLandPiece(int number) {
        if (number == 1){
            return landPiece1;
        }
        else if (number == 2){
            return landPiece2;
        }
        else {
            return null;
        }
    }

    public int getNumber() {
        return number;
    }

    public King getKing() {
        return king;
    }
}