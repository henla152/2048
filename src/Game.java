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
            Direction direction = waitForKeyInput(painter.getTerminal());
            if (tryMoveBlocks(direction)) {
                checkForCollisions(direction);
                tryMoveBlocks(direction);
                addBlock();
            }
        }
//        System.out.println(blockList.size());
//        for (Block block : blockList) {
//            block.print();
//        }
    }

    private void checkForCollisions(Direction direction) {
        Block[][] board = new Block[BOARD_SIZE][BOARD_SIZE];

        for (Block block : blockList) {
            board[block.getPosition().getX()][block.getPosition().getY()] = block;
        }

        int xSign = -1;
        int xDir = 0;
        int xStart = 0;
        int xEnd = BOARD_SIZE;
        int ySign = -1;
        int yDir = 0;
        int yStart = 0;
        int yEnd = BOARD_SIZE;

        switch (direction) {
            case LEFT:
                System.out.println("LEFT");
                xSign = -1;
                xStart = 1;
                xEnd = BOARD_SIZE;
                xDir = -1;
                break;
            case RIGHT:
                System.out.println("RIGHT");
                xSign = 1;
                xStart = BOARD_SIZE - 2;
                xEnd = -1;
                xDir = 1;
                break;
            case UP:
                System.out.println("UP");
                ySign = -1;
                yStart = 1;
                yEnd = BOARD_SIZE;
                yDir = -1;
                break;
            case DOWN:
                System.out.println("DOWN");
                ySign = 1;
                yStart = BOARD_SIZE-2;
                yEnd = -1;
                yDir = 1;
                break;
        }

        for (int x = xStart; x != xEnd; x = x - xSign) {
            for (int y = yStart; y != yEnd; y = y - ySign) {
                System.out.printf("x: %d, y: %d%n", x, y);
                Block tempBlock = board[x + xDir][y + yDir];
                if (tempBlock != null && board[x][y] != null) {
                    if (board[x][y].getCurrentMagnitude() == tempBlock.getCurrentMagnitude()) {
                        board[x + xDir][y + yDir] = new Block(new Position(x + xSign, y + xSign), tempBlock.getCurrentMagnitude().next());
                        board[x + xDir][y + yDir] = combineBlocks(tempBlock, board[x][y]);
                        board[x][y] = null;
                    }
                }
            }
        }
    }

    private Block combineBlocks(Block a, Block b) {
        Block res = new Block(new Position(a.getPosition().getX(), a.getPosition().getY()), a.getCurrentMagnitude().next());
        blockList.remove(a);
        blockList.remove(b);
        blockList.add(res);
        return res;
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

    private boolean tryMoveBlocks(Direction direction) {

        switch (direction) {
            case DOWN:
                return tryMoveBlocksDown();
            case UP:
                return tryMoveBlocksUp();
            case LEFT:
                return tryMoveBlocksLeft();
            case RIGHT:
                return tryMoveBlocksRight();

//            case Escape:
//                terminal.exitPrivateMode();
//                System.exit(0);
//                break;
        }
        return false;
    }

    private boolean tryMoveBlocksLeft() {
        boolean hasMoved = false;
        for (int x = 1; x < BOARD_SIZE; x++) {      //navigera
            for (int y = 0; y < BOARD_SIZE; y++) {
                for (Block block : blockList) {
                    if (block.getPosition().getX() == x && block.getPosition().getY() == y) {  //kolla om rutan innehåller block
                        int tempX = x;
                        while (tempX >= 1 && positionIsFree(new Position(tempX - 1, y))) {
                            tempX--;
                            hasMoved = true;
                        }
                        block.setPosition(new Position(tempX, y));
                    }
                }
            }
        }
        return hasMoved;
    }

    private boolean tryMoveBlocksRight() {
        boolean hasMoved = false;
        for (int x = 0; x < BOARD_SIZE - 1; x++) {      //navigera
            for (int y = 0; y < BOARD_SIZE; y++) {
                for (Block block : blockList) {
                    if (block.getPosition().getX() == x && block.getPosition().getY() == y) {  //kolla om rutan innehåller block
                        int tempX = x;
                        while (tempX < BOARD_SIZE - 1 && positionIsFree(new Position(tempX + 1, y))) {
                            tempX++;
                            hasMoved = true;
                        }
                        block.setPosition(new Position(tempX, y));
                    }
                }
            }
        }
        return hasMoved;
    }

    private boolean tryMoveBlocksUp() {
        boolean hasMoved = false;
        for (int x = 0; x < BOARD_SIZE; x++) {      //navigera
            for (int y = 1; y < BOARD_SIZE; y++) {
                for (Block block : blockList) {
                    if (block.getPosition().getX() == x && block.getPosition().getY() == y) {  //kolla om rutan innehåller block
                        int tempY = y;
                        while (tempY >= 1 && positionIsFree(new Position(x, tempY - 1))) {
                            tempY--;
                            hasMoved = true;
                        }
                        block.setPosition(new Position(x, tempY));
                    }
                }
            }
        }
        return hasMoved;
    }

    private boolean tryMoveBlocksDown() {
        boolean hasMoved = false;
        for (int x = 0; x < BOARD_SIZE; x++) {      //navigera
            for (int y = 0; y < BOARD_SIZE - 1; y++) {
                for (Block block : blockList) {
                    if (block.getPosition().getX() == x && block.getPosition().getY() == y) {  //kolla om rutan innehåller block
                        int tempY = y;
                        while (tempY < BOARD_SIZE - 1 && positionIsFree(new Position(x, tempY + 1))) {
                            tempY++;
                            hasMoved = true;
                        }
                        block.setPosition(new Position(x, tempY));
                    }
                }
            }
        }
        return hasMoved;
    }

    private Direction waitForKeyInput(Terminal terminal) {
        Key key;
        Direction direction = Direction.NONE;

        while (direction == Direction.NONE) {
            do {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {

                }
                key = terminal.readInput();
            }
            while (key == null);

            Key.Kind kind = key.getKind();

            switch (kind) {
                case ArrowUp:
                    direction = Direction.UP;
                    break;
                case ArrowDown:
                    direction = Direction.DOWN;
                    break;
                case ArrowLeft:
                    direction = Direction.LEFT;
                    break;
                case ArrowRight:
                    direction = Direction.RIGHT;
                    break;
            }


            while (key != null) {
                key = terminal.readInput();
            }
        }

        return direction;
    }


}
