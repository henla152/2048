import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

public class Game {

    private void handleInput() {
        Key key;
        boolean badInput = true;
        while (badInput) {
            do {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException ex) {

                }
                key = terminal.readInput();
            }
            while (key == null);

            switch (key.getKind()) {
                case ArrowDown:
                    badInput = false;
                    player.moveDown(objects);
                    break;
                case ArrowUp:
                    badInput = false;
                    player.moveUp(objects);
                    break;
                case ArrowLeft:

                    badInput = false;
                    player.moveLeft(objects);
                    break;
                case ArrowRight:
                    badInput = false;
                    player.moveRight(objects);
                    break;
                case q:
                    terminal.exitPrivateMode();

            }

            while (key != null) {
                key = terminal.readInput();
            }
        }
    }
}
