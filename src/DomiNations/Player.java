package DomiNations;

import java.util.HashMap;
import java.util.Scanner;

public class Player {
    protected String name;
    protected Color color;
    protected Kingdom kingdom;
    protected static final Scanner scanner = new Scanner(System.in);
    private static final HashMap<String,Boolean> choicesMap = new HashMap<String,Boolean>();
    private static final HashMap<String,String> directionsMap = new HashMap<String,String>();

    // Remplissage du dictionnaire pour les directions
    static {
        choicesMap.put("oui",true);
        choicesMap.put("non",false);
        directionsMap.put("haut","up");
        directionsMap.put("bas","down");
        directionsMap.put("gauche","left");
        directionsMap.put("droite","right");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setColor(int colorNumber) {
        this.color = new Color(colorNumber);
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

    // Le nom est directement atttribué au Player
    public void chooseName(int i){
        System.out.println("Saisir le nom du joueur " + i + " : ");
        String nameInput = scanner.nextLine();
        nameInput = nameInput.substring(0, 1).toUpperCase() + nameInput.substring(1).toLowerCase();   // On met la première lettre en majuscules et le reste en minsucules
        name = nameInput;
    }

    // La couleur est directement attribuée au Player et retirée du HashMap
    public void chooseColor(int i, HashMap<String, Integer> colorsToSelect) {
        int colorNumber = 0;
        do {
            String colorsList = String.join(", ", colorsToSelect.keySet()).replaceAll(", (?=[A-Za-z]*$)"," ou ");
            System.out.println("Saisir la couleur du joueur " + i + " parmi " + colorsList + " : ");
            String colorInput = scanner.nextLine();
            colorInput = colorInput.substring(0, 1).toUpperCase() + colorInput.substring(1).toLowerCase();   // On met la première lettre en majuscules et le reste en minsucules

            if (colorsToSelect.containsKey(colorInput)) {
                colorNumber = colorsToSelect.get(colorInput);
                colorsToSelect.remove(colorInput);
            }
        } while (colorNumber == 0);

        color = new Color(colorNumber);
    }

    // Le numéro du domino est renvoyé, mais le domino n'est pas supprimé du HashMap
    public int chooseDominoNumber(HashMap<Integer, Domino> dominosToSelect) {
        int domino = 0;
        // Premier essai
        try {
            System.out.println(name + " " + color.getEmoji() + " doit choisir un domino parmi " +
                    dominosToSelect.keySet() + " (Entrer la valeur du domino) :");
            domino = scanner.nextInt();
            scanner.nextLine();
            if (!(dominosToSelect.containsKey(domino))) {
                domino = 0;
            }
        }
        catch (Exception ex) {
            scanner.nextLine();
            domino = 0;
        }
        // Si ça n'a pas suffi on recommence jusqu'à avoir un domino correct
        while (domino == 0){
            try {
                System.out.println("Le domino ne peut être que " + dominosToSelect.keySet() + " : ");
                domino = scanner.nextInt();
                scanner.nextLine();
                if (!(dominosToSelect.containsKey(domino))) {
                    domino = 0;
                }
            }
            catch (Exception ex) {
                scanner.nextLine();
                domino = 0;
            }
        }
        return domino;
    }

    public int[] choosePosition() {
        // Choix de l'orientation
        int orientation = 0;
            // Premier essai
        try {
            System.out.println("Saisir l'orientation du domino (1, 2, 3 ou 4) : ");
            System.out.println("(1 correpond au domino horizontal tel quel, 2 correpond au domino pivoté de 90° dans le sens des aiguilles d'une montre, 3 au domino pivoté de 180° et 4 au domino pivoté de -90°)");
            orientation = scanner.nextInt();
            scanner.nextLine();
            if (orientation<1 || orientation>4) {
                orientation = 0;
            }
        }
        catch (Exception ex) {
            scanner.nextLine();
            orientation = 0;
        }
            // Si ça n'a pas suffi on recommence jusqu'à avoir un chiffre correct
        while (orientation == 0){
            try {
                System.out.println("L'orientation du domino ne peut être que 1, 2, 3 ou 4 : ");
                orientation = scanner.nextInt();
                scanner.nextLine();
                if (orientation<1 || orientation>4) {
                    orientation = 0;
                }
            }
            catch (Exception ex) {
                scanner.nextLine();
                orientation = 0;
            }
        }

        // Choix de la UpLeftPosition : coordonnée x
        int UpLeftPositionX = 0;
        // Premier essai
        try {
            System.out.println("Saisir la position du coin supérieur gauche de l'endroit ou vous souhaitez poser le domino.");
            System.out.println("Colonne : (1 tout à gauche, 5 tout à droite)");
            UpLeftPositionX = scanner.nextInt();
            scanner.nextLine();
            if (UpLeftPositionX<1 || UpLeftPositionX>5) {
                UpLeftPositionX = 0;
            }
        }
        catch (Exception ex) {
            scanner.nextLine();
            UpLeftPositionX = 0;
        }
        // Si ça n'a pas suffi on recommence jusqu'à avoir un chiffre correct
        while (UpLeftPositionX == 0){
            try {
                System.out.println("La colonne ne peut être que 1, 2, 3, 4 ou 5 : ");
                UpLeftPositionX = scanner.nextInt();
                scanner.nextLine();
                if (UpLeftPositionX<1 || UpLeftPositionX>5) {
                    UpLeftPositionX = 0;
                }
            }
            catch (Exception ex) {
                scanner.nextLine();
                UpLeftPositionX = 0;
            }
        }
        UpLeftPositionX--;      // On passe en coordonnées Java (0-4 au lieu de 1-5)

        // Choix de la UpLeftPosition : coordonnée y
        int UpLeftPositionY = 0;
        // Premier essai
        try {
            System.out.println("Ligne : (1 tout en haut, 5 tout en bas)");
            UpLeftPositionY = scanner.nextInt();
            scanner.nextLine();
            if (UpLeftPositionY<1 || UpLeftPositionY>5) {
                UpLeftPositionY = 0;
            }
        }
        catch (Exception ex) {
            scanner.nextLine();
            UpLeftPositionY = 0;
        }
        // Si ça n'a pas suffi on recommence jusqu'à avoir un chiffre correct
        while (UpLeftPositionY == 0){
            try {
                System.out.println("La colonne ne peut être que 1, 2, 3, 4 ou 5 : ");
                UpLeftPositionY = scanner.nextInt();
                scanner.nextLine();
                if (UpLeftPositionY<1 || UpLeftPositionY>5) {
                    UpLeftPositionY = 0;
                }
            }
            catch (Exception ex) {
                scanner.nextLine();
                UpLeftPositionY = 0;
            }

        }
        UpLeftPositionY--;      // On passe en coordonnées Java (0-4 au lieu de 1-5)

        return new int[]{orientation, UpLeftPositionX, UpLeftPositionY};
    }

    public boolean askKeepDomino() {
        String choice;
        while (true){
            System.out.println("Souhaitez vous poser ce domino ? (\"oui\" ou \"non\")");
            choice = scanner.nextLine();
            choice = choice.toLowerCase().strip();
            if (choicesMap.containsKey(choice)) {
                return choicesMap.get(choice);
            }
        }
    }

    public void askMove() {
        String direction = "";
        while (direction.equals("")){
            System.out.println("Souhaitez vous déplacer le royaume ? (\"non\" ou direction : haut, bas, gauche ou droite)");
            direction = scanner.nextLine();
            direction = direction.toLowerCase().strip();
            if (directionsMap.containsKey(direction)) {
                try{
                    this.kingdom.move(directionsMap.get(direction));
                    this.kingdom.print();
                } catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
            if (!direction.equals("non")) {
                direction = "";
            }
        }
    }
}