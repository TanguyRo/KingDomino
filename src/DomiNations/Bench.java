package DomiNations;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

public class Bench {
    private final int size;
    private Domino[] firstLane;
    private Domino[] secondLane;


    public Bench(int size) {
        this.size = size;
        this.firstLane = new Domino[size];
        this.secondLane = new Domino[size];
    }

    public void setFirstLane(Domino[] lane){
        this.firstLane = lane;
    }

    public void setSecondLane(Domino[] lane){
        this.secondLane = lane;
    }

    public Domino[] getLane(int lane){
        return (lane==1 ? firstLane : secondLane);
    }

    // Fonction de base uniquement appelée par les deux autres qui sont elles utilisées dans Game
    private Domino[] drawDominosAsArray(LinkedList<Domino> drawPile){
        Domino[] drawnDominos = new Domino[size];

        // On pioche autant de dominos qu'il y a de rois en jeu
        for (int i=0; i<size; i++){
            Domino drawnDomino = drawPile.getFirst();
            drawPile.remove();
            drawnDominos[i] = drawnDomino;
        }

        // Tri du tableau en fonction des number de chaque domino
        Arrays.sort(drawnDominos, (Comparator<Domino>) (domino1, domino2) -> Integer.compare(domino1.getNumber(), domino2.getNumber()));

        return drawnDominos;
    }

    // Fonction appelant drawDominosAsArray pour la lane de gauche, uniquement utilisée au premier tour
    public void drawFirstLane(LinkedList<Domino> drawPile){
        firstLane = drawDominosAsArray(drawPile);
    }

    // Fonction appelant drawDominosAsArray pour la lane de droite et décalant les dominos
    public void drawDominos(LinkedList<Domino> drawPile) {
        firstLane = secondLane;                         // Les dominos de la lane de gauche ont tous été posés, on peut décaler la lane de droite
        secondLane = drawDominosAsArray(drawPile);      // Et on pioche des nouveaux dominos à droite
    }

    public int[] getDominosValues(int lane){
        Domino[] dominosLane = (lane==1 ? firstLane : secondLane);
        int[] dominosValues = new int[size];

        for (int i=0; i<size; i++){
            dominosValues[i] = dominosLane[i].getNumber();
        }

        return dominosValues;
    }

    public void print(){
        System.out.println("Voilà le banc :");
        for (int i=0; i<size; i++){
            Domino[] dominos = {firstLane[i],secondLane[i]};
            StringBuilder[] parts = new StringBuilder[2];
            for (int j=0; j<2; j++){
                if (dominos[j]==null){
                    parts[j] = new StringBuilder("              ");
                }
                else {
                    Domino domino = dominos[j];
                    StringBuilder sb = new StringBuilder();
                    sb.append(domino.getNumber());
                    if (sb.length() == 1) sb.append(" "); // Toujours 2 caractères pour le chiffre
                    sb.append(" : ");
                    sb.append(domino.getLandPiece(1));
                    sb.append(" ");
                    sb.append(domino.getLandPiece(2));
                    sb.append(" ");
                    King king = domino.getKing();
                    if (king!=null){
                        sb.append(king.getColorEmoji());
                    }
                    else {
                        sb.append("\u26AA");
                    }
                    parts[j] = sb;
                }
            }
            System.out.println(parts[0] + " ｜ " + parts[1]);
        }
        System.out.println();
    }
}