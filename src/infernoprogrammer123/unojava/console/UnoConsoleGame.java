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
	
	private void setup() {
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
		
		// Pick the turn
		turn = (int) (Math.random() * 3);
	}

	public void play() {
		
		// Variable to store whether player is playing or not
		boolean playingGame = true;
		
		// While the game is playing
		while (playingGame) {
			// Set up the game
			setup();
			
			// main game
			boolean mainGame = true;
			while (mainGame) {
				UnoConsolePlayer currentPlayer = players.get(turn);
				System.out.println("It is now Player " + (turn + 1) + "'s turn");
				System.out.println("The top card is: " + topCard.getCard());
				// If the player is a human...
				if (currentPlayer.getPlayerType() == UnoPlayerConstants.HUMAN) {
					// Tell user how many cards other players have
					for (int j = 0; j < 4; j++) {
						if (!players.get(j).equals(currentPlayer)) {
							System.out.println("Player " + (j + 1) + " currently has " + players.get(j).getHand().size() + " cards.");
						}
					}
					
					// Get the player's move
					UnoConsoleCard cardPlayed = currentPlayer.playerMove();
					
					// Return the wild card back to a wild card
					if (topCard.getNumber() == UnoNumberConstants.PLUS_FOUR || topCard.getNumber() == UnoNumberConstants.WILD) {
						topCard.setColor(UnoColorConstants.WILD);
						discardPile.get(0).setColor(UnoColorConstants.WILD);
					}
					
					// If the player had to draw a card
					if (cardPlayed == null) {
						drawCard();
					} else {
						// Check if the move is special
						checkSpecial(cardPlayed);
						
						// Add it to the top
						addToTop(cardPlayed);
					}
				} else {
					// Get the pc's move
					UnoConsoleCard cardPlayed = currentPlayer.pcMove();
					
					// Return the wild card back to a wild card
					if (topCard.getNumber() == UnoNumberConstants.PLUS_FOUR || topCard.getNumber() == UnoNumberConstants.WILD) {
						topCard.setColor(UnoColorConstants.WILD);
						discardPile.get(0).setColor(UnoColorConstants.WILD);
					}
					
					// If the player had to draw a card
					if (cardPlayed == null) {
						drawCard();
					} else {
						// Check if the move is special
						checkSpecial(cardPlayed);
						
						// Add it to the top
						addToTop(cardPlayed);
					}
				}
				shiftTurn();
				
			}
		}
		
		
	}
	
	public UnoConsoleCard getTopCard() {
		return topCard;
	}
	
	public ArrayList<UnoConsoleCard> getDiscardPile() {
		return discardPile;
	}
	
	private void checkSpecial(UnoConsoleCard cardPlayed) {
		if (cardPlayed.getColor() == UnoColorConstants.WILD) {
			if (players.get(turn).getPlayerType() == UnoPlayerConstants.HUMAN) {
				boolean gettingInput = true;
				
				while (gettingInput) {
					System.out.println("You must select a number for one of the 4 colors that your wild card will be\n1 - Red\n2 - Yellow\n3 - Green\n4 - Blue");
					int userInput;
					
					try {
						userInput = Integer.parseInt(userInput());
						if (userInput > 4 || userInput < 1) {
							System.out.println("Unfortunately your input is out of range! Try again");
						} else {
							if (userInput == 1) {
								cardPlayed.setColor(UnoColorConstants.RED);
							} else if (userInput == 2) {
								cardPlayed.setColor(UnoColorConstants.YELLOW);
							} else if (userInput == 3) {
								cardPlayed.setColor(UnoColorConstants.GREEN);
							} else {
								cardPlayed.setColor(UnoColorConstants.BLUE);
							}
							
							gettingInput = false;
						}
					} catch (NumberFormatException nfe) {
						System.out.println("Unfortunately your input is non numeric, it must be numeric!");
					}
				}
			} else {
				int pcInput = (int) ((Math.random() * 3) + 1);
				
				if (pcInput == 1) {
					cardPlayed.setColor(UnoColorConstants.RED);
				} else if (pcInput == 2) {
					cardPlayed.setColor(UnoColorConstants.YELLOW);
				} else if (pcInput == 3) {
					cardPlayed.setColor(UnoColorConstants.GREEN);
				} else {
					cardPlayed.setColor(UnoColorConstants.BLUE);
				}
				System.out.println("PC: The wild card is now a " + cardPlayed.getCard());
			}
		}
		
		if (cardPlayed.getNumber() == UnoNumberConstants.SKIP) {
			shiftTurn();
		}
	}
	
	private void addToTop(UnoConsoleCard cardPlayed) {
		discardPile.add(cardPlayed);
		topCard = cardPlayed;
	}
	
	private void shiftTurn() {
		if (turn == 3) {
			turn = 0;
		} else {
			turn++;
		}
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
		players.get(turn).getHand().add(deck.get(0));
		discardPile.add(deck.get(0));
		deck.remove(0);
	}
	
	public static String userInput() {
		Scanner in = new Scanner(System.in);
		String s = in.nextLine();
		return s;
	}

}
