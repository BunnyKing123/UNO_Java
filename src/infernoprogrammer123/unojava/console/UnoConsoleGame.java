package infernoprogrammer123.unojava.console;

import java.util.ArrayList;
import java.util.Scanner;

import infernoprogrammer123.unojava.constants.*;

public class UnoConsoleGame {
	
	// Represents the complete deck
	private ArrayList<UnoConsoleCard> deck = new ArrayList<UnoConsoleCard>();
	private ArrayList<UnoConsoleCard> discardPile = new ArrayList<UnoConsoleCard>();
	private ArrayList<UnoConsolePlayer> players = new ArrayList<UnoConsolePlayer>();
	private int turn;
	private int numPlayers;
	
	private UnoConsoleCard topCard;
	
	public static void main(String[] args) {
		UnoConsoleGame game = new UnoConsoleGame();
		game.setup();
	}
	
	private void setup() {
		// Get number of players
		boolean gettingPlayers = true;
		while (gettingPlayers) {
			try {
				System.out.print("Let's play UNO, how many players would you like(Between 2 and 8): ");
				int userInput = Integer.parseInt(userInput());
				if (userInput < 2 || userInput > 8) {
					System.out.println("Unfortunately your input was out of range! Try again");
				} else {
					numPlayers = userInput;
					gettingPlayers = false;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Unfortunately your input was non numeric, make sure it is numeric!");
			}
		}
		System.out.println();
		
		// Setup the players
		for (int i = 0; i < numPlayers; i++) {
			// Set the player's name
			System.out.print("Enter this Player" + (i + 1) + "'s name: ");
			String name = userInput();
			
			System.out.print("Enter p if Player " + (i + 1) + " is human, and enter anything else if Player " + (i + 1) + " will be a computer: ");
			String userInput = userInput();
			if (userInput.toLowerCase().equals("p")) {
				players.add(new UnoConsolePlayer(UnoPlayerConstants.HUMAN, name));
			} else {
				players.add(new UnoConsolePlayer(UnoPlayerConstants.COMPUTER, name));
			}
			

			
			
			System.out.println();
		}
		
		// Create and shuffle the deck
		createDeck();
		shuffleDeck(deck);

		// Create each player's hand
		dealCards();

		// Set the top card
		topCard = deck.get(0);
		discardPile.add(deck.get(0));
		deck.remove(0);
		
		// Pick the turn
		turn = (int) (Math.random() * (numPlayers - 1));
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
				System.out.println("It is now " + currentPlayer + "'s turn");
				System.out.println("The top card is: " + topCard);
				// If the player is a human...
				if (currentPlayer.getPlayerType() == UnoPlayerConstants.HUMAN) {
					// Tell user how many cards other players have
					for (int j = 0; j < numPlayers; j++) {
						if (!players.get(j).equals(currentPlayer)) {
							System.out.println(players.get(j) + " currently has " + players.get(j).getHand().size() + " cards.");
						}
					}
					System.out.println();
					
					// Get the player's move
					UnoConsoleCard cardPlayed = currentPlayer.playerMove();
					System.out.println();
					
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
				System.out.println("PC: The wild card is now a " + cardPlayed);
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
		if (turn == (numPlayers - 1)) {
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
		// Initialize necessary arrays
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
		
		UnoColorConstants[] colorConstants = {
				UnoColorConstants.RED,
				UnoColorConstants.YELLOW,
				UnoColorConstants.GREEN,
				UnoColorConstants.BLUE
		};
		
		UnoNumberConstants[] wildNums = {
				UnoNumberConstants.WILD,
				UnoNumberConstants.PLUS_FOUR
		};
		
		for (int i = 0; i < 4; i++) {
			UnoColorConstants colorConstant = colorConstants[i];

			// Add the zero card of that color
			deck.add(new UnoConsoleCard(colorConstant, UnoNumberConstants.ZERO));

			for (int j = 0; j < 24; j++) {
				// Add the number/type card of that color
				int index = j / 2;
				deck.add(new UnoConsoleCard(colorConstant, numberConstants[index]));
			}

		}

		for (int k = 0; k < 8; k++) {
			// Add the wild card
			int index = k / 4;
			deck.add(new UnoConsoleCard(UnoColorConstants.WILD, wildNums[index]));
		}
	}

	/**
	 * Will shuffle the deck of cards
	 *
	 * @param deck - Represents the deck
	 * @return A shuffled deck of "cards"
	 */
	private void shuffleDeck(ArrayList<UnoConsoleCard> cards) {
		ArrayList<UnoConsoleCard> shuffledDeck = new ArrayList<UnoConsoleCard>();
		while (cards.size() != 0) {
			int index = (int) Math.round(Math.random() * (cards.size() - 1));
			shuffledDeck.add(cards.get(index));
			cards.remove(index);
		}
		deck = shuffledDeck;

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
				deck.remove(0);
			}
		}
	}

	private void drawCard() {
		players.get(turn).getHand().add(deck.get(0));
		deck.remove(0);
	}
	
	private void checkEmptyDeck() {
		if (deck.size() == 0) {
			shuffleDeck(discardPile);
			for (UnoConsoleCard card:discardPile) {
				discardPile.remove(card);
			}
		}
	}
	
	public static String userInput() {
		Scanner in = new Scanner(System.in);
		String s = in.nextLine();
		return s;
	}

}
