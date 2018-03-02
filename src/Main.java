public class Main {

    private static Painter painter = new Painter();

    public static void main(String[] args) {

        Game game = new Game(painter);
        game.gameInit();
        game.gameLoop();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
        painter.paintGameOver();
    }
}
