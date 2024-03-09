import java.util.Random;
/* -----------------------------------------------------
   Deck: a deck of cards
   ----------------------------------------------------- */
// https://www.cs.emory.edu/~cheung/Courses/170/Syllabus/10/Progs/Cards/1/DeckOfCards.java
public class Deck
{
    public static final int NCARDS = 52;

    private Card[] deckOfCards;         // Contains all 52 cards
    private int currentCard;            // deal THIS card in deck

    public Deck( )
    {
        deckOfCards = new Card[NCARDS];

        int i = 0;

        for ( int suit = Card.DIAMOND; suit <= Card.SPADE; suit++ )
            for ( int rank = 1; rank <= 13; rank++ )
                deckOfCards[i++] = new Card(suit, rank);

        currentCard = 0;
    }

    /* ---------------------------------
       shuffle(n): shuffle the deck
       --------------------------------- */
    public void shuffle(int n)
    {
        Random r = new Random();

        for (int i = deckOfCards.length - 1; i > currentCard; i--) {
            int j = currentCard + r.nextInt(i + 1 - currentCard);

            Card temp = deckOfCards[i];
            deckOfCards[i] = deckOfCards[j];
            deckOfCards[j] = temp;
        }
    }

    /*
     * shuffling a deck of cards by removing the first n cards
     * 
     */
    public void remove(Card[] cardsToRemove) {
        int[] indicesToRemove = getIndices(cardsToRemove);
        for (int i = 0; i < cardsToRemove.length; i++) {
            Card temp = deckOfCards[i];
            deckOfCards[i] = deckOfCards[indicesToRemove[i]];
            deckOfCards[indicesToRemove[i]] = temp;
            currentCard++;
        }
    }

    public int[] getIndices(Card[] cards) {
        int[] indicesOfCards = new int[cards.length];
        for (int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            indicesOfCards[i] = 13 * (card.suit() - 1) + card.rank() - 2; 
        }
        return indicesOfCards;
    }


    /* -------------------------------------------
       deal(): deal deckOfCards[currentCard] out
       ------------------------------------------- */
    public Card deal()
    {
        if ( currentCard < NCARDS )
        {
            return ( deckOfCards[currentCard++] );
        }
        else
        {
            System.out.println("Out of cards error");
            return ( null );  // Error;
        }
    }

    public String toString()
    {
        String s = "";
        int k;

        k = 0;
        for ( int i = 0; i < 4; i++ )
        {
            for ( int j = 1; j <= 13; j++ )
                s += (deckOfCards[k++] + " ");

            s += "\n";
        }
        return ( s );
    }
}
