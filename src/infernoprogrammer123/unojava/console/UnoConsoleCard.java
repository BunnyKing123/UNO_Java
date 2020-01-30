package infernoprogrammer123.unojava.console;

import infernoprogrammer123.unojava.constants.*;

public class UnoConsoleCard {
	

	private UnoColorConstants color; // Represents color of the card
	private UnoNumberConstants number; // Represents number/type of card

	/**
	 * Uno Console Card class is used to represent an Uno Card
	 *
	 * @param color - Represents the color of the card, must be an UnoColorConstant
	 * @param number - Represents the number of the card, must be an UnoNumberConstant
	 */
	public UnoConsoleCard (UnoColorConstants color, UnoNumberConstants number) {
		this.color = color;
		this.number = number;
	}

	// Encapsulation stuff
	/**
	 * Returns the Uno Color Constant of the card
	 *
	 * @return UnoColorConstant
	 */
	public UnoColorConstants getColor() { return color; }
	
	public void setColor(UnoColorConstants color) {
		this.color = color;
	}

	/**
	 * Returns the Uno Number Constant of the card
	 *
	 * @return UnoNumberConstant
	 */
	public UnoNumberConstants getNumber() { return number; }
	
	@Override
	public String toString() {
		
		String cardColor = "DEFAULT";
		String cardNum = "DEFAULT";
		
		/*
		if (color == UnoColorConstants.RED) {
			cardColor = "Red";
		} else if (color == UnoColorConstants.BLUE) {
			cardColor = "Blue";
		} else if (color == UnoColorConstants.GREEN) {
			cardColor = "Green";
		} else if (color == UnoColorConstants.YELLOW) {
			cardColor = "Yellow";
		} else {
			cardColor = "Uncolored";
		}
		*/

		return color + " " + number;

	}

}
