package Battleships;
import java.io.*;

public class JBattleshipsGame {
	
	// TODO: put in some AI for computer
	// TODO: allow configurable frid size via command line flags
	private static JBattleships player1 = new JBattleships();
	private static JBattleships computer = new JBattleships();

	private static final String EOL = System.getProperty("line.separator");
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static String userInput1;
	private static String userInput2;
	private static String userInput3;
	private static int x;
	private static int y;
	private static char aspect;
	
	private static void setUpComputerShips(){
		computer.placeRandomShips();
	}
	
	private static void setUpShips(){
		
		Ship[] ships = player1.getShips();
		String error = "IO error trying to read ship co-ord!";
		
		// loop through each ship, asking user to place it in the game board
		for(int i = 0, j = ships.length; i < j; i++) {
			
			int cont = 0;
			while(cont == 0) {
				System.out.println(player1.printGrid()+"Ship "+(i+1)+
					" is "+ships[i].getLength()+" units long. Place ship "+(i+1)+"'s x co-ord:");
		
				try {
					userInput1 = br.readLine();
				}
				catch (IOException ioe) {
					System.out.println(error);
					System.exit(1);
				}
		
				System.out.println("Place ship "+(i+1)+"'s y co-ord:");
		
				try {
						userInput2 = br.readLine();
				}
				catch (IOException ioe) {
					System.out.println(error);
					System.exit(1);
				}
				System.out.println("Place your first ship horizontally or vertically? (h/v):");
				try {
						userInput3 = br.readLine();
				}
				catch (IOException ioe) {
					System.out.println(error);
					System.exit(1);
				}
				
				try {
					
					try {
						y = Integer.parseInt(userInput2);
					}
					catch (NumberFormatException nfe) {
						continue;
					}
					try {
						x = Integer.parseInt(userInput1);
					}
					catch (NumberFormatException nfe) {
						x = (int) Character.toUpperCase(userInput1.charAt(0)) - 65;
					}
					if(x < 0) {
						continue;
					}
					if(x == 17){
						player1.setGameOver();
						break;
					}
					aspect = userInput3.charAt(0);
				}
				catch (StringIndexOutOfBoundsException oobe) {
					System.out.println("Oops, try again!");
					continue;
				}
				cont++;
			}
			if(cont == 1){
				try {
					player1.placeShip(ships[i], x, y, aspect);
				}
				catch (IllegalArgumentException iae) {
					i--;
				}
				catch (ArrayIndexOutOfBoundsException oobe) {
					i--;
				}
			}
		}
	}
	
	private static int getInput() {
		
		System.out.println("Please fire your missile. Provide the x co-ord:");
		try {
			userInput1 = br.readLine();
		}
		catch (IOException ioe) {
			System.out.println("IO error trying to read co-ords!");
			System.exit(1);
		}
		if (userInput1.equals("quit")){
			return 2;
		}
		System.out.println("Now provide the y co-ord:");
		try {
			userInput2 = br.readLine();
		}
		catch (IOException ioe) {
			System.out.println("IO error trying to read co-ords!");
			System.exit(1);
		}
		if (userInput2.equals("quit")){
			return 2;
		}
		
		try {
			x = Integer.parseInt(userInput1);
		}
		catch (NumberFormatException nfe) {
			try {
				x = (int) Character.toUpperCase(userInput1.charAt(0)) - 65;
			}
			catch (StringIndexOutOfBoundsException sioobe) {
				System.out.println("Oops! You need to enter a grid ref.");
				return 0;
			}
		}
		
		try {
			y = Integer.parseInt(userInput2);
		}
		catch (NumberFormatException nfe) {
			System.out.println("Oops. The figure you entered for the Y coordinate is not valid.");
			return 0;
		}
		catch (StringIndexOutOfBoundsException sioobe) {
			System.out.println("Oops! You need to enter a grid ref.");
			return 0;
		}
		
		try { // fire player 1's missile at computer grid
				computer.fireMissile(x, y);
			}
		catch (ArrayIndexOutOfBoundsException aioob) {
			System.out.println("Oops! Try firing within the grid.");
			return 0;
		}	
		return 1;
	}
	
	public static void main(String... args){
		
		int x = 0;
		int y = 0;
		char aspect = 'h';
		Ship ship;
		
		// set up ships on the human player's grid
		setUpShips();
		
		System.out.println(player1.printGrid());
		
		// set up ships on the computer player's grid		
		computer.placeRandomShips();
		
		while(!player1.isGameOver() && !computer.isGameOver()){
			
			int continueGame = 0;
			while(continueGame == 0) {
				continueGame = getInput();	
			}
			
			if (continueGame == 2) { // user has quit
				break;
			}
			
			// fire computers's missile at player 1's grid
			player1.fireRandomMissile();
			
			System.out.println("PLAYER 1 GRID:"+EOL+player1.printGrid());
			Boolean isOpponent = new Boolean(true);
			System.out.println("COMPUTER GRID:"+EOL+computer.printOpponentGrid());
		}
		
		if(player1.isGameOver()) {
			System.out.println("ALL PLAYER 1 SHIPS SUNK! COMPUTWR WINS! GAME OVER!");
		}
		else if (computer.isGameOver()) {
			System.out.println("ALL COMPUTER SHIPS SUNK! PLAYER 1 WINS! GAME OVER!");
		}
		else {
			System.out.println("You quit.");
		}
	}
	
}