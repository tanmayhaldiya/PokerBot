import java.util.*;
public class Player_Texas {
    private Card[] holeCards = new Card[2];
    private int bestHandValue = 0;
    private Card[] bestHand = new Card[5];

    public Player_Texas () {
        // im not gonna do anything here lmao
    }

    public Player_Texas (Card card1, Card card2) {
        holeCards[0] = card1;
        holeCards[1] = card2;
    }

    // so that we can deal one card at a time
    private void setCards(Card card, int cardNum) {
        holeCards[cardNum] = card;
    }

    // because "bestHand = hand" wasn't working...
    private void updateHand(Card[] hand) {
        for (int i = 0; i < 5; i++) {
            bestHand[i] = hand[i];
        }
    }

    public void community(ArrayList<Card> comm) {
        if (comm.size() < 3 || comm.size() > 5) {
            System.out.println("Faulty community card count: " + comm.size());
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
                    updateHand(hand);
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
        d.shuffle(1000);

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