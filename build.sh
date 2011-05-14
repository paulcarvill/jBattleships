# run using:
# sudo ./build.sh

# compile class under test
javac -cp .:src:lib:lib/junit4.8.1/junit-4.8.1.jar src/Battleships/*.java

# compile test class
javac -cp .:src:test:lib/junit4.8.1/junit-4.8.1.jar test/JBattleshipsTest.java

# run test
java -cp .:src:test:lib/junit4.8.1/junit-4.8.1.jar org.junit.runner.JUnitCore JBattleshipsTest


# to jar archive the game code:
#
# cd src
# jar cvf JAR_NAME.jar battle
#
# 
# to run code from jar:
#
# java -classpath JAR_NAME.jar battle.JBattleShipsGame


