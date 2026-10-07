import java.util.ArrayList;

public class TakeTurn implements Runnable {

    private CardDeck pickupDeck;
    private CardDeck discardDeck;
    public static ArrayList<TakeTurn> takeTurnArray = new ArrayList<TakeTurn>();

    public volatile boolean gamewon = false;

    public TakeTurn(CardDeck pickupDeck, CardDeck discardDeck) {
        this.pickupDeck = pickupDeck;
        this.discardDeck = discardDeck;

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
        System.out.println(Thread.currentThread().getName() + " has run");
    }

    //public boolean checkWin() {
        
    //}
}
