import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;


//For some reason have to import the class you are wanting to test even though they are in the same package
import game.Player;
import game.Card;
import game.CardDeck;
import game.Pack;

public class testPlayer {
    Player player1;
    Player player2;
    CardDeck deck1;
    CardDeck deck2;


    @BeforeAll 
    void setupPlayers(){
        player1 = new Player();
        player2 = new Player();
        deck1 = new CardDeck();
        deck2 = new CardDeck();
        try{ //have to put in a try block but since the path is hard coded it can't be wrong
            Pack pack1 = new Pack("src/test/resources/testTwo.txt",2);
            pack1.deal();
        } catch (Exception fileex){

        }
    }

    @Test 
    @Tag("unit")
    void testPlayerGetters(){
        //getID()
        assertEquals(1, player1.getID());
        assertEquals(2, player2.getID());
        //getPlayer(id)
        assertEquals(player1, Player.getPlayer(1));
        assertEquals(player2, Player.getPlayer(2));
        //getHand(id)
        //already tested in the testPack class, testDeal() method
    }

    @Test 
    @Tag("unit")
    void testPlayerAddToHand(){
        Player player1 = new Player();
        Card card1 = new Card(1);
        Card card2 = new Card(2);
        player1.addToHand(card1);
        player1.addToHand(card2);
        assertEquals(1,Player.getHand(1).get(0).getValue());
        assertEquals(2,Player.getHand(1).get(1).getValue());
    }

    @Test 
    @Tag("unit")
    void testHandCountInvarience(){
        Card newCard = this.deck1.draw();
        this.chooseAndDiscard(newCard);
    }
}
