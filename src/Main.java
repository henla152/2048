public class Main {

    private static Painter painter = new Painter();

    public static void main(String[] args) {

        //FEATURE Highscore sparas till fil
        boolean bool = true;


        while (bool) {
            Game game = new Game(painter);
            game.gameInit();
            game.gameLoop();
            bool = false;
        }
    }
}
