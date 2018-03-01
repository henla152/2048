import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.List;

public class Painter {

    private Terminal terminal;
    private final int xScale = 13;
    private final int yScale = 7;
    private final int xMarginal = 2;
    private final int yMarginal = 1;
    private final int xOffset = 13;
    private final int yOffset = 1;
    private final int columnAmount = Game.BOARD_SIZE * xScale + xOffset * 2 + xMarginal * 3;
    private final int rowAmount = Game.BOARD_SIZE * yScale + yOffset * 2 + yMarginal * 3;


    public Painter() {
//        terminal = TerminalFacade.createTerminal(System.in,
//                System.out, Charset.forName("UTF8"));
        terminal = TerminalFacade.createSwingTerminal(columnAmount, rowAmount);
        terminal.enterPrivateMode();
        terminal.setCursorVisible(false);
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void paint(List<Block> blockList) {
        terminal.clearScreen();
        paintBorder();
        paintBlocks(blockList);
    }

    public void paintBlocks(List<Block> blockList) {
        for (Block block : blockList) {
            terminal.applyBackgroundColor(block.getColor().getRed(), block.getColor().getGreen(), block.getColor().getBlue());
            int tempX = (block.getPosition().getX() * xScale) + xOffset + xMarginal * 2;
            int tempY = (block.getPosition().getY() * yScale) + yOffset + yMarginal * 2;
            for (int x = tempX; x < tempX + xScale - xMarginal; x++) {
                for (int y = tempY; y < tempY + yScale - yMarginal; y++) {
                    terminal.moveCursor(x, y);
                    terminal.putCharacter(' ');
                }
            }
        }
    }

    public void paintBorder() {
        terminal.applyBackgroundColor(110, 110, 110);
//        for (int i = 0; i < ((xScale * Game.BOARD_SIZE + xMarginal) + 1); i++) {
//            int tempX = Math.max((xOffset - 1 + i), 0);
//            int tempY = Math.min(yOffset - 1, 30);
//            terminal.moveCursor(tempX, tempY);
//            terminal.putCharacter(' ');
//            tempY = Math.min(yOffset + (yScale * Game.BOARD_SIZE + yMarginal * 4) - 1, 30);
//            terminal.moveCursor(tempX, tempY);
//            terminal.putCharacter(' ');
//        }
//        for (int i = 0; i < ((yScale * Game.BOARD_SIZE + yMarginal) + 1); i++) {
//            int tempX = Math.max((xOffset - 2), 0);
//            int tempY = Math.min((yOffset - 1 + i), 30);
//            terminal.moveCursor(tempX, tempY);
//            terminal.putCharacter(' ');
//            tempX = Math.max((xOffset - 1), 0);
//            terminal.moveCursor(tempX, tempY);
//            terminal.putCharacter(' ');
//            tempX = Math.max((xOffset + (xScale * Game.BOARD_SIZE + xMarginal * 2) - 1), 0);
//            terminal.moveCursor(tempX, tempY);
//            terminal.putCharacter(' ');
//            tempX = Math.max((xOffset + (xScale * Game.BOARD_SIZE + xMarginal * 2) - 2), 0);
//            terminal.moveCursor(tempX, tempY);
//            terminal.putCharacter(' ');
//        }
        for (int x = xOffset; x < columnAmount - xOffset; x++) {
            terminal.moveCursor(x, yOffset);
            terminal.putCharacter(' ');
            terminal.moveCursor(x, rowAmount - yOffset - 1);
            terminal.putCharacter(' ');
        }

        for (int y = yOffset; y < rowAmount - yOffset; y++) {
            terminal.moveCursor(xOffset, y);
            terminal.putCharacter(' ');
            terminal.moveCursor(xOffset + 1, y);
            terminal.putCharacter(' ');
            terminal.moveCursor(columnAmount - xOffset - 1, y);
            terminal.putCharacter(' ');
            terminal.moveCursor(columnAmount - xOffset - 2, y);
            terminal.putCharacter(' ');
        }

    }
}
