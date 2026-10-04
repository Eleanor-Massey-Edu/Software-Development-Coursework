import java.util.ArrayList;

public class CardDeck {
    private ArrayList<Integer> deck = new ArrayList<Integer>();
    private int deckID;
    private static int totalDecks = 0;
    public static ArrayList<CardDeck> cardDeckArray = new ArrayList<CardDeck>();


    CardDeck() {
        totalDecks += 1;
        this.deckID = totalDecks;

        cardDeckArray.add(this);
    
    }

    static CardDeck getCardDeck(int id) throws InvalidNumOfDecksException {
        if (id > cardDeckArray.size()) {
            throw new InvalidNumOfDecksException("You have tried to access an invalid deck.");
        }

        return cardDeckArray.get(id - 1);
    }

    public void addToDeck(int card) {
        deck.add(card);
    }
}