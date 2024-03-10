public class DeckTest {
    public static void main (String[] args) {
        Deck d = new Deck();
        Card[] remove = new Card[3];
        remove[0] = new Card("As");
        remove[1] = new Card("2c");
        remove[2] = new Card("Td");
        System.out.println("Before:\n" + d);
        d.remove(remove);
        System.out.println("After:\n" + d);
    }
}