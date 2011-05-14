package Battleships;
import java.io.*;
import java.util.Random;

public class JBattleships {

	private static final String EOL = System.getProperty("line.separator");
	private int gridWidth = 20;
	private int gridHeight = 20;
	private String[][] grid;
	private Ship[] ships;
	private int totalHitsRequired = 0;
	private int totalHitsMade = 0;
	private Random generator = new Random();
	
	public int getGridWidth(){
		return gridWidth;
	}

	public int getGridHeight(){
		return gridHeight;
	}
	
	public String printGrid(){
		String gridToPrint = "  ";
		
		if(gridHeight > 9){
			gridToPrint = "   ";
		}
		
		char x = 65;
		for (int i = 0; i < gridWidth; i++) {
			String xAxisLabel = x + " ";
			gridToPrint += xAxisLabel;
			x++;
		}
		gridToPrint += EOL;
		
		for(int i = 0, j = grid.length; i < j; i++) {
			String yAxisLabel = i > 9 ? i + " " : " " + i + " ";
			gridToPrint += yAxisLabel;
			for(int k = 0, l = grid[i].length; k < l; k++) {
				gridToPrint += grid[i][k];
			}
			gridToPrint += EOL;
		}
		return gridToPrint;
	}
	
	private String printXAxis(){
		String xAxis = "  ";
		
		if(gridHeight > 9){
			xAxis = "   ";
		}
	
		char x = 65;
		for (int i = 0; i < gridWidth; i++) {
			String xAxisLabel = x + " ";
			xAxis += xAxisLabel;
			x++;
		}
		xAxis += EOL;
		return xAxis;
	}
	
	public String printOpponentGrid(){
		String gridToPrint = printXAxis();
		
		for(int i = 0, j = grid.length; i < j; i++) {
			String yAxisLabel = i > 9 ? i + " " : " " + i + " ";
			gridToPrint += yAxisLabel;
			for(int k = 0, l = grid[i].length; k < l; k++) {
				if(grid[i][k] == "o ") {
					gridToPrint += "~ ";
				}
				else {
					gridToPrint += grid[i][k];
				}
			}
			gridToPrint += EOL;
		}
		return gridToPrint;
	}
	
	public String getGridReference(int x, int y){
 		return grid[x][y];
	}
	
	private String[][] setUpGrid(){
		String[][] grid = new String[gridWidth][gridHeight];
		for(int i = 0, j = grid.length; i < j; i++){
			for(int k = 0, l = grid[i].length; k < l; k++){
				grid[i][k] = "~ ";
			}
		}
		return grid;
	}
	
	public Ship[] getShips(){
		return ships;
	}
	
	private Ship[] setUpShips(){
		Ship[] ships = new Ship[5];
		for(int i = 0; i < 5; i++){
			ships[i] = new Ship(2+i);
			totalHitsRequired += (2+i);
		}
		return ships;
	}
	
	public void placeShip(Ship ship, int x, int y, char aspect){
		
		int i = 0;
		int shipLength = ship.getLength();
		
		if(aspect == 'h') {
			if((x + shipLength) > getGridWidth()) {
				throw new IllegalArgumentException("ship placed horizontally outside grid");
			}
		}
		else if(aspect == 'v') {
			if((y + shipLength) > getGridHeight()) {
				throw new IllegalArgumentException("ship placed vertically outside grid");
			}
		}
		
		while(i < shipLength) {
			if(grid[y][x] == "o ") {
				throw new IllegalArgumentException("ship placed over another ship (x:" + x + ", y:" + y + ")");
			}
			grid[y][x] = "o ";
			if(aspect == 'h'){
				x++;
			}
			else {
				y++;
			}
			i++;
		}
	}
	
	public void placeRandomShips(){
		for(int i = 0, j = ships.length; i < j; i++) {
			int isGridRefEmpty = 0;
			int x = 0;
			int y = 0;
			int shipLength = ships[i].getLength();
			int randomIndex = generator.nextInt(10);
			char aspect = 'h';
			
			while(isGridRefEmpty == 0) {
				
				if(randomIndex < 5) {
					x = generator.nextInt(gridWidth - shipLength);
					y = generator.nextInt(gridHeight);
				}
				else {
					x = generator.nextInt(gridWidth);
					y = generator.nextInt(gridHeight - shipLength);
				}
				aspect = randomIndex < 5 ? 'h' : 'v';
				
				int k = 0;
				int l = x;
				int m = y;
				int numberOfFullGridRefs = 0;
				
				while(k < shipLength) {
					if(grid[m][l] == "o ") {
						numberOfFullGridRefs++;
						break;
					} 
					if(aspect == 'h'){
						l++;
					}
					else {
						m++;
					}
					k++;
				}
				isGridRefEmpty = (numberOfFullGridRefs == 0) ? 1 : 0;
			}
			placeShip(ships[i], x, y, aspect);
		}
	}
	
	public void fireMissile(int x, int y){
		if(grid[y][x] == "x "){
			return;
		}
		if(grid[y][x] == "o ") {
			grid[y][x] = "x ";
			totalHitsMade++;
		}
		else {
			grid[y][x] = "  ";
		}
		
	}
	
	public void fireRandomMissile(){
		Random generator = new Random();
		int x = generator.nextInt(gridWidth);
		int y = generator.nextInt(gridHeight);
		fireMissile(x, y);
	}
	
	public Boolean isGameOver(){
		if(totalHitsMade == totalHitsRequired){
			return true;
		}
		return false;
	}
	
	public void setGameOver(){
		totalHitsMade = totalHitsRequired;
	}
	
	public JBattleships(int gridWidth, int gridHeight){
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		this.grid = setUpGrid();
		this.ships = setUpShips();
	}
	
	public JBattleships(){
		this.grid = setUpGrid();
		this.ships = setUpShips();
	}
	
}

