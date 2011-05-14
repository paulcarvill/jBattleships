#JBattleships
JBattleships is a game where you and your opponent position a set of battleships in the ocean, represented by a grid. You then take turns to fire at each other's ships in order to sink them. Play continues until one player has lost all their ships.

##Usage:
There are three classes used in the game:

1. Ship represents a ship in the game, with its own length property.

2. JBattleships takes care of the grid setup, placement of ships and firing of missiles.

3. JBattleshipsGame takes care of setting up a two-player game and handles the input.

They are both in the JBattleships package.

Run the game on the command line:
	
	java -cp src battle/JBattleshipsGame
	

##Compilation/Tests:
To keep the checkout and build of the project simple, just run the build script to compile the source and run the unit tests.

	./build.sh