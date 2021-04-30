package DomiNations;

public class Domain {
	private final Kingdom kingdom = new Kingdom();
	private final String type;
	private int totalCrownNumber;
	
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
