import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

//For some reason have to import the class you are wanting to test even though they are in the same package
import game.Card;

public class testCard {
    @Test 
    @Tag("unit")
    void testGetValue(){
        Card card = new Card(5);
        assertEquals(5,card.getValue(),"The card has changed value");
    }
}
