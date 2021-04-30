package DomiNations;

public class Position {
	private final int column;
	private final int row;


	public Position(int column, int row) {
		this.column = column;
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
}
