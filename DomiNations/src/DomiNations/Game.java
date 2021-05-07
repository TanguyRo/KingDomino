package DomiNations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class Game {
	private Player[] players;
	private Player currentPlayer;
	private Kingdom[] kingdoms;
	private King[] kings;
	private LinkedList<Domino> drawPile;
	private int nbPlayers;
	private boolean piocheVide;
	
	public void play() {
		createPlayers();
		initialiseKingdoms();
		try {
			initialiseDrawPile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//Tour par Tour
		
		//(dans init drawpile ?)
		//retire 24 dominos alÈatoire si deux joueurs (24 restant dans la pioche)
		//retire 12 dominos (36 restant dans la pioche) si 3 joueurs
		//4 joueur : tout les dominos seront utilisÈs
			
		//Premier Tour
		//Piocher autant de dominos qu'il y a de rois en jeu
		//Disposer ces dominos face numÈrotÈe visible et rangÈs par ordre croissant
		//retourner ces dominos face paysage
		do {
			//RÈcupÈrer le domino sur lequel son roi se trouvait
			//Placer ce domino dans le royaume en respectant les rËgles de connexion.
			//SÈlectionner un domino de la ligne suivante en y plaÁant son roi.
		}
		while(!piocheVide)
		//Tous les dominos ont ÈtÈ piochÈs et posÈs
		//calcul des points par joueur
		// n = nbCases * nbCouronnes
		//nbCases nombre de cases du domaine
		//nbCouronnes nombre de couronnes sur le domaine
		
		//comparaison point entre joueur
		//si ÈgalitÈ -> plus grand domaine gagne
		//si ÈgalitÈ de domaine -> plus de couronnes gagne
		//sinon tous gagnant
	}
	
	public void createPlayers() {
		int color = 0;

		Scanner scanner = new Scanner(System.in);
		System.out.println("Saisir nombre de joueur : ");
		nbPlayers = scanner.nextInt();
		scanner.nextLine();

		Player[] players = new Player[nbPlayers];
		King[] kings = new King[4]

		// Noms et couleurs des joueurs :
		for(int i = 1; i<=nbPlayers; i++) {
			System.out.println("Saisir nom joueur " + i + " : ");
			String name = scanner.nextLine();

			do {
				System.out.println("Saisir couleur joueur " + i + " (\"rose\", \"jaune\", \"vert\", \"bleu\") : ");
				String color_input = scanner.nextLine();

				switch (color_input) {
					case "rose":
						color = 1;
						break;
					case "jaune":
						color = 2;
						break;
					case "vert":
						color = 3;
						break;
					case "bleu":
						color = 4;
						break;
					default:
						color = 0;
						System.out.println("Saisir une couleur valide : ");
						break;
				}

			} while (color == 0);

			players[i] = new Player(name, color);
			
			// Cr√©ation des rois en fonction du nombre de joueurs
			for(int i = 1; i<=nbPlayers; i++) {
				if(nbPLayers > 2) {
					king[i].setPlayer(players[i]);
				}else {

				}
				
			}
		}

		currentPlayer = players[0];
		System.out.println("Les joueurs ont bien √©t√© cr√©√©s.");
	}
	
	public void initialiseKingdoms() {
		Kingdom[] kingdoms = new Kingdom[nbPlayers];

		for(int i = 0; i<nbPlayers; i++) {
			Kingdom kingdom = new Kingdom(1,players[i]);	// taille 1 car uniquement le ch√¢teau au d√©part
		}
	}

	public void initialiseDrawPile() throws FileNotFoundException {
		// scanner va lire le contenu du fichier .csv
		Scanner scanner = new Scanner(new File("dominos.csv"));
		scanner.nextLine();		// On saute la ligne d'en-t√™te

		// stringBuilder va stocker le contenu du fichier
		StringBuilder stringBuilder = new StringBuilder();
		while (scanner.hasNextLine()) {
			stringBuilder.append(scanner.nextLine())
					.append("\n");
		}
		scanner.close();

		// Les deux lignes suivantes vont ensuite produire un String[] contenant
		// les donneees du fichier CSV
		String data = stringBuilder.toString();
		String[] dominos = data.split("\n");

		// Cr√©ation de la pile de dominos
		LinkedList<Domino> drawPile = new LinkedList<>();

		// Cr√©ation de tous les dominos et placement dans l'ordre dans la pile drawPile
		for (int i=0; i < dominos.length; i++) {
			String[] infosDomino = dominos[i].split(",");
			LandPiece landPiece1 = new LandPiece(infosDomino[1],Integer.valueOf(infosDomino[0]));
			LandPiece landPiece2 = new LandPiece(infosDomino[3],Integer.valueOf(infosDomino[2]));
		    Domino domino = new Domino(landPiece1, landPiece2, Integer.valueOf(infosDomino[4]));
			drawPile.add(domino);
		}

		// M√©lange des dominos
		Collections.shuffle(drawPile);

		System.out.println("La pioche a √©t√© m√©lang√©e.");
	}

}
