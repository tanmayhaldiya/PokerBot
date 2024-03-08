public class Main {
    public static void main(String[] args) {
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