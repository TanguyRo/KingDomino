package DomiNations;

public class King {
    private Player player;
    private int color;


    public King(Player player){
        this.player = player;
        this.color = player.getColor();
    }

    public void setPlayer(Player player){
        this.player = player;
        this.color = player.getColor();
    }

    public Player getPlayer(){
        return player;
    }

    public int getColor() {
        return color;
    }
}