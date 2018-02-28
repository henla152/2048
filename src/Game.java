import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private final int BOARD_SIZE = 4;
    private Painter painter;
    private List<Block> blockList;
    Random random = new Random();

    public void gameLoop() {
//        handleInput();

        System.out.println(blockList.size());
        for (Block block : blockList) {
            System.out.println(block.currentValue + " " + block.getPosition());
        }
    }


    public Game() {

        painter = new Painter();
        blockList = new ArrayList<>();

    }


    public void gameInit() {
        //slumpa 2 block som har värde antingen 2 eller 4
//        blockList.add(new Block());

        int tempX, tempY;
        Position position1 = new Position(random.nextInt(4),random.nextInt(4));
        do {
            tempX = random.nextInt(4);
            tempY = random.nextInt(4);
        } while (position1.getX() == tempX && position1.getY() == tempY);
        Position position2 = new Position(random.nextInt(4),random.nextInt(4));

        blockList.add(new Block(position1));
        blockList.add(new Block(position2));





    }



/*    private void handleInput() {
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
    */

}
