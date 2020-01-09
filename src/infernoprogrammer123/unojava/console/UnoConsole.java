package infernoprogrammer123.unojava.console;

public class UnoConsole {
	
	static UnoConsoleGame mainGame;
	
	public static void main(String[] args) {
		mainGame = new UnoConsoleGame();
		mainGame.setup();
	}
	
}
