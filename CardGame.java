import java.util.ArrayList;
import java.util.Scanner;

public class CardGame {
    //array of all the player threads
    public static ArrayList<Thread> playerThreadArray = new ArrayList<Thread>();
    public static void main(String[] args) {
        setUp();
        for (Thread thread:playerThreadArray){
            thread.start();
        }
    }

    public static void setUp() {
        // Make scanner object (input)
        Scanner scanner = new Scanner(System.in);
        boolean packValid = false;
        Pack pack = null;

        //Ask user the number of players
        System.out.println("Please enter the number of players: ");
        String stringNumPlayers = scanner.nextLine();
        int numPlayers = Integer.parseInt(stringNumPlayers); // change to int

        while (!packValid){
            //Ask user for the pack to use for the game
            System.out.println("Please enter location of pack to load: ");
            String packLocation = scanner.nextLine();


            try{ //try to instantiate the pack and if an error has been thrown, deal with it in the catch
                pack = new Pack(packLocation, numPlayers);
                packValid = true;
            } catch (InvalidNumOfCardsException numex){
                System.out.println("You have entered an invalid pack. " + numex.getMessage());
            } catch (NumberFormatException numformat){
                System.out.println("You have entered an invalid pack. The pack must only contain integers");
            } catch (NegativeException ne){
                System.out.println("You have entered an invalid pack. " + ne.getMessage());
            }
        }

        //create the required number of players, player threads and decks
        for (int i = 1; i <= numPlayers; i++) {
            new Player();
            new CardDeck();
        }

        for (int j = 1; j<= numPlayers; j++){
            new TakeTurn(CardDeck.getCardDeck(j), CardDeck.getCardDeck((j % numPlayers) + 1 ), Player.getPlayer(j).hand);
            //Create a new thread, that has the correct target object, and immidiately add it to the array of thread objects
            playerThreadArray.add(new Thread(TakeTurn.getTakeTurn(j), "Player" + j + " thread"));

        }

        if (pack != null){
            pack.deal();
        }
        


    }
}