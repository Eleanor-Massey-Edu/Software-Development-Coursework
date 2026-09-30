import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Pack {

    //Pack constructor
    Pack(String packLocation){
        File packFile = new File(packLocation); //makes a File object that we can read
        Queue<Integer> pack = new LinkedList<>(); //make the LinkedList data structure that the pack will be stored in

        try (Scanner scanner = new Scanner (packFile)) {
            while (scanner.hasNextLine()){
                String stringData = scanner.nextLine();
                int data = Integer.parseInt(stringData);
                //append the data into a FIFO linked list structure
                pack.offer(data);  //puts the next bit of data onto the tail of the linked list

            }            
        } catch (FileNotFoundException e) {
            System.out.println("Could not find a file in that location");
        }
        System.out.println(pack);

    }
    
}
