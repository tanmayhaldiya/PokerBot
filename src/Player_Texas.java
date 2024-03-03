import java.util.IllegalArgumentException;

public class Player_Texas {
    private Card[] holeCards;
    private int bestHandValue;
    private Card[] bestHand;

    public Player_Texas () {
        bestHand = new Card[5];
        bestHandValue = 0;
        holeCards = new Card[2];
    }

    public Player_Texas (Card card1, Card card2) {
        bestHand = new Card[5];
        bestHandValue = 0;
        holeCards = new Card[2];
        holeCards[0] = card1;
        holeCards[1] = card2;
    }

    /**
     * dealing one card at a time
     * @param card - the card that is dealt
     * @param cardNum - n where card is the nth card dealt
     */
    private void setCards(Card card, int cardNum) {
        holeCards[cardNum] = card;
    }

    /* because "bestHand = hand" wasn't working...
    private void updateHand(Card[] hand) {
        for (int i = 0; i < 5; i++) {
            bestHand[i] = hand[i];
        }
    }*/

    /**
     * determines the best possible hand the current player has given the current community cards
     * @param comm - the dealer's cards
     * @throws java.lang.IllegalArgumentException - if comm's size is less than three or greater than 5
     */
    public void community(ArrayList<Card> comm) {
        if (comm.size() < 3 || comm.size() > 5) {
           throw new IllegalArgumentException("Faulty community card count: " + comm.size());
        }

        Card[] available = new Card[2 + comm.size()];

        available[0] = holeCards[0];
        available[1] = holeCards[1];

        for (int i = 2; i < available.length; i++) {
            available[i] = comm.get(i - 2);
        }

        Card[] hand = new Card[5];
        for (int mask = 0; mask < (1 << available.length); mask++) {
            if (Integer.bitCount(mask) == 5) {
                int index = 0;
                for (int i = 0; i < available.length; i++) {
                    if ((mask & (1 << i)) != 0) {
                        hand[index++] = available[i];
                    }
                }
                int curVal = FiveCardPoker.valueHand(hand);

                /*
                System.out.println(Arrays.toString(hand));
                System.out.println(curVal);
                System.out.println(FiveCardPoker.WON_BY[curVal / 1000000]);
                System.out.println();
                 */

                if (curVal > bestHandValue) {
                    // System.out.println(Arrays.toString(hand));
                    bestHandValue = curVal;
                    // System.out.println(Arrays.toString(bestHand));
                    bestHand = hand;
                    // System.out.println(Arrays.toString(bestHand));
                    // System.out.println(curVal);
                    // System.out.println();
                }
            }
        }
    }

    public String toString() {
        return holeCards[0].toString() + " " + holeCards[1].toString();
    }

    public static void main (String[] args) {
        Deck d = new Deck();
        d.shuffle();

        Player_Texas p = new Player_Texas(d.deal(), d.deal());

        System.out.println(p);

        ArrayList<Card> comm = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Card c = d.deal();
            System.out.print(c + " ");
            comm.add(c);
        }
        System.out.println('\n');

        p.community(comm);

        System.out.println("Winner!!");
        System.out.println(Arrays.toString(p.bestHand));
        System.out.println(p.bestHandValue);
        System.out.println(FiveCardPoker.WON_BY[p.bestHandValue / 1000000]);

    }
}