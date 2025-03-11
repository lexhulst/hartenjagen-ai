public class Card implements Comparable<Card> {
    public Suit suit;
    public Value value;

    
    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }

    @Override
    public int compareTo(Card card){
        if(this.suit == card.suit){
            return this.value.ordinal() - card.value.ordinal();
        }
        return this.suit.ordinal() - card.suit.ordinal();
    }
}