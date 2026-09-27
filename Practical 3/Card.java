
import java.util.Objects;

public class Card
{
    private String rank;
    private String suit;

    // Constructor
    public Card(String rank, String suit)
    {
        this.rank = rank;
        this.suit = suit;
    }

    // Display card
    @Override
    public String toString()
    {
        return rank + " of " + suit;
    }

    // Compare two cards
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Card c = (Card) obj;

        return rank.equals(c.rank) && suit.equals(c.suit);
    }

    // Generate hash code
    @Override
    public int hashCode()
    {
        return Objects.hash(rank, suit);
    }
}
