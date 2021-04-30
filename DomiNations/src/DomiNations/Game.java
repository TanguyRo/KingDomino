package DomiNations;

import java.util.Scanner;

public class Game {
	private Player[] players;
	private Player currentPlayer;
	private Domino[] drawPile;

	public void play() {
		createPlayers();
		initialiseBoards();
	}
	
	public void createPlayers() {
		int color = 0;

		Scanner scanner = new Scanner(System.in);
		System.out.println("Saisir nombre de joueur : ");
		int nbr_joueur = scanner.nextInt();
		for(int i = 1; i<nbr_joueur; i++) {
			System.out.println("Saisir nom joueur " + i + " : ");
			String name = scanner.nextLine();

			do {
				System.out.println("Saisir couleur joueur " + i + " (\"rouge\", \"bleu\", \"vert\", \"jaune\") : ");
				String color_input = scanner.nextLine();

				switch (color_input) {
					case "rouge":
						color = 1;
						break;
					case "bleu":
						color = 2;
						break;
					case "vert":
						color = 3;
						break;
					case "jaune":
						color = 4;
						break;
					default:
						color = 0;
						System.out.println("Saisir une couleur valide : ");
						break;
				}

			} while (color == 0);

			players[i] = new Player(name, color);
		}

		currentPlayer = players[0];
	}
	
	public void initialiseBoards() {
		
	}

	public void initialiseDominos(){}

    public void initialiseDrawPile(){}
}
