# Software-Development-Coursework
Threaded self-playing card game for ECM2414 Software Development Coursework

## Requirements

- JDK 25+
- JUnit 5
- Visual Studio Code 1.142.0

### In order to run the cards.jar project

Firstly, navigate to the directory which includes cards.jar.

Then in your terminal, run:
```bash
java -jar cards.jar
```
Input the number of players when prompted.

Then input the path of the pack file when prompted.

## Running the JUnit 5 Tests

This project uses JUnit 5 to run tests. It is not compiled in Maven and so individual tests must be ran form the terminal. Make sure junit-platform-console is in the project lib directory.

To run the provided tests, please follow the instructions for the Terminal. Although, feel free to use your IDE of choice, or follow instructions provided to run in VS code

# VS code version 

Install Test Runner for Java extension. Navigate to the testing section, which is the beaker icon on the left. Then, you can individually hover over each individual method and run them.

# Terminal

In order to run from the terminal, make sure you are in the root directory. In order to run the JUnit tests, you have to run each method seperately. The commands for doing so are below. The .class files for the tests must be in the bin folder, which is located in your root directory. Please compile your .class files from the tests to the bin directory in the root.

### How to compile test .java files

The submission already comes with compiled .class files for the tests in the bin folder, however if you need to compile them, run this in your terminal from root directory:

```bash
javac -d bin -cp "lib/junit-platform-console-standalone-1.14.4.jar:bin" src/test/java/*.java
```

### How to run test methods from the terminal

```bash
java -jar ./lib/junit-platform-console-standalone-1.14.4.jar execute --class-path bin --select-method "testTakeTurn#testAtomicCardDrawHandCount"
```

```bash
java -jar ./lib/junit-platform-console-standalone-1.14.4.jar execute --class-path bin --select-method "testTakeTurn#testCorrectDiscardCheck"
```

```bash
java -jar ./lib/junit-platform-console-standalone-1.14.4.jar execute --class-path bin --select-method "testTakeTurn#testFIFOBehaviour"
```

```bash
java -jar ./lib/junit-platform-console-standalone-1.14.4.jar execute --class-path bin --select-method "testPlayer#testPlayerGetters"
```

```bash
java -jar ./lib/junit-platform-console-standalone-1.14.4.jar execute --class-path bin --select-method "testPlayer#testPlayerAddToHand"
```

```bash
java -jar ./lib/junit-platform-console-standalone-1.14.4.jar execute --class-path bin --select-method "testPack#testPackValidation"
```

```bash
java -jar ./lib/junit-platform-console-standalone-1.14.4.jar execute --class-path bin --select-method "testPack#testPackDeal"
```

```bash
java -jar ./lib/junit-platform-console-standalone-1.14.4.jar execute --class-path bin --select-method "testPack#testImmediateWin"
```

```bash
java -jar ./lib/junit-platform-console-standalone-1.14.4.jar execute --class-path bin --select-method "testCardDeck#testCardDeckGetMethods"
```

```bash
java -jar ./lib/junit-platform-console-standalone-1.14.4.jar execute --class-path bin --select-method "testCardDeck#testCardDeckDraw"
```

```bash
java -jar ./lib/junit-platform-console-standalone-1.14.4.jar execute --class-path bin --select-method "testCardDeck#testPrintDecks"
```

```bash
java -jar ./lib/junit-platform-console-standalone-1.14.4.jar execute --class-path bin --select-method "testCard#testGetValue"
```
