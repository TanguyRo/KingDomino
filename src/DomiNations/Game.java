package DomiNations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
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
	private LinkedList<Domino> piocheTour;
	private LinkedList<java.lang.Integer> piocheTourValue;
	private LinkedList<java.lang.Integer> piocheTourTri;


	public void play() {
		createPlayers();
		initialiseKingdoms();
		try {
			initialiseDrawPile(nbPlayers);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		LinkedList<Domino> piocheTour = new LinkedList<>();
		LinkedList<java.lang.Integer> piocheTourValue = new LinkedList<>();
		LinkedList<Domino> piocheTourTri = new LinkedList<>();
		//Dictionary<Domino, int> piocheTour = new Hashtable<Domino, int>();
		do {
			//Disposer ces dominos face num�rot�e visible et rang�s par ordre croissant (swing)
			//retourner ces dominos face paysage (swing)

			Collections.shuffle(kings); //mélanger les rois
			for(int i; i <= kings.length; i++){
				/**
				 * Pioche un domino dans drawPile
				 * Fait correspondre le domino à un roi
				 * (déjà mélangé donc au hasard)
				 * **/
				drawPile[i].setKing(kings[i]);
				//Ajoute le domino à une pile de domino en jeu

				//piocheTour.put(drawPile[i], drawPile[i].getNumber());
				piocheTour.add(drawPile[i]);
				piocheTourValue.add(drawPile[i].getNumber);

				drawPile.remove();
				//retire le domino pioché de la pile
			}
			//Tri des dominos dans l'ordre croissant selon number
			//sortValue(piocheTour);
			Arrays.sort(piocheTourValue);

			for(int i; i< kings.length; i++){
				for(int j; j< kings.length; j++){
					if(piocheTourValue[j] == piocheTour[i].getNumber()){
						piocheTourTri.add(piocheTour[i]);
					}
				}
			}

			//Joueurs jouent dans l'ordre croissant (numéro domino)
			for(int i; i <= kings.length; i++) {
				piocheTourTri[i].getKing().getPlayer();
				//Joue
			}

			//R�cup�rer le domino sur lequel son roi se trouvait
			//Placer ce domino dans le royaume en respectant les r�gles de connexion.
			//S�lectionner un domino de la ligne suivante en y pla�ant son roi.
		}
		while(!piocheVide);
		//Tous les dominos ont �t� pioch�s et pos�s
		//calcul des points par joueur
		// n = nbCases * nbCouronnes
		//nbCases nombre de cases du domaine
		//nbCouronnes nombre de couronnes sur le domaine

		//comparaison point entre joueur
		//si �galit� -> plus grand domaine gagne
		//si �galit� de domaine -> plus de couronnes gagne
		//sinon tous gagnant
	}

	//public static void sortValue(int[] liste, )
	/**
	public static void sortValue(Hashtable<Domino, int> test){
		ArrayList<Map.Entry<Domino, int>> l = new ArrayList(test.entrySet());
		Collections.sort(l, new Comparator<Map.Entry<Domino, int>>()){
			public int compare(Map.Entry<Domino, int> o1, Map.Entry<Domino, int> o2){
				return o1.getValue().compareto(o2.getValue());
			});
		}
	}
	**/

	public void createPlayers() {
		int color = 0;

		Scanner scanner = new Scanner(System.in);
		do {
			System.out.println("Saisir un nombre de joueur (minimum 2) : ");
			nbPlayers = scanner.nextInt();
			scanner.nextLine();

		}while(nbPlayers<2);

		Player[] players = new Player[nbPlayers];
		King[] kings = new King[4];

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

			players[i-1] = new Player(name, color);

			// Création des rois en fonction du nombre de joueurs
			if (nbPlayers==3 || nbPlayers==4){
				kings[i-1] = new King(players[i-1]);
			}
			else if (nbPlayers == 2){
				if (i==1){
					kings[0] = new King(players[i-1]);
					kings[1] = new King(players[i-1]);
				}
				else {
					kings[2] = new King(players[i-1]);
					kings[3] = new King(players[i-1]);
				}
			}
		}

		currentPlayer = players[0];
		System.out.println("Les joueurs ont bien été créés.");
	}

	public void initialiseKingdoms() {
		Kingdom[] kingdoms = new Kingdom[nbPlayers];

		for(int i = 0; i<nbPlayers; i++) {
			kingdoms[i] = new Kingdom(1,players[i]);	// taille 1 car uniquement le château au départ

			Cell[][] cells = new Cell[5][5];

			for (int x=0; x<5; x++){
				for (int y=0; y<5; y++){
					cells[x][y] = new Cell(new Position(x,y),true,null);
				}
			}

			// Case du chateau
			cells[2][2].setState(false);
			cells[2][2].setCurrentLandPiece(new LandPiece("chateau",0));

			kingdoms[i].setCells(cells);

		}
	}

	public void initialiseDrawPile(int nbPlayers) throws FileNotFoundException {
		// scanner va lire le contenu du fichier .csv
		Scanner scanner = new Scanner(new File("dominos.csv"));
		scanner.nextLine();		// On saute la ligne d'en-tête

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

		// Création de la pile de dominos
		LinkedList<Domino> drawPile = new LinkedList<>();

		// Création de tous les dominos et placement dans l'ordre dans la pile drawPile
		for (int i=0; i < dominos.length; i++) {
			String[] infosDomino = dominos[i].split(",");
			LandPiece landPiece1 = new LandPiece(infosDomino[1],Integer.valueOf(infosDomino[0]));
			LandPiece landPiece2 = new LandPiece(infosDomino[3],Integer.valueOf(infosDomino[2]));
		    Domino domino = new Domino(landPiece1, landPiece2, Integer.valueOf(infosDomino[4]));
			drawPile.add(domino);
		}

		// Mélange des dominos
		Collections.shuffle(drawPile);

		switch(nbPlayers) {
			case 2:
				for(int i=1; i<24; i++) { drawPile.remove(); }
				break;
			case 3:
				for(int i=1; i<12; i++) { drawPile.remove(); }
				break;
		}


		System.out.println("La pioche a été mélangée.");
	}

}
