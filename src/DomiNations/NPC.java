package DomiNations;

// NPC -> Non-playable Character

import java.util.*;

public class NPC extends Player {
    private static final ArrayList<String> namesList = new ArrayList<>(Arrays.asList("Léa", "Thomas", "Manon", "Lucas", "Camille", "Théo", "Chloé", "Hugo", "Emma", "Maxime", "Marie", "Nicolas", "Océane", "Quentin", "Sarah", "Alexandre", "Laura", "Antoine", "Mathilde", "Clément", "Julie", "Alexis", "Marine", "Valentin", "Pauline", "Julien", "Lucie", "Romain", "Anaïs", "Florian", "Inès", "Louis", "Clara", "Benjamin", "Justine", "Paul", "Lisa", "Pierre", "Maeva", "Baptiste", "Juliette", "Enzo", "Émilie", "Kevin", "Morgane", "Adrien", "Charlotte", "Tom", "Eva", "Guillaume"));
    private static final Random random = new Random();

    // Le nom est directement atttribué au Player et retiré de la liste
    // @Override (paramètres différents donc pas d'Override)
    public void chooseName(){
        int randomIndex = random.nextInt(namesList.size());
        String randomName = namesList.get(randomIndex);
        namesList.remove(randomIndex);                    // Pour ne pas prendre 2 fois le même prénom
        name = randomName;
    }

    // La couleur est directement attribuée au Player et retirée du HashMap
    // @Override (paramètres différents donc pas d'Override)
    public void chooseColor(HashMap<String, Integer> colorsToSelect) {
        ArrayList<String> possibleColors = new ArrayList<>(colorsToSelect.keySet());    // On choisit une couleur au hasard dans la liste des restantes
        String chosenColor = possibleColors.get(random.nextInt(possibleColors.size()));
        color = new Color(colorsToSelect.get(chosenColor));     // On cherche le nombre correspondant à la couleur (différent de l'indice)
        colorsToSelect.remove(chosenColor);
    }

    // Le numéro du domino est renvoyé, mais le domino n'est pas supprimé du HashMap
    @Override
    public int chooseDomino(HashMap<Integer, Domino> dominosToSelect) {
        ArrayList<Integer> possibleDominos = new ArrayList<>(dominosToSelect.keySet());    // On choisit une couleur au hasard dans la liste des restantes
        Integer chosenDominoNumber = possibleDominos.get(random.nextInt(possibleDominos.size()));
        System.out.println(this.getName() + " " + this.getColorEmoji() + " a choisi le domino " + chosenDominoNumber + ".");
        return chosenDominoNumber;
    }

}
