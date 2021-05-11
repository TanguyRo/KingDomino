package DomiNations;

public class Bench {
    private Domino[] firstLane;
    private Domino[] secondLane;


    public Bench(Domino[] firstLane, Domino[] secondLane) {
        this.firstLane = firstLane;
        this.secondLane = secondLane;
    }

    public void setFirstLane(Domino[] firstLane){
        this.firstLane = firstLane;
    }

    public void setSecondLane(Domino[] firstLane){
        this.secondLane = secondLane;
    }

    public Domino[] getFirstLane(){
        return firstLane;
    }

    public Domino[] getSecondLane(){
        return secondLane;
    }
}