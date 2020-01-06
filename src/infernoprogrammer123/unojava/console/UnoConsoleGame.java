package infernoprogrammer123.unojava.console;

import java.util.ArrayList;

import infernoprogrammer123.unojava.constants.*;

public class UnoConsoleGame {
	
	// Represents the complete deck
	public ArrayList<UnoConsoleCard> deck = new ArrayList<UnoConsoleCard>();
	public ArrayList<UnoConsoleCard> discardPile = new ArrayList<UnoConsoleCard>();
	public ArrayList<UnoConsoleCard> player1Hand = new ArrayList<UnoConsoleCard>();
	public ArrayList<UnoConsoleCard> player2Hand = new ArrayList<UnoConsoleCard>();
	public UnoConsoleCard topCard;
	
	public void setup() {
		// Create and shuffle the deck
		createDeck(deck);
		deck = shuffleDeck(deck);
		
		// Create each player's hand
		dealCards(player1Hand, player2Hand);
		
		// Set the top card
		topCard = deck.get(0);
		deck.remove(0);
		
		// Begin the game
		play();
	}
	
	private void play() {
		// Variable storing the turn
		UnoTurnConstants turn;

		if (Math.round(Math.random()) == 1) {
			turn = UnoTurnConstants.HUMAN;
		} else {
			turn = UnoTurnConstants.COMPUTER;

		}
		
		while (true) {
			if (turn == UnoTurnConstants.HUMAN) {
				// When it's the human turn check if the human has any playable cards
				ArrayList<UnoConsoleCard> playable = new ArrayList<UnoConsoleCard>();
				
				for (UnoConsoleCard card:player1Hand) {
					if (cardValid(card, topCard)) {
						playable.add(card);
					}
				}
				
				// If there are no playable cards, draw a card
				if (playable.size() == 0) {
					drawCard(turn);
				} else {
					// If there are playable cards, ask for user input and display playable cards
					// Make sure that user's choice is within range of playable cards and user input is numeric
					// Then remove from hand and add to deck, check if card is special, if so do what is necessary based on that
				}
				
			} else {
				// When it's the computer's turn, check if the computer has any playable cards
			}
		}
	}
	
	/**
	 * Will create the deck of cards
	 * 
	 * @param deck - Represents the deck of cards
	 */
	private void createDeck(ArrayList<UnoConsoleCard> deck) {
		for (int i = 0; i < 4; i++) {
			// For each color of card besides wild
			UnoColorConstants[] colorConstants = {
					UnoColorConstants.RED,
					UnoColorConstants.YELLOW,
					UnoColorConstants.GREEN,
					UnoColorConstants.BLUE
			};
			UnoColorConstants colorConstant = colorConstants[i];
			
			// Add the zero card of that color
			deck.add(new UnoConsoleCard(colorConstant, UnoNumberConstants.ZERO));
			
			for (double j = 0; j < 24; j++) {
				// For each type besides zero
				UnoNumberConstants[] numberConstants = {
						UnoNumberConstants.ONE,
						UnoNumberConstants.TWO,
						UnoNumberConstants.THREE,
						UnoNumberConstants.FOUR,
						UnoNumberConstants.FIVE,
						UnoNumberConstants.SIX,
						UnoNumberConstants.SEVEN,
						UnoNumberConstants.EIGHT,
						UnoNumberConstants.NINE,
						UnoNumberConstants.PLUS_TWO,
						UnoNumberConstants.SKIP,
						UnoNumberConstants.REVERSE
				};
				
				// Add the number/type card of that color
				int index = (int) Math.floor(j / 2);
				deck.add(new UnoConsoleCard(colorConstant, numberConstants[index]));
			}
			
		}
		
		for (int k = 0; k < 8; k++) {
			UnoNumberConstants[] wildNums = {
					UnoNumberConstants.WILD,
					UnoNumberConstants.PLUS_FOUR
			};
			
			// Add the wild card
			int index = (int) Math.floor(k / 4);
			deck.add(new UnoConsoleCard(UnoColorConstants.WILD, wildNums[index]));
		}
	}
	
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
	
	/**
	 * Will shuffle the deck of cards
	 * 
	 * @param deck - Represents the deck
	 * @return A shuffled deck of "cards"
	 */
	private ArrayList<UnoConsoleCard> shuffleDeck(ArrayList<UnoConsoleCard> deck) {
		ArrayList<UnoConsoleCard> shuffledDeck = new ArrayList<UnoConsoleCard>();
		while (deck.size() != 0) {
			int index = (int) Math.round(Math.random() * (deck.size() - 1));
			shuffledDeck.add(deck.get(index));
			deck.remove(index);
		}
		return shuffledDeck;
		
	}
	
	/**
	 * Will deal 7 cards to each player
	 * 
	 * @param hand1 - First player's hand
	 * @param hand2 - Second player's hand
	 */
	private void dealCards(ArrayList<UnoConsoleCard> hand1, ArrayList<UnoConsoleCard> hand2) {
		for (double i = 0; i < 14; i++) {
			if (hand1.size() == 7) {
				hand1.add(deck.get(0));
				deck.remove(0);
			} else {
				hand2.add(deck.get(0));
				deck.remove(0);
			}
		}
	}
	
	private void drawCard(UnoTurnConstants turn) {
		if (turn == UnoTurnConstants.HUMAN) {
			player1Hand.add(deck.get(0));
			deck.remove(0);
		} else {
			player2Hand.add(deck.get(0));
			deck.remove(0);
		}
			
	}

}
