package DomiNations;

import java.util.Arrays;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);

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

    public int[] choosePositionTests() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Orientation, UpLeftPositionX, UpLeftPositionY : ");
        boolean correctInput = false;
        int[] values = new int[3];
        while (!correctInput) {
            try {
                String input = scanner.nextLine();
                input = input.replaceAll("\n", "");  // On enlève le retour à la ligne final
                String[] inputs = input.split(", ");   // On sépare les 3 valeurs
                for (int i = 0; i < 3; i++) {                // Conversion en int
                    values[i] = Integer.parseInt(inputs[i]);
                }
                if (values[0] >= 1 && values[0] <= 4 && values[1] >= 1 && values[1] <= 5 && values[2] >= 1 && values[2] <= 5) {
                    correctInput = true;
                }
            } catch (Exception ex) {
                System.out.println("Réessayer : ");
            }
        }
        values[1]--;
        values[2]--;
        return values;
    }

}