package DomiNations;

public class LandPiece {
    private Position position;
    private final String type;
    private final int crownNumber;

    public LandPiece(String type, int crownNumber) {
        this.position = null;
        this.type = type;
        this.crownNumber = crownNumber;
    }

    public void setCurrentPosition(Position position) {
        this.position = position;
    }

    public Position getCurrentPosition() {
        return position;
    }

    public String getType() {
        return type;
    }

    public int getCrownNumber() {
        return crownNumber;
    }
}
