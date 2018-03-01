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
            //addBlock
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


        Block blockTest = new Block(new Position(1, 1), Block.Magnitude.M4);
        blockList.add(blockTest);
       // Block blockTest2 = new Block(new Position(1, 0),Block.Magnitude.M2);
        //blockList.add(blockTest2);

        moveBlocksDown();

/*        Block blockTest1 = new Block(new Position(2, 0));
        blockList.add(blockTest1);*/


        //slumpa 2 block som har värde antingen 2 eller 4
/*        addBlock();
        addBlock();*/



/*        for (int i = 0; i < 14; i++) {
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
    private void moveBlocksRight(){
        for (int x = 0; x < BOARD_SIZE-1; x++) {      //navigera
            for (int y = 0; y < BOARD_SIZE; y++) {
                for (Block block : blockList) {
                    if (block.getPosition().getX() == x && block.getPosition().getY() == y) {  //kolla om rutan innehåller block
                        int tempX = x;
                        while (tempX < BOARD_SIZE-1 && positionIsFree(new Position(tempX +1, y))) {
                            tempX++;
                        }
                        block.setPosition(new Position(tempX, y));
                    }
                }
            }
        }
    }

    private void moveBlocksUp(){
        for (int x = 0; x < BOARD_SIZE; x++) {      //navigera
            for (int y = 1; y < BOARD_SIZE; y++) {
                for (Block block : blockList) {
                    if (block.getPosition().getX() == x && block.getPosition().getY() == y) {  //kolla om rutan innehåller block
                        int tempY = y;
                        while (tempY >= 1 && positionIsFree(new Position(x, tempY-1))) {
                            tempY--;
                        }
                        block.setPosition(new Position(x, tempY));
                    }
                }
            }
        }

    }

    private void moveBlocksDown(){
        for (int x = 0; x < BOARD_SIZE; x++) {      //navigera
            for (int y = 0; y < BOARD_SIZE-1; y++) {
                for (Block block : blockList) {
                    if (block.getPosition().getX() == x && block.getPosition().getY() == y) {  //kolla om rutan innehåller block
                        int tempY = y;
                        while (tempY < BOARD_SIZE-1 && positionIsFree(new Position(x, tempY+1))) {
                            tempY++;
                        }
                        block.setPosition(new Position(x, tempY));
                    }
                }
            }
        }

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
                    moveBlocksDown();
                    break;
                case ArrowUp:
                    moveBlocksUp();
                    break;
                case ArrowLeft:
                    moveBlocksLeft();
                    System.out.println("Vänster");
                    break;
                case ArrowRight:
                    moveBlocksRight();
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
