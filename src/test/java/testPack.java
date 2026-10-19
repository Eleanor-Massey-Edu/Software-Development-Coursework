import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

//For some reason have to import the class you are wanting to test even though they are in the same package
import game.Pack;
import game.Player;
import game.CardDeck;
import game.CardGame;
import game.TakeTurn;
import exceptions.InvalidNumOfCardsException;
import exceptions.NegativeException;

public class testPack {
    @Test 
    @Tag("unit")
    void testPackValidation(){

        //Valid one person
        assertDoesNotThrow(() -> {
		    new Pack("src/test/resources/testOne.txt", 1);
        });

        //Valid two people
        assertDoesNotThrow(() -> {
		    new Pack("src/test/resources/testTwo.txt", 2);
        });

        //Valid ten people
        assertDoesNotThrow(() -> {
		    new Pack("src/test/resources/testTen.txt", 10);
        });

        //Negatives
        assertThrows(NegativeException.class, () -> {
		    new Pack("src/test/resources/testNegativesTwo.txt", 2);
        });

        assertThrows(NegativeException.class, () -> {
		    new Pack("src/test/resources/testNegativesBuriedTwo.txt", 2);
        });

        //Integers
        assertThrows(NumberFormatException.class, () -> {
		    new Pack("src/test/resources/testIntegersTwo.txt", 2);
        });

        assertThrows(NumberFormatException.class, () -> {
		    new Pack("src/test/resources/testIntegersBuriedTwo.txt", 2);
        });

        //Pack size
        assertThrows(InvalidNumOfCardsException.class, () -> {
		    new Pack("src/test/resources/testThreeIncorrectNumber.txt", 3);
        });
    }

    @Test 
    @Tag("unit")
    void testPackDeal(){
        Player player1 = new Player();
        Player player2 = new Player();
        CardDeck deck1 = new CardDeck();
        CardDeck deck2 = new CardDeck();
        
        try{ //have to put in a try block but since the path is hard coded it can't be wrong
            Pack pack1 = new Pack("src/test/resources/testTwo.txt",2);
            pack1.deal();
        } catch (Exception fileex){

        }
        
        //should have 4 cards in each player's hand and 4 cards in each deck to start, dealt in round robin fashion
        assertEquals(10, Player.getHand(1).get(0).getValue());
        assertEquals(1, Player.getHand(2).get(0).getValue());
        assertEquals(9, Player.getHand(1).get(1).getValue());
        assertEquals(4, Player.getHand(2).get(1).getValue());
        assertEquals(12, Player.getHand(1).get(2).getValue());
        assertEquals(11, Player.getHand(2).get(2).getValue());
        assertEquals(12, Player.getHand(1).get(3).getValue());
        assertEquals(9, Player.getHand(2).get(3).getValue());

        assertEquals(10, CardDeck.getCardDeck(1).deck.get(0).getValue());
        assertEquals(1, CardDeck.getCardDeck(2).deck.get(0).getValue());
        assertEquals(9, CardDeck.getCardDeck(1).deck.get(1).getValue());
        assertEquals(4, CardDeck.getCardDeck(2).deck.get(1).getValue());
        assertEquals(12, CardDeck.getCardDeck(1).deck.get(2).getValue());
        assertEquals(11, CardDeck.getCardDeck(2).deck.get(2).getValue());
        assertEquals(12, CardDeck.getCardDeck(1).deck.get(3).getValue());
        assertEquals(9, CardDeck.getCardDeck(2).deck.get(3).getValue());

    }

    @Test
    @Tag("unit")
    void testImmediateWin() {

        String simulatedInput = "2\nsrc/test/resources/testImmediateWinTwo.txt";

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

        Scanner scanner = new Scanner(System.in);
        CardGame.setUp(scanner);

        boolean hasWon = false;

        TakeTurn player1TakeTurn = new TakeTurn(CardDeck.getCardDeck(1), CardDeck.getCardDeck(2), Player.getHand(1), 1);

        if (player1TakeTurn.checkWin() != null) {
            hasWon = true;
        }

        assertTrue(hasWon, "Player should win immediately.");
    }
}