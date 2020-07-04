package io.github.bunnyking123.unojava.constants;

public enum UnoColorConstants {
	
	RED("Red"), BLUE("Blue"), GREEN("Green"), YELLOW("Yellow"), WILD("Uncolored");

	private String name;
	
	private UnoColorConstants(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
