import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    private Player[] players;
    public static void main(String[] args) {
        Main game = new Main();
        game.distributeCards();
    }

    private void distributeCards(){
        // Generate the deck of cards
        List<Card> deck = new ArrayList<Card>();
        for(Suit suit : Suit.values()){
            for(Value value : Value.values()){
                deck.add(new Card(suit, value));
            }
        }
        
        // Shuffle the deck and give each player a hand
        Player[] players = new Player[4];
        Collections.shuffle(deck);
        for(int i = 0; i < 4; i++){
            Card[] deckArray = deck.toArray(new Card[deck.size()]);
            players[i] = new Player(Arrays.copyOfRange(deckArray, i*13, (i+1)*13));
        }

        this.players = players;
    }

    private static void printCard(Card card) {
        System.out.println("the " + card.value + " of " + card.suit + "\n");
    }
}