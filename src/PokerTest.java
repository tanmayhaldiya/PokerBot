public class PokerTest {
    public static void main (String[] args) {
        deckTest();
    }

    public void deckTest() {
        Deck d = new Deck();
        Card[] remove = new Card[3];
        remove[0] = new Card("As");
        remove[1] = new Card("2c");
        remove[2] = new Card("Td");

        d.remove(remove);
        System.out.println(d);
    }

    public void gameTest() {
        Game game = new Game(5);
        // one round
        game.preflop();
        System.out.println(game.toString());
        game.flop();
        System.out.println(game.toString());
        game.turn();
        System.out.println(game.toString());
        game.river();
        System.out.println(game.toString());
    }
}