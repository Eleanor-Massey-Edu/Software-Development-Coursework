import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

//For some reason have to import the class you are wanting to test even though they are in the same package
import game.CardDeck;
import game.Card;

public class testCardDeck {
    
    public static CardDeck deck1;
    public static CardDeck deck2;
    public static CardDeck deck3;
    public static CardDeck deck4;
    public static CardDeck deck5;
    public static CardDeck deck6;
    public static CardDeck deck7;
    public static CardDeck deck8;

    @BeforeAll 
    static void setup() {
        deck1 = new CardDeck();
        deck2 = new CardDeck();
        deck3 = new CardDeck();
        deck4 = new CardDeck();
        deck5 = new CardDeck();
        deck6 = new CardDeck();
        deck7 = new CardDeck();
        deck8 = new CardDeck();
    }
    
    @Test 
    @Tag("unit")
    void testCardDeckGetMethods(){
        //getSize()
        assertSame(0,deck1.getSize(),"The deck is the wrong size");
        Card card1 = new Card(6);
        deck1.addToDeck(card1);
        assertEquals(1,deck1.getSize());
        //Names should be in the order they were created
        assertEquals("deck1",deck1.getName(),"The deck's name is wrong");
        assertEquals("deck2",deck2.getName(),"The deck's name is wrong");
        assertEquals("deck3",deck3.getName(),"The deck's name is wrong");
        assertEquals("deck4",deck4.getName(),"The deck's name is wrong");
        //getNameSpace()is for returning the name with a space after it for outputting purposes
        assertEquals("deck 1",deck1.getNameSpace(),"The deck's name with a space is wrong");
    }

    @Test 
    @Tag("unit")
    void testCardDeckDraw(){
        Card card1 = new Card(4);
        Card card2 = new Card(3);
        Card card3 = new Card(2);
        Card card4 = new Card(1);
        deck2.addToDeck(card1);
        deck2.addToDeck(card2);
        deck2.addToDeck(card3);
        deck2.addToDeck(card4);
        //deck1 should look like [4,3,2,1]. Should draw the 6 and get rid of the 6 from the deck
        assertEquals(4,deck2.draw().getValue(),"The card that was drawn does not have the right value");
        assertEquals(3,deck2.draw().getValue(),"The card that was drawn does not have the right value");
        assertEquals(2,deck2.draw().getValue(),"The card that was drawn does not have the right value");
        assertEquals(1,deck2.draw().getValue(),"The card that was drawn does not have the right value");
        assertThrows(IndexOutOfBoundsException.class,() -> {deck2.draw();});

    }

    @Test 
    @Tag("unit")
    void testPrintDecks(){
        Card card1 = new Card(7);
        Card card2 = new Card(13);
        Card card3 = new Card(4);
        Card card4 = new Card(1);
        Card card5 = new Card(8);
        Card card6 = new Card(3);
        Card card7 = new Card(11);
        Card card8 = new Card(4);
        Card card9 = new Card(9);
        deck5.addToDeck(card1);
        deck5.addToDeck(card2);
        deck6.addToDeck(card3);
        deck6.addToDeck(card4);
        deck7.addToDeck(card5);
        deck7.addToDeck(card6);
        deck7.addToDeck(card7);
        deck8.addToDeck(card8);
        deck8.addToDeck(card9);
        
        CardDeck.printDecks();
        //check the output files. 
        // Should see deck1_output.txt having deck1 contents: 7 13
        // Should see deck2_output.txt having deck1 contents: 4 1 
        // Should see deck3_output.txt having deck1 contents: 8 3 11
        // Should see deck4_output.txt having deck1 contents: 4 9

    }
}
