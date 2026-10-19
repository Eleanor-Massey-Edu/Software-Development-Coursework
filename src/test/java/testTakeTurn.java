import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import game.CardDeck;
import game.Pack;
import game.Player;
import game.Card;
//For some reason have to import the class you are wanting to test even though they are in the same package
import game.TakeTurn;
import game.CardGame;

public class testTakeTurn {

    private static final java.io.InputStream originalIn = System.in;

    @BeforeEach 
    void testSetUpGame(){

        String simulatedInput = "2\nsrc/test/resources/testTwo.txt";

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

        Scanner scanner = new Scanner(System.in);
        CardGame.setUp(scanner);

    }

    @Test
    @Tag("unit")
    void testAtomicCardDrawHandCount() {
        TakeTurn player1TakeTurn = new TakeTurn(CardDeck.getCardDeck(1), CardDeck.getCardDeck(2), Player.getHand(1), 1);
        TakeTurn player2TakeTurn = new TakeTurn(CardDeck.getCardDeck(2), CardDeck.getCardDeck(1), Player.getHand(2), 2);

        int correctPlayer1HandSize = Player.getHand(1).size();

        Card newCard = CardDeck.getCardDeck(1).draw(); // draw a card from player 1's deck
        player1TakeTurn.chooseAndDiscard(newCard); // choose a card to discard and discard it from player 1's hand
        assertEquals(correctPlayer1HandSize, Player.getHand(1).size());

        int correctPlayer2HandSize = Player.getHand(2).size();

        Card newCard2 = CardDeck.getCardDeck(2).draw(); // draw a card from player 1's deck
        player2TakeTurn.chooseAndDiscard(newCard2); // choose a card to discard and discard it from player 1's hand
        assertEquals(correctPlayer2HandSize, Player.getHand(2).size());
    }

        /*Player player1 = new Player();
        Player player2 = new Player();
        CardDeck deck1 = new CardDeck();
        CardDeck deck2 = new CardDeck();
        try{ //have to put in a try block but since the path is hard coded it can't be wrong
            Pack pack1 = new Pack("src/test/resources/testTwo.txt",2);
            pack1.deal();
        } catch (Exception fileex){
        }
        TakeTurn player1TakeTurn = new TakeTurn(CardDeck.getCardDeck(1), CardDeck.getCardDeck(2), Player.getHand(1), 1);
        TakeTurn player2TakeTurn = new TakeTurn(CardDeck.getCardDeck(2), CardDeck.getCardDeck(1), Player.getHand(2), 2);
        TakeTurn.takeTurnArray.add(player1TakeTurn);
        Player.playerThreadArray.add(new Thread(player1TakeTurn, "1"));
        Thread t1*/

    @Test 
    @Tag("unit")
    void testCorrectDiscardCheck(){ // Test to see if players are discarding the correct cards, i.e. player 1 is not discarding 1s
        TakeTurn player1TakeTurn = new TakeTurn(CardDeck.getCardDeck(1), CardDeck.getCardDeck(2), Player.getHand(1), 1);
        TakeTurn player2TakeTurn = new TakeTurn(CardDeck.getCardDeck(2), CardDeck.getCardDeck(1), Player.getHand(2), 2);

        Card newCard = CardDeck.getCardDeck(1).draw(); // draw a card from player 1's deck
        player1TakeTurn.chooseAndDiscard(newCard); // choose a card to discard and discard it from player 1's hand

        assertNotEquals(1, CardDeck.getCardDeck(2).deck.get(CardDeck.getCardDeck(2).getSize() - 1));


        Card newCard2 = CardDeck.getCardDeck(2).draw(); // draw a card from player 2's deck
        player2TakeTurn.chooseAndDiscard(newCard2); // choose a card to discard and discard it from player 2's hand
        
        assertNotEquals(2, CardDeck.getCardDeck(1).deck.get(CardDeck.getCardDeck(1).getSize() - 1));

    }

    @Test
    @Tag("unit")

    void testFIFOBehaviour() {
        TakeTurn player1TakeTurn = new TakeTurn(CardDeck.getCardDeck(1), CardDeck.getCardDeck(2), Player.getHand(1), 1);
        TakeTurn player2TakeTurn = new TakeTurn(CardDeck.getCardDeck(2), CardDeck.getCardDeck(1), Player.getHand(2), 2);

        Card newCard = CardDeck.getCardDeck(1).draw();
        player1TakeTurn.chooseAndDiscard(newCard);

        Card cardToTrack = CardDeck.getCardDeck(2).deck.get(CardDeck.getCardDeck(2).getSize() - 1); // first card added to bottom of deck

        // do 4 turns so all of the ORIGINAL cards are removed, and only the added cards remain.
        for (int i = 0; i < 4; i++) {
            Card card = CardDeck.getCardDeck(1).draw();
            player1TakeTurn.chooseAndDiscard(card); 

            Card card2 = CardDeck.getCardDeck(2).draw();
            player2TakeTurn.chooseAndDiscard(card2);
        }

        assertSame(cardToTrack, CardDeck.getCardDeck(2).deck.get(0));
        }
    }

