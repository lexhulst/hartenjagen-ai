
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Always make sure that the Lists are sorted as this decreases the amount of possible states

public class Player{
    private List<Card> hand = new ArrayList<Card>(); // The player's hand
    private List<Card> obtainedCards = new ArrayList<Card>(); // The card obtained by the player via the tricks
    private boolean[] hasSuitPublic = new boolean[4]; // Suits which the player does not have and are public information


    public Player(Card[] hand){
        this.hand = Arrays.asList(hand);
        Collections.sort(this.hand);

        this.hasSuitPublic[Suit.HEARTS.ordinal()] = true;
        this.hasSuitPublic[Suit.DIAMONDS.ordinal()] = true;
        this.hasSuitPublic[Suit.CLUBS.ordinal()] = true;
        this.hasSuitPublic[Suit.SPADES.ordinal()] = true;
    }

    public void removeCard(Card card){
        hand.remove(card);
    }

    public void addTrick(Card[] trick){
        for(Card card : trick){
            obtainedCards.add(card);
        }
        Collections.sort(obtainedCards);
    }

    public int getScore(){
        int score = 0;
        for(Card card : obtainedCards){
            if(card.suit == Suit.HEARTS){
                score++;
            } else if(card.equals(new Card(Suit.SPADES, Value.QUEEN))){
                score += 13;
            } else if(card.equals(new Card(Suit.SPADES, Value.JACK))){
                score += 6;
            }
        }
        return score;
    }

    public List<Card> getHand(){
        return hand;
    }

    public boolean isStartingPlayer(){
        return hand.contains(new Card(Suit.CLUBS, Value.TWO));
    }
}