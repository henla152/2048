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

    public void gameLoop() {

        while (!gameOver) {
            //logik
            //kontrollera gameOVer

            painter.paint(blockList);

            Direction direction = waitForKeyInput(painter.getTerminal());
            if (tryMoveBlocks(direction) | checkForCollisions(direction)) {
                tryMoveBlocks(direction);
                if (!addBlock()) gameOver = true;
            } else {
                for (Block block1 : blockList) {
                    for (Block block2 : blockList) {
                    }
                }
            }

//            if (!(tryMoveBlocks(direction) && checkForCollisions(direction)) && listOfFreePositions().size() == 0) { //stannar med en kvar....
//                    gameOver = true;
//            }

//            if (blockList.size() == BOARD_SIZE*BOARD_SIZE) gameOver = true;
        }
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
        for (int x = xStart; x != xEnd; x = x - xSign) {
            for (int y = yStart; y != yEnd; y = y - ySign) {
                Block tempBlock = board[x + xDir][y + yDir];
                if (tempBlock != null && board[x][y] != null) {
                    if (board[x][y].getCurrentMagnitude() == tempBlock.getCurrentMagnitude()) {
                        board[x + xDir][y + yDir] = combineBlocks(tempBlock, board[x][y]);
                        board[x][y] = null;
                        hasCombined = true;
                    }
                }
            }
        }
        return hasCombined;
    }

    private Block combineBlocks(Block a, Block b) {
        Block newBlock = new Block(new Position(a.getPosition().getX(), a.getPosition().getY()), a.getCurrentMagnitude().next());
        blockList.remove(a);
        blockList.remove(b);
        blockList.add(newBlock);
        return newBlock;
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
        Block.Magnitude m = Block.Magnitude.M2;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 3; y++) {
                blockList.add(new Block(new Position(x, y), m));
                m = m.next();
            }
        }
        blockList.add(new Block(new Position(1, 3), Block.Magnitude.M128));
        blockList.add(new Block(new Position(2, 3), Block.Magnitude.M256));
        blockList.add(new Block(new Position(3, 3), Block.Magnitude.M1024));
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


}
