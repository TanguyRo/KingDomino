package DomiNations;

public class Player {
    private String name;
    private int color;
    private Kingdom kingdom;


    public Player(String name, int color) {
        this.name = name;
        this.color = color;
        this.kingdom = null;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setColor(int color){
        this.color = color;
    }

    public String getName(){
        return name;
    }

    public int getColor(){
        return color;
    }

    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }
}