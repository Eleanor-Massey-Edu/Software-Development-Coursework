package game;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import exceptions.InvalidNumOfTakeTurnObjectsException;
import game.Card;

public class TakeTurn implements Runnable {

    public static volatile boolean gameFinished = false;
    public static String winningThread = null;
    public static volatile ArrayList<String> winningThreads = new ArrayList<String>();

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
        
        winningThread = checkWin();
        
        //System.out.println("gameFinished is already: " + TakeTurn.gameFinished);
        CardDeck firstDeck;
        CardDeck secondDeck;
        Integer finalPlayer = Player.playerArray.size();
        String stringFinalPlayer = finalPlayer.toString();
        //System.out.println(Thread.currentThread().getName());
        //System.out.println("Thinks the final player is: " + "Player " + finalPlayer);
        if (Thread.currentThread().getName().equals(stringFinalPlayer)){  //rearrange the order of getting the locks if it is the last player, to avoid circular pattern deadlock
            firstDeck = this.discardDeck;
            secondDeck = this.pickupDeck;
            //System.out.println("Rearranging order for last player so that the first deck is: " + firstDeck.getName() + " and the second deck is: " + secondDeck.getName());
        } else {
            firstDeck = this.pickupDeck;
            secondDeck = this.discardDeck;
        }

        //System.out.println("Gotten to the while loop but the game finished is already: " + TakeTurn.gameFinished);
        while (!TakeTurn.gameFinished){
            if (pickupDeck.getSize() != 0){  //checks if there are cards left in the pickup deck to pickup from
                //System.out.println(Thread.currentThread().getName() + " trying to get past 1st lock on " + firstDeck.getName());
                synchronized(firstDeck){
                    //System.out.println(Thread.currentThread().getName() + " has gotten past the first lock on " + firstDeck.getName());
                    //System.out.println(Thread.currentThread().getName() + " trying to get past 2nd lock on " + secondDeck.getName());
                    synchronized(secondDeck){
                        //System.out.println(Thread.currentThread().getName() + " has both the locks on " + firstDeck.getName() + " and " + secondDeck.getName());  
                        try{
                            Card newCard = this.pickupDeck.draw();
                            this.chooseAndDiscard(newCard);
                            System.out.println("player " + Thread.currentThread().getName() + " has chosen and discarded");
                        } catch (IndexOutOfBoundsException ex){
                            //if there is not a card left to draw from their pickup deck, do nothing and don't try to chooseAndDiscard()
                        }
                        //thread has taken their turn
                        //System.out.println(Thread.currentThread().getName() + " has chosen and discarded.");
                        winningThread = checkWin();
                        if (winningThread != null){ //if there has been a winning thread, add it to the list of threads that have won this cycle of the while loop
                            winningThreads.add(winningThread);
                        }
                        }
                    }
                }
            
            try{
                Thread.sleep(100);
                //System.out.println(Thread.currentThread().getName() + " has slept for 100");
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            //System.out.println(Thread.currentThread().getName() + " has yeilded their locks");
            }
        //doFinalOutputs
        System.out.println(winningThreads + " from " + Thread.currentThread().getName());
        if (winningThreads != null && Thread.currentThread().getName() == winningThreads.get(0)) { //only allow one thread to run writeWinOutputs, and the winning thread is guaranteed to pass this if statement
            System.out.println(Thread.currentThread().getName() + " is writing the win outputs, list is " + winningThreads);
            System.out.println("player " + winningThreads.get(0) + " wins");
            writeWinOutputs(winningThreads.get(0)); //choose the first thread that won to be the winner that's written out
            //System.out.println(TakeTurn.gameFinished);
        }
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
                this.writePlayerActions(newCard, cardAtRandomPosition);
                //System.out.println("Outputting to file");
            }
        }
        
    }

    public String checkWin() {
        System.out.println("Check win has been called");
        int firstCardValue = hand.get(0).getValue(); // take the first card in hand to start check if all are the same value
        int counter = 0;
        for (Card card:hand){
            if (card.getValue() == firstCardValue){ // if the sequential cards are equal in value, increase the counter
                counter += 1;
            }
        }
        if (counter == 4){ //if the hand that you have just changes now has 4 of the same value then they have won and the game is finished
            TakeTurn.gameFinished = true;
            //System.out.println("player " + Thread.currentThread().getName() + " wins");
            CardDeck.printDecks();
            //this.writeWinOutputs();
            return Thread.currentThread().getName();
        } else {
            return null;
        }
    }

    public void writePlayerActions(Card newCard, Card discardedCard) {
        try {
            FileWriter actionWriter = new FileWriter("player" + Thread.currentThread().getName() + "_output.txt", true); // open in append mode
            String drawMessage = "player " + Thread.currentThread().getName() + " draws a " + newCard.getValue() + " from " + this.pickupDeck.getNameSpace() + "\n";
            actionWriter.write(drawMessage);
            String discardMessage = "player " + Thread.currentThread().getName() + " discards a " + discardedCard.getValue() + " to " + this.discardDeck.getNameSpace() + "\n";
            actionWriter.write(discardMessage);
            String currentMessage = "player " + Thread.currentThread().getName() + " current hand is ";
            for (Card card:hand) {
                currentMessage = currentMessage + card.getValue() + " ";
            }
            currentMessage = currentMessage + "\n";
            actionWriter.write(currentMessage);
            actionWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void writeWinOutputs(String winnerID) { 
        Integer winningThread = Integer.parseInt(winnerID);
        try {
            for (int k = 1; k <= takeTurnArray.size(); k++) { //go round all the threads and write appropriate outputs
                if (k == winningThread) { //write the winning thread's output file
                    FileWriter winWriter = new FileWriter("player" + k + "_output.txt", true);
                    String winMessage = "player " + k + " wins" +
                                        "\n" + "player " + k + " exits" + "\n" +
                                        "player " + k + " final hand: ";
                    for (Card card:Player.getHand(k)) {
                        winMessage = winMessage + card.getValue() + " ";
                    }
                    winWriter.write(winMessage);
                    winWriter.close();
                } else {  //write one of the loser thread's output file
                    FileWriter loseWriter = new FileWriter("player" + k + "_output.txt", true);
                    String loseMessage = "player " + winningThread + " has informed player " + k 
                    + " that player " + winningThread + " has won" + "\n" + "player " + k + " exits" + "\n" +
                    "player " + k + " hand: ";
                    for (Card card:Player.getHand(k)) {
                        loseMessage = loseMessage + card.getValue() + " ";
                    }
                    loseWriter.write(loseMessage);
                    loseWriter.close();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
