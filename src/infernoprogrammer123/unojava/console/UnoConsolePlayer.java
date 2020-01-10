package infernoprogrammer123.unojava.console;

import infernoprogrammer123.unojava.constants.*;
import java.util.ArrayList;

public class UnoConsolePlayer {
	
	private ArrayList<UnoConsoleCard> hand = new ArrayList<UnoConsoleCard>();
	private UnoPlayerConstants playerType;
	private String name;
	
	public UnoConsolePlayer(UnoPlayerConstants playerType, String name) {
		this.playerType = playerType;
		this.name = name;
	}
	
	public UnoPlayerConstants getPlayerType() {
		return playerType;
	}
	
	public UnoConsoleCard pcMove() {
		// Get the top card and discard pile
		UnoConsoleCard topCard = UnoConsole.mainGame.getTopCard();
		ArrayList<UnoConsoleCard> discardPile = UnoConsole.mainGame.getDiscardPile();
		
		// Create an arraylist of playable cards
		ArrayList<UnoConsoleCard> playable = new ArrayList<UnoConsoleCard>();
		
		// For each card in the hand, check if the card is playable and add to the list of playable cards
		for (UnoConsoleCard card:hand) {
			if (cardValid(card, topCard)) {
				playable.add(card);
			}
		}
		
		if (playable.size() == 0) {
			// If there are no playable cards, return nothing
			System.out.println("PC: Looks like I'll have to draw");
			return null;
		} else {
			// Get a random item from the list
			int rand = (int) (Math.random() * (playable.size() - 1));
			UnoConsoleCard card = playable.get(rand);
			hand.remove(card);
			System.out.println("I would like to play " + card);
			System.out.println("I now have " + hand.size() + " cards left");
			return card;
		}
	}
	
	public UnoConsoleCard playerMove() {
		// Get the top card and discard pile
		UnoConsoleCard topCard = UnoConsole.mainGame.getTopCard();
		ArrayList<UnoConsoleCard> discardPile = UnoConsole.mainGame.getDiscardPile();
		
		// Create an arraylist of playable cards
		ArrayList<UnoConsoleCard> playable = new ArrayList<UnoConsoleCard>();
		
		// For each card in the hand, check if the card is playable and add to the list of playable cards
		for (UnoConsoleCard card:hand) {
			if (cardValid(card, topCard)) {
				playable.add(card);
			}
		}
		
		if (playable.size() == 0) {
			System.out.println("You have no playable cards, now drawing a card");
			return null;
		} else {
			// Boolean for when the input is valid
			boolean validInput = false;
			
			// Prepare a variable to store the player's choice:
			UnoConsoleCard choice = null;
			
			// Tell user their list of cards will be displayed
			System.out.println("Here is your hand along with the number it is associated with:");
			
			// Print the list of cards
			for (int i = 0; i < hand.size(); i++) {
				System.out.println(i + " - " + hand.get(i));
			}
			
			// Ask user to pick the number
			System.out.println("Choose one of the numbers above");
			
			// While input isn't valid
			while (validInput == false) {
				// Create variable for user input
				int userInput;
				
				// Using try block to make sure user inputs an integer
				try {
					// Prompt user for input
					System.out.print("Enter a number: ");
					userInput = Integer.parseInt(UnoConsoleGame.userInput());
					
					if (userInput < 0 || userInput > hand.size() - 1) {
						System.out.println("Unfortunately your number was out of range, try again!");
					} else if (!cardValid(hand.get(userInput), topCard)) {
						System.out.println("Unfortunately that card is invalid!");
					} else {
						choice = hand.get(userInput);
						hand.remove(userInput);
						validInput = true;
					}
					
				} catch (NumberFormatException nfe) {
					System.out.println("Your input was not numeric, try again");
				}
				
			}
			
			return choice;
		}
	}
	
	public ArrayList<UnoConsoleCard> getHand() { return hand; }
	
	/**
	 * Static function that is used to check if a card being played is valid, must fit at least one of the following rules:
	 * 1. The two numbers are the same
	 * 2. The two colors are the same
	 * 3. The card being played is a wild card
	 *
	 * @param cardPlayed - Represents card being played
	 * @param playPileCard - Represents card on the play pile
	 * @return boolean - If the card being played can be played return true, else return false
	 */
	public boolean cardValid(UnoConsoleCard cardPlayed, UnoConsoleCard playPileCard) {
		// Get the color and number of the card played and the color and the number of the card on top of the play pile
		UnoColorConstants color = cardPlayed.getColor();
		UnoNumberConstants number = cardPlayed.getNumber();
		UnoColorConstants pileColor = playPileCard.getColor();
		UnoNumberConstants pileNumber = playPileCard.getNumber();

		// If the numbers are the same, or the colors are the same, or one of the card's colors is a wild card
		if (color == pileColor || number == pileNumber || color == UnoColorConstants.WILD) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
