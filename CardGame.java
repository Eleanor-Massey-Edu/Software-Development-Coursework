import java.util.Scanner;

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


        System.out.println("Please enter location of pack to load: ");
        String stringPackLocation = scanner.nextLine();
    }
}