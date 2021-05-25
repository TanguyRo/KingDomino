package DomiNations;

public class Player {
    private final String name;
    private final Color color;
    private Kingdom kingdom;


    public Player(String name, int colorNumber) {
        this.name = name;
        this.color = new Color(colorNumber);
        this.kingdom = null;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int getColorNumber() {
        return color.getNumber();
    }

    public String getColorName() {
        return color.getName();
    }

    public String getColorEmoji() {
        return color.getEmoji();
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }

    public int chooseDomino(int[] values) {
        // TODO Placer ici le scanner + la vérification que c'est bien un int et qu'il est pas pris
        return 0;
    }

    public int[] choosePosition() {
        // TODO Scanner + vérifications chiffres entre 1 et 4 pour l'orientation et entre 0 et 4 pour la upLeftPosition
        return new int[]{0, 1, 2};
    }
}