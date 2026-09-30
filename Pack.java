import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Pack {

    //Pack constructor
    Pack(String packLocation, int numPlayers){
        File packFile = new File(packLocation); //makes a File object that we can read
        Queue<Integer> pack = new LinkedList<>(); //make the LinkedList data structure that the pack will be stored in

        try (Scanner scanner = new Scanner (packFile)) {  //make a Scanner that reads the packFile
            while (scanner.hasNextLine()){
                //validate pack file
                //Conditions: no negative integers, total of 8n cards,  Possible for a player to win

                String stringData = scanner.nextLine(); //read the next line of the packFile

                try{ //validate all the data is int and put in pack
                    int data = Integer.parseInt(stringData); 
                    pack.offer(data);  //puts the next bit of data onto the tail of the linked list 
                } catch (NumberFormatException e){
                    System.out.println("The pack must only contain integers");
                }

            }            
        } catch (FileNotFoundException e) {
            System.out.println("Could not find a file in that location");
        }

        //validate the size of the pack is 8n
        if (pack.size() != 8*numPlayers){
            throw new InvalidNumOfCardsException("There are an invalid number of cards in the deck provided");
        }

    }
    
}
