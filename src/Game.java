import java.util.*;

/**
 * Simulates a game with a specific number of players.
 *
 * As of now, folding/checking/calling/betting is not implemented.
 */
public class Game {
    int numPlayers;
    TexasPlayer[] players;
    Deck deck;
    List<Card> community;

    public Game (int n) {
        numPlayers = n;
        players = new TexasPlayer[numPlayers];
        deck = new Deck();
        community = new ArrayList<>();
    }



    /**
     * ability to determine hand value for just 4 cards ?
     */
    public void preflop() {
        // deal every player two cards
        for (int i = 0; i < numPlayers; i++) {
            TexasPlayer newPlayer = new TexasPlayer();
            players[i] = newPlayer;
            newPlayer.setCards(deck.deal(), 0);
            newPlayer.setCards(deck.deal(), 1);
        }
    }

    public void flop() {
        // setting community cards
        System.out.print("DEALT CARDS: ");
        for (int i = 0; i < 3; i++) {
            Card newCard = deck.deal();
            community.add(newCard);
            System.out.print(newCard.toString() + " ");
        }
        System.out.println();

        updateAllHands();
    }

    public void turn() {
        Card newCard = deck.deal();
        community.add(newCard);
        System.out.println("DEALT CARD: " + newCard.toString() + " ");
        updateAllHands();
    }

    public void river() {
        Card newCard = deck.deal();
        community.add(newCard);
        System.out.println("DEALT CARD: " + newCard.toString() + " ");
        updateAllHands();
    }

    public void setCommunity(List<Card> c) { community = c; }

    public void addCommunityCard(Card c) { community.add(c); }

    public void setHands(List<Card[]> hands) {
        for (int i = 0; i < numPlayers; i++) {
            players[i].setCards(hands.get(i));
        }
    }

    private void updateAllHands() {
        for (int i = 0; i < numPlayers; i++) {
            players[i].updateHand(community);
        }
    }

    /**
     * Prints all players hands
     * @return a string
     */
    public String toString() {
        String str = "";
        int maxPlayer = 0;
        int maxValue = Integer.MIN_VALUE;
        for (int i = 0; i < numPlayers; i++) {
            str += "Player #" + i + ": " + players[i].getBestHandValue() + "\n";
            if (players[i].getBestHandValue() > maxValue) {
                maxPlayer = i;
                maxValue = players[i].getBestHandValue();
            }

        }

        return str;
    }

}