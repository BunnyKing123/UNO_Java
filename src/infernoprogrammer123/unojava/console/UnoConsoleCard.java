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

		if (number == UnoNumberConstants.ONE) {
			cardNum = " One";
		} else if (number == UnoNumberConstants.TWO) {
			cardNum = " Two";
		} else if (number == UnoNumberConstants.THREE) {
		    cardNum = " Three";
		} else if (number == UnoNumberConstants.FOUR) {
		    cardNum = " Four";
		} else if (number == UnoNumberConstants.FIVE) {
		    cardNum = " Five";
		} else if (number == UnoNumberConstants.SIX) {
		    cardNum = " Six";
		} else if (number == UnoNumberConstants.SEVEN) {
		    cardNum = " Seven";
		} else if (number == UnoNumberConstants.EIGHT) {
		    cardNum = " Eight";
		} else if (number == UnoNumberConstants.NINE) {
		    cardNum = " Nine";
		} else if (number == UnoNumberConstants.ZERO) {
		    cardNum = " Zero";
		} else if (number == UnoNumberConstants.PLUS_FOUR) {
		    cardNum = " Plus Four";
		} else if (number == UnoNumberConstants.PLUS_TWO) {
		    cardNum = " Plus Two";
		} else if (number == UnoNumberConstants.REVERSE) {
			cardNum = " Reverse";
		} else if (number == UnoNumberConstants.SKIP) {
			cardNum = " Skip";
		} else {
			cardNum = " Wild Card";
		}

		return cardColor + cardNum;

	}

}
