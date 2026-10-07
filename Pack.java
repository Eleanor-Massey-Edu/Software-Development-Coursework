import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Pack {

    private ArrayList<Integer> packQueue = new ArrayList<>(); //make the LinkedList data structure that the pack will be stored in


    //Pack constructor
    Pack(String packLocation, int numPlayers) throws NegativeException{
        int count = 0;
        File packFile = new File(packLocation); //makes a File object that we can read

        try (Scanner scanner = new Scanner (packFile)) {  //make a Scanner that reads the packFile
            while (scanner.hasNextLine()){

                String stringData = scanner.nextLine(); //read the next line of the packFile

                //validate all the data is int and is positive and put in pack
                count += 1;
                int data = Integer.parseInt(stringData); 
                if (data < 0) {
                    throw new NegativeException("The data must be non-negative");
                }
                packQueue.add(data);  //puts the next bit of data onto the tail of the linked list 

            }
    
            //after the while loop, check the counter to see how many cards are in the pack
            if (count != 8*numPlayers){
                throw new InvalidNumOfCardsException("There are an invalid number of cards in the deck provided");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Could not find a file in that location");
        }
        }
    
    public synchronized void deal() {
        for (int i = 1; i <= 4; i++) { // 4 cards total for each person's hand
            for (Player player: Player.playerArray) { // Do for each player
                int cardToAdd = packQueue.get(0); // Retrieves and removes top card in the ArrayList pack
                player.addToHand(cardToAdd); // Add single card to player hand
                packQueue.remove(0);
            }
        }

        for (int i = 1; i <= 4; i++) { // 4 cards total for each person's deck
            for (CardDeck deck: CardDeck.cardDeckArray) { // Do for each deck
                if (!(CardDeck.cardDeckArray.isEmpty())) { // If cardDeckArray is not empty, keep dealing cards
                    int cardToAdd = packQueue.get(0); // Retrieves and removes top card in the ArrayList pack
                    deck.addToDeck(cardToAdd); // Add single card to player deck
                    packQueue.remove(0);
                }
                
            }
        } 
        
    }
}