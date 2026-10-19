import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import game.CardDeck;
import game.Pack;
import game.Player;
//For some reason have to import the class you are wanting to test even though they are in the same package
import game.TakeTurn;
import game.CardGame;

public class testTakeTurn {
    @BeforeAll 
    static void makeThreads(){

        Scanner scanner = new Scanner(System.in);
        CardGame.setUp(scanner);
        for (Thread thread : CardGame.playerThreadArray){
            thread.start();
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
    }

    @Test 
    @Tag("unit")
    void testGetValue(){

    }
}
