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
    private final int legendSize = 30;
    private final int xLegend = columnAmount + 5;
    private final int yLegend = 2;

    private void paintLegend() {
        int y = yLegend;
        Block.Magnitude blockMagnitude = Block.Magnitude.M2;
        for (int i = 1; i < 12; i = i + 1) {
            int value = (int) Math.pow(2, i);
            y = i + i;
            printTextToTerminal("      ", xLegend, yLegend + y, ColorMap.getColor(blockMagnitude), ColorMap.getColor(blockMagnitude));
            printTextToTerminal((value + ""), xLegend + 7, yLegend + y ,new RGBColor(46,52,54),new RGBColor(255,255,255));
//            printTextToTerminal((value + ""), xLegend + 7, yLegend + y ,new RGBColor(46,52,54),new RGBColor(110,110,110));
            blockMagnitude = blockMagnitude.next();
        }
    }

    private void printCharacterToTerminal(Character character, int x, int y, RGBColor rgbColorBackground, RGBColor rgbColorForeground) {
        terminal.applyBackgroundColor(rgbColorBackground.getRed(), rgbColorBackground.getGreen(), rgbColorBackground.getBlue());
        terminal.applyForegroundColor(rgbColorForeground.getRed(), rgbColorForeground.getGreen(), rgbColorForeground.getBlue());
        terminal.moveCursor(x, y);
        terminal.putCharacter(character);
    }

    private void printTextToTerminal(String string, int x, int y, RGBColor rgbColorBackground, RGBColor rgbColorForeground) {
        for (int i = 0; i < string.length(); i++)
            printCharacterToTerminal(string.charAt(i), (x + i), y, rgbColorBackground, rgbColorForeground);
    }


    public Painter() {
//        terminal = TerminalFacade.createTerminal(System.in,
//                System.out, Charset.forName("UTF8"));
        terminal = TerminalFacade.createSwingTerminal(columnAmount + legendSize, rowAmount);
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

        paintLegend();
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
