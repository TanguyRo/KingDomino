package DomiNations;

public class LandPiece {
    private Cell cell;
    private final String type;
    private final int crownNumber;

    public LandPiece(String type, int crownNumber) {
        this.cell = null;
        this.type = type;
        this.crownNumber = crownNumber;
    }

    public void setCurrentPosition(Cell cell) {
        this.cell = cell;
    }

    public Cell getCurrentCell() {
        return cell;
    }

    public String getType() {
        return type;
    }

    public int getCrownNumber() {
        return crownNumber;
    }
}
