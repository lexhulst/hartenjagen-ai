import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// State consists of the following information:
// - Card that were already played
// - Public knowledge of the suits that the player does not have
// - If the player already has points
// - The player's hand
// - Current trick


// Good idea to use threads
// Need to enable merging of states anyways


public class Main {
    private Player[] players = new Player[4];
    private Map<State,Integer> choiceEvaluator = new HashMap<State,Integer>();

    public static void main(String[] args) {
        Main game = new Main();
        game.play();
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
        Collections.shuffle(deck);
        for(int i = 0; i < 4; i++){
            Card[] deckArray = deck.toArray(new Card[deck.size()]);
            players[i] = new Player(Arrays.copyOfRange(deckArray, i*13, (i+1)*13));
        }
    }

    private int determineStartingPlayer(){
        for(int i = 0; i < 4; i++){
            if(players[i].isStartingPlayer()){
                return i;
            }
        }
        throw new java.lang.RuntimeException("No starting player found");
    }

    private boolean heartsPlayed(boolean[] cardPlayed){
        for(int i = 0; i < 13; i++){
            if(cardPlayed[i]){
                return true;
            }
        }
        return false;
    }

    private void updatePlayedCard(Card card, boolean[] cardPlayed){
        cardPlayed[card.suit.ordinal() * 13 + card.value.ordinal()] = true;
    }

    // Determines which cards can be played
    private List<Card> choices(List<Card> hand, Suit suit, boolean heartsPlayed, boolean opening){
        List<Card> possibleChoices = new ArrayList<Card>();
        
        // If we are opening the trick, we can play any card (except hearts if they are not already broken)
        if(opening){
            if(heartsPlayed){
                return hand;
            }
            for(Card card : hand){
                if(card.suit != Suit.HEARTS){
                    possibleChoices.add(card);
                }
            }
            return possibleChoices;
        }

        // If we can follow suit, we must do so
        for(Card card : hand){
            if(card.suit == suit){
                possibleChoices.add(card);
            }
        }
        if(possibleChoices.isEmpty()){
            return possibleChoices;
        }

        // If we cannot follow suit, we can play any card 
        return hand;
    }

    private void updateHasSuitPublic(Card[] trickCards, int startingPlayer, boolean[][] hasSuitPublic){
        Suit suit = trickCards[0].suit;
        for(int i = 1; i < 4; i++){
            if(trickCards[i].suit != suit){
                hasSuitPublic[(startingPlayer + i) % 4][suit.ordinal()] = false;
            }
        }
    }

    private void playoutGame(int startingPlayer){
        boolean[] cardPlayed = new boolean[52];
        boolean[][] hasSuitPublic = new boolean[4][4];
        boolean[] hasPoints = new boolean[4];

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                hasSuitPublic[i][j] = true;
            }
        }

        // First trick (different as two of clubs must be played)
        Card[] trickCards = new Card[4];
        Card chosenCard = new Card(Suit.CLUBS, Value.TWO);
        players[startingPlayer].removeCard(chosenCard);
        trickCards[0] = chosenCard;
        updatePlayedCard(chosenCard, cardPlayed);
        for(int i = 1; i < 4; i++){

            // Gebleven bij: deze code in een functie zetten en zorgen dat het spel wordt uitgespeeld
            List<Card> hand = players[(startingPlayer + i) % 4].getHand();
            List<Card> possibleChoices = choices(hand, chosenCard.suit, heartsPlayed(cardPlayed), false);
            // Choose card based on state mapping
            for(Card card : possibleChoices){
                State state = new State(cardPlayed, hasSuitPublic, hasPoints, hand, trickCards, card);
                if(!choiceEvaluator.containsKey(state)){
                    chosenCard = card;
                    break;
                }
            }

            // Play the chosen card
            players[(startingPlayer + i) % 4].removeCard(chosenCard);
            trickCards[i] = chosenCard;
            updatePlayedCard(chosenCard, cardPlayed);
            if(chosenCard.suit != trickCards[0].suit){
                hasSuitPublic[(startingPlayer + i) % 4][chosenCard.suit.ordinal()] = false;
            }
        }
        
    }

    private void play(){
        distributeCards();
        // TODO: Add giving cards to other players
        int startingPlayer = determineStartingPlayer();
        playoutGame(startingPlayer);
    }

    private static void printCard(Card card) {
        System.out.println("the " + card.value + " of " + card.suit + "\n");
    }
}