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
    private static int score;


    public void gameLoop() {

        while (!gameOver) {
            //logik
            //kontrollera gameOVer

            painter.paint(blockList);

            if (blockList.size() > 15 && !(isCombinableNeighbours())) gameOver = true;

            Direction direction = Direction.NONE;
            if (!gameOver) {
                direction = waitForKeyInput(painter.getTerminal());
            }
            if (tryMoveBlocks(direction) | checkForCollisions(direction)) {
                tryMoveBlocks(direction);

            }
        }
    }

    private boolean isCombinableNeighbours() {
        int x1, y1, x2, y2;
        Block.Magnitude mag1, mag2;
        for (Block block1 : blockList) {
            x1 = block1.getPosition().getX();
            y1 = block1.getPosition().getY();
            mag1 = block1.getCurrentMagnitude();
            for (Block block2 : blockList) {
                x2 = block2.getPosition().getX();
                y2 = block2.getPosition().getY();
                mag2 = block2.getCurrentMagnitude();
                if (x2 == x1 - 1 && y2 == y1 && mag1 == mag2) return true;
                if (x2 == x1 + 1 && y2 == y1 && mag1 == mag2) return true;
                if (x2 == x1 && y2 == y1 - 1 && mag1 == mag2) return true;
                if (x2 == x1 && y2 == y1 + 1 && mag1 == mag2) return true;
            }
        }
        return false;
    }

    private boolean checkForCollisions(Direction direction) {
        Block[][] board = new Block[BOARD_SIZE][BOARD_SIZE];

        for (Block block : blockList) {
            board[block.getPosition().getX()][block.getPosition().getY()] = block;
        }

        boolean hasCombined = false;
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
                xSign = -1;
                xStart = 1;
                xEnd = BOARD_SIZE;
                xDir = -1;
                break;
            case RIGHT:
                xSign = 1;
                xStart = BOARD_SIZE - 2;
                xEnd = -1;
                xDir = 1;
                break;
            case UP:
                ySign = -1;
                yStart = 1;
                yEnd = BOARD_SIZE;
                yDir = -1;
                break;
            case DOWN:
                ySign = 1;
                yStart = BOARD_SIZE - 2;
                yEnd = -1;
                yDir = 1;
                break;
        }
        int scoreValue = 0;
        for (int x = xStart; x != xEnd; x = x - xSign) {
            for (int y = yStart; y != yEnd; y = y - ySign) {
                Block tempBlock = board[x + xDir][y + yDir];
                if (tempBlock != null && board[x][y] != null) {
                    if (board[x][y].getCurrentMagnitude() == tempBlock.getCurrentMagnitude()) {
                        board[x + xDir][y + yDir] = combineBlocks(tempBlock, board[x][y]);
                        board[x][y] = null;
                        hasCombined = true;
                        scoreValue += getCurrentMagnitudeInteger(tempBlock.getCurrentMagnitude().next());
                    }
                }
            }
        }
        score += scoreValue;
        return hasCombined;
    }

    private Block combineBlocks(Block a, Block b) {
        Block newBlock = new Block(new Position(a.getPosition().getX(), a.getPosition().getY()), a.getCurrentMagnitude().next());
        blockList.remove(a);
        blockList.remove(b);
        blockList.add(newBlock);
        return newBlock;
    }

    private int getCurrentMagnitudeInteger(Block.Magnitude magnitude) {
        Block.Magnitude mag = magnitude;
        int scoreValue;

        switch (mag) {
            case M4:
                scoreValue = 4;
                break;
            case M8:
                scoreValue = 8;
                break;
            case M16:
                scoreValue = 16;
                break;
            case M32:
                scoreValue = 32;
                break;
            case M64:
                scoreValue = 64;
                break;
            case M128:
                scoreValue = 128;
                break;
            case M256:
                scoreValue = 256;
                break;
            case M512:
                scoreValue = 512;
                break;
            case M1024:
                scoreValue = 1024;
                break;
            case M2048:
                scoreValue = 2048;
                break;
            default:
                scoreValue = 0;
                break;
        }
        return scoreValue;


    }

    public Game(Painter painter) {
        this.painter = painter;
        blockList = new ArrayList<>();
    }

    public void gameInit() {
        //slumpa 2 block som har värde antingen 2 eller 4
        addBlock();
        addBlock();

        //DEBUG
//        Block.Magnitude m = Block.Magnitude.M2;
//        for (int x = 0; x < 4; x++) {
//            for (int y = 0; y < 3; y++) {
//                blockList.add(new Block(new Position(x, y), m));
//                m = m.next();
//            }
//        }
//        blockList.add(new Block(new Position(1, 3), Block.Magnitude.M128));
//        blockList.add(new Block(new Position(2, 3), Block.Magnitude.M256));
//        blockList.add(new Block(new Position(3, 3), Block.Magnitude.M1024));
//        blockList.add(new Block(new Position(3,3), Block.Magnitude.M2));
    }

    private boolean addBlock() {
        List<Position> tempList = listOfFreePositions();
        if (tempList.size() != 0) {
            Collections.shuffle(tempList);
            blockList.add(new Block(tempList.get(0)));
            return true;
        }
        return false;
    }

    private List<Position> listOfFreePositions() {
        List<Position> result = new ArrayList<>();
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Position pos = new Position(x, y);
                if (positionIsFree(pos)) {
                    result.add(pos);
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
                        while (tempX > 0 && positionIsFree(new Position(tempX - 1, y))) {
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
        for (int x = BOARD_SIZE - 2; x >= 0; x--) {      //navigera
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
            for (int y = BOARD_SIZE - 2; y >= 0; y--) {
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

    public static String getScore() {

        return Integer.toString(score);
    }
}
