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
        String packLocation = scanner.nextLine();

        try{
            Pack pack = new Pack(packLocation, numPlayers);
        } catch (InvalidNumOfCardsException numex){
            System.out.println(numex.getMessage());
        } catch (NumberFormatException numformat){
            System.out.println(numformat.getMessage());
        }
    }
}