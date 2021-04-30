package DomiNations;

public class Kingdom {
	private int size;
	private final Player player;


	public Kingdom(int size, Player player) {
		this.size = size;
		this.player = player;
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
}
