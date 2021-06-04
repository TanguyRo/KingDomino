package DomiNations;

public class King {
    private final Player player;
    private final Color color;
    private Domino currentDomino;


    public King(Player player){
        this.player = player;
        this.color = player.getColor();
        this.currentDomino = null;
    }

    public Player getPlayer(){
        return player;
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