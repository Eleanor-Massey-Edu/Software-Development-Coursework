import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

//For some reason have to import the class you are wanting to test even though they are in the same package
import game.CardDeck;
import game.Pack;
import game.Player;
import game.Card;
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
    @Tag("integration")
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

    @Test 
    @Tag("integration")
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

