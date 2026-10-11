import java.util.ArrayList;

public class CardDeck {
    private volatile ArrayList<Card> deck = new ArrayList<Card>();  //needs to be volatile because threads need to see when other threads update the deck
    private int deckID;
    private static int totalDecks = 0;
    public static ArrayList<CardDeck> cardDeckArray = new ArrayList<CardDeck>();


    CardDeck() {
        totalDecks += 1;
        this.deckID = totalDecks;

        cardDeckArray.add(this);
        System.out.println("made a new deck");
    
    }

    public static CardDeck getCardDeck(int id) throws InvalidNumOfDecksException {
        if (id > cardDeckArray.size()) {
            throw new InvalidNumOfDecksException("You have tried to access an invalid deck.");
        }

        return cardDeckArray.get(id - 1);
    }

    public int getSize(){
        return deck.size();
    }

    public String getName(){
        return "Deck " + this.deckID;
    }

    public void addToDeck(Card card) {
        deck.add(card); //adds card to the bottom (end) of the deck
    }

    public Card draw(){
        Card drawnCard = deck.get(0);  //read the card value at the top of the deck
        deck.remove(0); //removed the card from the deck it has been drawn from
        return drawnCard;
    }
}