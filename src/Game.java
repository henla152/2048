import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {

    public static final int BOARD_SIZE = 4;
    private Painter painter;
    private List<Block> blockList;
    private boolean gameOver = false;
    Random random = new Random();

    public void gameLoop() {

        while (!gameOver) {
            //logik
            //kontrollera gameOVer

            System.out.println("runda");
            painter.paint(blockList);
            waitForKeyInput(painter.getTerminal());
            checkForCollisions();
            addBlock();
        }
//        System.out.println(blockList.size());
//        for (Block block : blockList) {
//            block.print();
//        }
    }

    private void checkForCollisions() {
        
    }

    public Game(Painter painter) {
        this.painter = painter;
        blockList = new ArrayList<>();
    }

    public void gameInit() {
        //slumpa 2 block som har värde antingen 2 eller 4
        addBlock();
        addBlock();


        /*for (int i = 0; i < 14; i++) {
            addBlock();
        }*/

        /* for (int i = 0; i < 100; i++) {
            blockList.add(new Block(new Position(random.nextInt(BOARD_SIZE), random.nextInt(BOARD_SIZE))));
        }*/
    }

    private void addBlock() {
        List<Position> tempList = listOfFreePositions();
        Collections.shuffle(tempList);
        blockList.add(new Block(tempList.get(0)));
    }

    private List<Position> listOfFreePositions() {
        List<Position> result = new ArrayList<>();
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (positionIsFree(new Position(x, y))) {
                    result.add(new Position(x, y));
                }
            }
        }
        return result;
    }

    private boolean positionIsFree(Position position) {
        for (Block block : blockList)
            if (block.getPosition().getX() == position.getX() && block.getPosition().getY() == position.getY())
                return false;
        return true;
    }


    private void moveBlocksLeft() {
        for (int x = 1; x < BOARD_SIZE; x++) {      //navigera
            for (int y = 0; y < BOARD_SIZE; y++) {
                for (Block block : blockList) {
                    if (block.getPosition().getX() == x && block.getPosition().getY() == y) {  //kolla om rutan innehåller block
                        int tempX = x;
                        while (tempX >= 1 && positionIsFree(new Position(tempX - 1, y))) {
                            tempX--;
                        }
                        block.setPosition(new Position(tempX, y));
                    }
                }
            }
        }
    }

    private void waitForKeyInput(Terminal terminal) {
        Key key;

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
                    moveBlocksLeft();
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
