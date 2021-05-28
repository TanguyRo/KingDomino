package DomiNations;

public class King {
    private Player player;
    private Color color;
    private Domino currentDomino;


    public King(Player player){
        this.player = player;
        this.color = player.getColor();
        this.currentDomino = null;
    }

    public void setPlayer(Player player){
        this.player = player;
        this.color = player.getColor();
    }

    public Player getPlayer(){
        return player;
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

    public Domino getCurrentDomino() {
        return currentDomino;
    }

    public void setCurrentDomino(Domino currentDomino) {
        this.currentDomino = currentDomino;
    }
}