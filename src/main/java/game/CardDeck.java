package game;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

import exceptions.InvalidNumOfDecksException;
import game.Card;

public class CardDeck {
    public volatile ArrayList<Card> deck = new ArrayList<Card>();  //needs to be volatile because threads need to see when other threads update the deck
    private int deckID;
    private static int totalDecks = 0;
    public static ArrayList<CardDeck> cardDeckArray = new ArrayList<CardDeck>();


    public CardDeck() {
        totalDecks += 1;
        this.deckID = totalDecks;
        cardDeckArray.add(this);
    }

    public static CardDeck getCardDeck(int id) throws InvalidNumOfDecksException {
        if (id > cardDeckArray.size() || id < 0) {
            throw new InvalidNumOfDecksException("You have tried to access an invalid deck.");
        }
        return cardDeckArray.get(id - 1);
    }

    //get how many cards are in the deck
    public int getSize(){
        return deck.size();
    }

    public String getName(){
        return "deck" + this.deckID;
    }

    public String getNameSpace(){ //for returning the name with a space after it for outputting purposes
        return "deck " + this.deckID;
    }

    public void addToDeck(Card card) {
        deck.add(card); //adds card to the bottom (end) of the deck
    }

    public Card draw() throws IndexOutOfBoundsException{
        if (deck.size() < 1){
            throw new IndexOutOfBoundsException("The deck doesn't have any cards to draw");
        } 
        Card drawnCard = deck.get(0);  //read the card value at the top of the deck
        deck.remove(0); //removed the card from the deck it has been drawn from
        return drawnCard;
    }

    public static void printDecks() {
        for (CardDeck cardDeckObj:cardDeckArray) {
            try {
                FileWriter deckWriter = new FileWriter(cardDeckObj.getName() + "_output.txt");
                String message = cardDeckObj.getName() + " contents: ";
                for (Card card:cardDeckObj.deck) {
                    message = message + card.getValue() + " "; 
                }
                deckWriter.write(message);
                deckWriter.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}