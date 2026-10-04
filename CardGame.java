import java.util.Scanner;
import java.util.ArrayList;

public class CardGame {
    public static void main(String[] args) {
        setUp();
    }

    public static void setUp() {
        // Make scanner object (input)
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the number of players: ");
        String stringNumPlayers = scanner.nextLine();
        int numPlayers = Integer.parseInt(stringNumPlayers); // change to int

        for (int i = 1; i <= numPlayers; i++) {
            new Player();
        }

        System.out.println("Please enter location of pack to load: ");
        String packLocation = scanner.nextLine();

        try{
            Pack pack = new Pack(packLocation, numPlayers);
            System.out.println(pack.packQueue);
            pack.deal();
            System.out.println(Player.getPlayer(1).hand);
            System.out.println(Player.getPlayer(2).hand);
        } catch (InvalidNumOfCardsException numex){
            System.out.println(numex.getMessage());
        } catch (NumberFormatException numformat){
            System.out.println(numformat.getMessage());
        }

    }
}