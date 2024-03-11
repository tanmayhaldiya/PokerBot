import java.util.Arrays;
import java.util.ArrayList;
public class SimulateGame {
    public static double[] simulateGame(Card[] playerA, Card[] playerB, Card[] community){
        TexasPlayer[] players = new TexasPlayer[2];
        players[0] = new TexasPlayer(playerA[0], playerA[1]);
        players[1] = new TexasPlayer(playerB[0], playerB[1]);

        ArrayList<Card> comm = new ArrayList<>();
        for (Card c : community) {
            comm.add(c);
        }

        Card[] stored = new Card[4 + community.length];
        stored[0] = playerA[0];
        stored[1] = playerA[1];
        stored[2] = playerB[0];
        stored[3] = playerB[1];

        // System.out.println(Arrays.toString(stored));

        for (int i = 0; i < comm.size(); i++){
            stored[4 + i] = comm.get(i);
        }

        Deck d = new Deck();
        d.remove(stored);
        double res[] = new double[3];
        // res[0] = P(A win)
        // res[1] = P(B win)
        // res[2] = P(tie)

        int iterations = 1000000;
        for (int i = 0; i < iterations; i++) {
            d.shuffle();
            ArrayList<Card> tempComm = (ArrayList<Card>) comm.clone();

            for (int j = 0; j < (5 - tempComm.size()); j++) {
                tempComm.add(d.deal());
            }

            players[0].updateHand(tempComm);
            players[1].updateHand(tempComm);

            int valA = players[0].getBestHandValue();
            int valB = players[1].getBestHandValue();
            if (valA > valB) {
                res[0] += ((double) 1) / iterations;
            } else if (valB > valA) {
                res[1] += ((double) 1) / iterations;
            } else {
                res[2] += ((double) 1) / iterations;
            }

            d.setCurrentCard(stored.length);
        }
        return res;
    }

    public static void main (String[] args) {
        Card[] myHand = new Card[2];
        myHand[0] = new Card("As");
        myHand[1] = new Card("Kc");
        System.out.println(Arrays.toString(myHand));

        Card[] oppHand = new Card[2];
        oppHand[0] = new Card("2s");
        oppHand[1] = new Card("2h");
        System.out.println(Arrays.toString(oppHand));

        Card[] emptyComm = new Card[0];
        double[] res = simulateGame(myHand, oppHand, emptyComm);
        System.out.println(Arrays.toString(res));
    }


    public void simulateGame () {
        Game simGame = new Game(2);
        
    }
}
