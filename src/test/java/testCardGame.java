import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

//For some reason have to import the class you are wanting to test even though they are in the same package
import game.CardGame;

public class testCardGame {

    public class InputReader {
        public String readInput() {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        }
}


    @Test 
    @Tag("notWorking")
    void testSetUp(){ //tried to simulate the user inputs necessary for the setup() function but not able to

        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        String simulatedNumPlayers = "3";
        String simulatedFileLocation = "src/test/resources/testThree.txt";
    
        ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedNumPlayers.getBytes());
        System.setIn(testIn);

        

        Scanner scanner = new Scanner(simulatedNumPlayers);
        //String input = scanner.nextLine();
        
        CardGame.setUp(scanner);
        //check it keeps asking for a valid pack file until you provide it with one
    }
}
