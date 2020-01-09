package infernoprogrammer123.unojava.console;

import java.util.ArrayList;
import java.util.Scanner;

import infernoprogrammer123.unojava.constants.*;

public class UnoConsoleGame {
	
	// Represents the complete deck
	private ArrayList<UnoConsoleCard> deck = new ArrayList<UnoConsoleCard>();
	private ArrayList<UnoConsoleCard> discardPile = new ArrayList<UnoConsoleCard>();
	private ArrayList<UnoConsolePlayer> players = new ArrayList<UnoConsolePlayer>();
	private int turn = 1;
	
	public UnoConsoleCard topCard;
	
	public static void main(String[] args) {
		UnoConsoleGame game = new UnoConsoleGame();
		game.setup();
	}
	
	public void setup() {
		// Create and shuffle the deck
		createDeck();
		deck = shuffleDeck();

		// Create each player's hand
		dealCards();

		// Set the top card
		topCard = deck.get(0);
		deck.remove(0);
		
		// Setup the players
		for (int i = 1; i < 5; i++) {
			System.out.print("Enter p if player " + i + " is human, and enter anything else if player " + i + " will be a computer: ");
			String userInput = userInput();
			if (userInput.toLowerCase().equals("p")) {
				players.add(new UnoConsolePlayer(UnoPlayerConstants.HUMAN));
			} else {
				players.add(new UnoConsolePlayer(UnoPlayerConstants.COMPUTER));
			}
		}
		
		// Deal cards
		dealCards();

		// Begin the game
		play();
	}

	private void play() {
		
		for (UnoConsolePlayer player:players) {
			for (UnoConsoleCard card:player.getHand()) {
				System.out.println(card.getCard());
			}
		}
		
		System.out.println("Top card: " + topCard.getCard());
		
		UnoConsoleCard played = players.get(0).playerMove();
		if (played == null) {
			System.out.println("You had no valid cards");
		} else {
			System.out.println(played.getCard());
		}
		
	}
	
	public UnoConsoleCard getTopCard() {
		return topCard;
	}
	
	public ArrayList<UnoConsoleCard> getDiscardPile() {
		return discardPile;
	}

	/**
	 * Will create the deck of cards
	 *
	 * @param deck - Represents the deck of cards
	 */
	private void createDeck() {
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
	 * Will shuffle the deck of cards
	 *
	 * @param deck - Represents the deck
	 * @return A shuffled deck of "cards"
	 */
	private ArrayList<UnoConsoleCard> shuffleDeck() {
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
	private void dealCards() {
		for (UnoConsolePlayer player:players) {
			for (int i = 0; i < 7; i++) {
				player.getHand().add(deck.get(0));
				discardPile.add(deck.get(0));
				deck.remove(0);
			}
		}
	}

	private void drawCard() {
		players.get(turn - 1).getHand().add(deck.get(0));
		discardPile.add(deck.get(0));
		deck.remove(0);
	}
	
	public static String userInput() {
		Scanner in = new Scanner(System.in);
		String s = in.nextLine();
		return s;
	}

}
