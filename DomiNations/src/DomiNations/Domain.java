package DomiNations;

public class Domain {
	private final Kingdom kingdom;
	private final String type;
	private int totalCrownNumber;


	public Domain(Kingdom kingdom, String type, int totalCrownNumber) {
		this.kingdom = kingdom;
		this.type = type;
		this.totalCrownNumber = totalCrownNumber;
	}

	public void setCrownNumber(int totalCrownNumber) {
		this.totalCrownNumber = totalCrownNumber;
	}

	public int getCrownNumber() {
		return totalCrownNumber;
	}

	public Kingdom getKingdom() {
		return kingdom;
	}

	public String getType() {
		return type;
	}
}
