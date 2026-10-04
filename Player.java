import java.util.ArrayList;

public class Player {
    public ArrayList<Integer> hand = new ArrayList<Integer>();
    private int playerID;
    private static int totalPlayers = 0;
    public static ArrayList<Player> playerArray = new ArrayList<Player>();

    Player() {
        totalPlayers += 1;
        this.playerID = totalPlayers;

        playerArray.add(this);
        System.out.println(playerArray);
    }

    static Player getPlayer(int id) throws InvalidNumOfPlayersException {
        if (id > playerArray.size()) {
            throw new InvalidNumOfPlayersException("You have tried to access an invalid player.");
        }

        return playerArray.get(id - 1);
    }

    public void addToHand(int card) {
        hand.add(card);
    }
}