/* -----------------------------------------------------------
   Encoding:

        Suit: 4 = Spade
              3 = Heart
              2 = Club
              1 = Diamond

        Rank:  A = 1
               2 = 2
               ...
               J = 11
               Q = 12
               K = 13

   Card:

         byte cardSuit;                -- contain 1, 2, 3, or 4
         byte cardRank;                -- contain 2, 3, ... 13, 14
   ----------------------------------------------------------- */
// https://www.cs.emory.edu/~cheung/Courses/170/Syllabus/10/Progs/Cards/1/Card.java

public class Card
{
    public static final int SPADE   = 4;
    public static final int HEART   = 3;
    public static final int CLUB    = 2;
    public static final int DIAMOND = 1;

    private static final String[] Suit = { "*", "d", "c", "h", "s"};  // "*" is so that we start at index 1
    private static final String[] Rank = { "*", "*", "2", "3", "4",  // "*" "*" is so that we start at index 2
            "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};

    private byte cardSuit;
    private byte cardRank;

    public Card(int suit, int rank )
    {
        if ( rank == 1 )
            cardRank = 14;     // Give Ace the rank 14
        else
            cardRank = (byte) rank;

        cardSuit = (byte) suit;
    }

    public Card(String cardStr) 
    {
        char rank = cardStr.charAt(0);
        char suit = cardStr.charAt(1);
        if (rank >= 50 && rank <= 57) {
            cardRank = (byte) (rank - 48);
        } else if (rank == 'T') {
            cardRank = 10;
        } else if (rank == 'J') {
            cardRank = 11;
        } else if (rank == 'Q') {
            cardRank = 12;
        } else if (rank == 'K') {
            cardRank = 13;
        } else if (rank == 'A') {
            cardRank = 14;
        } else {
            System.out.println("Invalid rank input");
        }
        if (suit == 'd' || suit == 'D') {
            cardSuit = 1;
        } else if (suit == 'c' || suit == 'C') {
            cardSuit = 2;
        } else if (suit == 'h' || suit == 'H') {
            cardSuit = 3;
        } else if (suit == 's' || suit == 'S') {
            cardSuit = 4;
        } else {
            System.out.println("Invalid suit input");
        }
    }

    public int suit()
    {
        return ( cardSuit );         // This is a shorthand for:
        //   this.cardSuit
    }

    public String suitStr()
    {
        return( Suit[ cardSuit ] );  // This is a shorthand for:
        //   this.Suit[ this.cardSuit ]
    }

    public int rank()
    {
        return ( cardRank );
    }

    public String rankStr()
    {
        return ( Rank[ cardRank ] );
    }


    public String toString()
    {
        return ( Rank[ cardRank ] + Suit[ cardSuit ] );
    }
}