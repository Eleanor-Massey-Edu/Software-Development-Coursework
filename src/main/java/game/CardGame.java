package game;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import exceptions.NegativeException;
import exceptions.InvalidNumOfCardsException;
import game.Card;


public class CardGame {
    //array of all the player threads
    public static ArrayList<Thread> playerThreadArray = new ArrayList<Thread>();
    public static void main(String[] args) {
        // Make scanner object (input)
        Scanner scanner = new Scanner(System.in);
        //run the setup before the players start playing the game
        setUp(scanner);
        //start the threads playing the game
        for (Thread thread:playerThreadArray){
            thread.start();
        }
    }

    public static void setUp(Scanner scanner) { //pass in the scanner object to facilitate testing hard-coded inputs
        
        
        boolean packValid = false;
        Pack pack = null;

        //Ask user the number of players
        System.out.println("Please enter the number of players: ");
        String stringNumPlayers = scanner.nextLine();
        int numPlayers = Integer.parseInt(stringNumPlayers); // change to int

        //Ask user for the pack to use for the game
        while (!packValid){
            System.out.println("Please enter location of pack to load: ");
            String packLocation = scanner.nextLine();


            try{ //try to instantiate the pack and if an error has been thrown, deal with it in the catch
                pack = new Pack(packLocation, numPlayers);
                packValid = true;
                System.out.println("packValid has been set to true");
            } catch (InvalidNumOfCardsException numex){
                System.out.println("You have entered an invalid pack. " + numex.getMessage());
            } catch (NumberFormatException numformat){
                System.out.println("You have entered an invalid pack. The pack must only contain integers");
            } catch (NegativeException ne){
                System.out.println("You have entered an invalid pack. " + ne.getMessage());
            } catch (FileNotFoundException fileex){
                System.out.println("Cannot find a file at that location");
            }
        }

        //create the required number of players, player threads and decks
        for (int i = 1; i <= numPlayers; i++) {
            new Player();
            new CardDeck();
        }

        //make the TakeTurn objects that are going to be the tasks for the player threads
        //different TakeTurn object for each player because they are taking turns on different decks and have different hands
        for (int j = 1; j<= numPlayers; j++){
            //the target only needs the decks that the player given that target will need
            new TakeTurn(CardDeck.getCardDeck(j), CardDeck.getCardDeck((j % numPlayers) + 1 ), Player.getHand(j), j);
            //Create a new thread, that has the correct target object, and immidiately add it to the array of thread objects
            playerThreadArray.add(new Thread(TakeTurn.getTakeTurn(j), Integer.toString(j)));
        }

        //if a valid pack file has been given, the deal it out
        if (pack != null){
            pack.deal();
        }
        
        //write the initial hands to the output files once the pack has been dealt
        for (int k = 1; k <= numPlayers; k++) { // initiate initial hand line in playerX_output.txt
            try {
                FileWriter playerWriter = new FileWriter("player" + k + "_output.txt");
                ArrayList<Card> currentHand = Player.getHand(k);
                String message = "player " + k + " initial hand " + currentHand.get(0).getValue() + " " +
                + currentHand.get(1).getValue() + " " + currentHand.get(2).getValue() + " " + currentHand.get(3).getValue() + "\n";
                playerWriter.write(message);
                playerWriter.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}