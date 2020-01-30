package infernoprogrammer123.unojava.constants;

public enum UnoNumberConstants {
	
	ZERO("Zero"), ONE("One"), TWO("Two"), 
	THREE("Three"), FOUR("Four"), FIVE("Five"), 
	SIX("Six"), SEVEN("Seven"), EIGHT("Eight"), 
	NINE("Nine"), PLUS_FOUR("+4"), PLUS_TWO("+2"), 
	REVERSE("Reverse"), SKIP("Skip"), WILD("Wild");
	
	private String name;
	
	private UnoNumberConstants(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}

