import java.util.ArrayList;
import java.util.Random;

public class TakeTurn implements Runnable {

    public static volatile boolean gameFinished = false;

    private CardDeck pickupDeck;
    private CardDeck discardDeck;
    private ArrayList<Card> hand;
    private int preferredNumber;
    public static ArrayList<TakeTurn> takeTurnArray = new ArrayList<TakeTurn>();

    public volatile boolean gamewon = false;

    public TakeTurn(CardDeck pickupDeck, CardDeck discardDeck, ArrayList<Card> hand, int playerID) {
        this.pickupDeck = pickupDeck;
        this.discardDeck = discardDeck;
        this.hand = hand;
        this.preferredNumber = playerID;

        //add this instance to the array of all TakeTurn instances
        takeTurnArray.add(this);
    }

    public static TakeTurn getTakeTurn(int id) throws InvalidNumOfTakeTurnObjectsException {
        if (id > takeTurnArray.size()) {
            throw new InvalidNumOfTakeTurnObjectsException("You have tried to access an invalid TakeTurn object.");
        }

        return takeTurnArray.get(id - 1);
    }


    @Override 
    public void run() {
        checkWin();
        CardDeck firstDeck;
        CardDeck secondDeck;
        int finalPlayer = Player.playerArray.size();
        //System.out.println(Thread.currentThread().getName());
        //System.out.println("Thinks the final player is: " + "Player " + finalPlayer);
        if (Thread.currentThread().getName().equals( "Player " + finalPlayer)){  //rearrange the order get the locks if it is the last player
            firstDeck = this.discardDeck;
            secondDeck = this.pickupDeck;
            System.out.println("Rearranging order for last player so that the first deck is: " + firstDeck.getName() + " and the second deck is: " + secondDeck.getName());
        } else {
            firstDeck = this.pickupDeck;
            secondDeck = this.discardDeck;
        }

        while (!gameFinished){
            if (pickupDeck.getSize() != 0){  //checks if there are cards left in the pickup deck to pickup from
                System.out.println(Thread.currentThread().getName() + " trying to get past 1st lock on " + firstDeck.getName());
                synchronized(firstDeck){
                    System.out.println(Thread.currentThread().getName() + " has gotten past the first lock on " + firstDeck.getName());
                    System.out.println(Thread.currentThread().getName() + " trying to get past 2nd lock on " + secondDeck.getName());
                    synchronized(secondDeck){
                        System.out.println(Thread.currentThread().getName() + " has both the locks on " + firstDeck.getName() + " and " + secondDeck.getName());  
                        Card newCard = this.pickupDeck.draw();
                        this.chooseAndDiscard(newCard);
                        checkWin();
                    }
                }
            }
            try{
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " has slept for 100");
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(Thread.currentThread().getName() + " has yeilded their locks");
        }
        
        //doFinalOutputs
    }

    public void chooseAndDiscard(Card newCard){
        //logic to choose a card to discard
        boolean foundCardToDiscard = false;
        hand.add(newCard); //add the new card to the 
        Random r = new Random();
        while (!foundCardToDiscard){
            int randomPosition = r.nextInt(hand.size());
            Card cardAtRandomPosition = hand.get(randomPosition);
            if (cardAtRandomPosition.getValue() != this.preferredNumber) {  //allowed to discard that card
                hand.remove(cardAtRandomPosition); //removes the card that was chosen randomly
                foundCardToDiscard = true;

                //add the discarded card to the discard deck
                this.discardDeck.addToDeck(cardAtRandomPosition);
            }
        }
        
        
    }

    public void checkWin() {
        int firstCardValue = hand.get(0).getValue();
        int counter = 0;
        for (Card card:hand){
            if (card.getValue() == firstCardValue){
                counter += 1;
            }
        }
        if (counter == 4){ //if the hand that you have just changes now has 4 of the same value then they have won and the game is finished
            gameFinished = true;
            System.out.println(Thread.currentThread().getName() + " wins");
        } else {
            gameFinished = false;
        }
    }
}
