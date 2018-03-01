import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.googlecode.lanterna.input.Key.Kind.*;

public class Game {

    public static final int BOARD_SIZE = 4;
    private Painter painter;
    private List<Block> blockList;
    private boolean gameOver = false;
    Random random = new Random();

    public void gameLoop() {

        while (!gameOver) {

            painter.paint(blockList);
            waitForKeyInput(painter.getTerminal());

        }
//        System.out.println(blockList.size());
//        for (Block block : blockList) {
//            block.print();
//        }
    }

    public Game(Painter painter) {
        this.painter = painter;
        blockList = new ArrayList<>();

    }

    public void gameInit() {
        //slumpa 2 block som har värde antingen 2 eller 4

        int tempX, tempY;
        Position position1 = new Position(random.nextInt(BOARD_SIZE), random.nextInt(BOARD_SIZE));
        do {
            tempX = random.nextInt(BOARD_SIZE);
            tempY = random.nextInt(BOARD_SIZE);
        } while (position1.getX() == tempX && position1.getY() == tempY);
        Position position2 = new Position(tempX, tempY);
        blockList.add(new Block(position1));
        blockList.add(new Block(position2));


        for (int i = 0; i < 14; i++) {
            newBlock();
        }



/*        for (int i = 0; i < 100; i++) {
            blockList.add(new Block(new Position(random.nextInt(BOARD_SIZE), random.nextInt(BOARD_SIZE))));
        }*/
    }

    private void newBlock() {
        int tempX, tempY;
        do {
            tempX = random.nextInt(BOARD_SIZE);
            tempY = random.nextInt(BOARD_SIZE);
        } while (!slotIsFree(new Position(tempX,tempY)));

        blockList.add(new Block(new Position(tempX, tempY)));
    }

    private boolean slotIsFree(Position position) {
        for (Block block : blockList)
            if (block.getPosition().getX() == position.getX() && block.getPosition().getY() == position.getY())
                return false;
        return true;
    }


    private void waitForKeyInput(Terminal terminal) {
        Key key;

        while (true) {
            do {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {

                }
                key = terminal.readInput();
            }

            while (key == null);

            switch (key.getKind()) {
                case ArrowDown:

                    break;
                case ArrowUp:

                    break;
                case ArrowLeft:

                    break;
                case ArrowRight:
                    break;

                case Escape:
                    terminal.exitPrivateMode();
                    System.exit(0);
                    break;
            }
            while (key != null) {
                key = terminal.readInput();
            }
        }
    }
}
