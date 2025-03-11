
import java.util.List;

// State consists of the following information:
// - Card that were already played
// - Public knowledge of the suits that the player does not have
// - If the player already has points
// - The player's hand
// - Current trick

public class State{
    private final boolean[] cardPlayed;
    private final boolean[][] hasSuitPublic;
    private final boolean[] hasPoints;
    private final List<Card> currentHand;
    private final Card[] currentTrick;
    private final Card choice;

    public State(boolean[] cardPlayed, boolean[][] hasSuitPublic, boolean[] hasPoints, List<Card> currentHand, Card[] currentTrick, Card choice) {
        this.cardPlayed = cardPlayed;
        this.hasSuitPublic = hasSuitPublic;
        this.hasPoints = hasPoints;
        this.currentHand = currentHand;
        this.currentTrick = currentTrick;
        this.choice = choice;
    }

}