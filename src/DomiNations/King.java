package DomiNations;

import javafx.scene.image.Image;

public class King {
    private final Player player;
    private final Color color;
    private Image image;
    private Domino currentDomino;


    public King(Player player){
        this.player = player;
        this.color = player.getColor();
        this.image = null;
        this.currentDomino = null;
    }

    public Player getPlayer(){
        return player;
    }

    public String getColorEmoji() {
        return color.getEmoji();
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Domino getCurrentDomino() {
        return currentDomino;
    }

    public void setCurrentDomino(Domino currentDomino) {
        this.currentDomino = currentDomino;
    }


}