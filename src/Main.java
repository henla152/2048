public class Main {

    public static void main(String[] args) {

        //FEATURE Highscore sparas till fil
        boolean bool = true;
        while (bool) {
            Game game = new Game();
            game.gameInit();
            game.gameLoop();
            bool = false;
        }


    }


}
