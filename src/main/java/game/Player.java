package game;

import java.util.ArrayList;

import exceptions.InvalidNumOfPlayersException;

import game.Card;

public class Player {
    private ArrayList<Card> hand = new ArrayList<Card>();
    public int playerID;
    private static int totalPlayers = 0;
    public static ArrayList<Player> playerArray = new ArrayList<Player>();

    public Player() {
        totalPlayers += 1;
        this.playerID = totalPlayers;
        playerArray.add(this);
    }

    public static Player getPlayer(int id) throws InvalidNumOfPlayersException {
        if (id > playerArray.size()) {
            throw new InvalidNumOfPlayersException("You have tried to access an invalid player.");
        }
        return playerArray.get(id - 1);
    }

    public int getID(){
        return this.playerID;
    }

    public static ArrayList<Card> getHand(int id) {
        return Player.getPlayer(id).hand;
    }

    public void addToHand(Card card) {
        hand.add(card);
    }
}