package DomiNations;

public class LandPiece {
    private Cell cell;
    private final String type;
    private final int crownNumber;
    private Domain parentDomain;

    public LandPiece(String type, int crownNumber) {
        this.cell = null;
        this.type = type;
        this.crownNumber = crownNumber;
        this.parentDomain = null;
    }

    public void setCurrentCell(Cell cell) {
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

    public void setParentDomain(Domain parentDomain) {
        this.parentDomain = parentDomain;
    }

    public Domain getParentDomain() {
        return parentDomain;
    }
}