package DomiNations;

import java.util.ArrayList;
import java.util.Iterator;

public class Kingdom {
	private int size;
	private final Player player;
	private Cell[][] cells;
	private ArrayList<Domain> domains;


	public Kingdom(int size, Player player) {
		this.size = size;
		this.player = player;
		this.cells = new Cell[5][5];
		this.domains = new ArrayList<>();
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public Player getPlayer() {
		return player;
	}

	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

	public void setDomains(ArrayList<Domain> domains) {
		this.domains = domains;
	}

	public ArrayList<Domain> getDomains() {
		return domains;
	}

	public void move(String direction){

		Cell[][] newCells = new Cell[5][5];
		for (int x=0; x<5; x++){

			for (int y=0; y<5; y++){

				switch (direction) {

					case "up":
						newCells[y][x] = cells[y+1][x];
						break;

					case "down":
						newCells[y][x] = cells[y-1][x];
						break;

					case "left":
						newCells[y][x] = cells[y][x+1];
						break;

					case "right":
						newCells[y][x] = cells[y][x-1];
						break;
				}


			}
		}

		cells = newCells;		// on remplace par le nouveau "board"
	}


}
