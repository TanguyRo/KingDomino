package DomiNations;

import javafx.scene.image.Image;
import java.util.HashMap;

public class LandPiece {
    private Cell cell;
    private final String type;
    private final int crownNumber;
    private Image image;
    private Domain parentDomain;
    private static final HashMap<String,String> typesMap = new HashMap<String,String>();    // static car commun à tous les objets LandPiece

    public LandPiece(String type, int crownNumber) {
        this.cell = null;
        this.type = type;
        this.crownNumber = crownNumber;
        this.parentDomain = null;
    }

    // Remplissage du dictionnaire pour les emojis correspondant à chaque type de domaine (pour le print)
    static {
        typesMap.put("Champs","\uD83C\uDF3E");
        typesMap.put("Chateau","\uD83C\uDFF0");
        typesMap.put("Foret","\uD83C\uDF32");
        typesMap.put("Mer","\uD83C\uDF0A");
        typesMap.put("Mine","\u26CF\uFE0F️");
        typesMap.put("Montagne","\u26F0\uFE0F");
        typesMap.put("Prairie","\uD83C\uDF3F");
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

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setParentDomain(Domain parentDomain) {
        this.parentDomain = parentDomain;
    }

    public Domain getParentDomain() {
        return parentDomain;
    }

    @Override
    public String toString(){
        String type = this.getType();
        int crownNumber = this.getCrownNumber();
        return (typesMap.get(type) + crownNumber);
    }
}