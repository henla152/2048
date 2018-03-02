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
    private final int legendSize = xOffset*2;
    private final int xLegend = columnAmount;
    private final int yLegend = 2;
    private final int xLegendOffset = columnAmount - xOffset + (xOffset / 6);
    private final int xLegendTextOffset = xLegendOffset + 5;
    private final int yLegendTextOffset = yLegend + 5;



    private final RGBColor borderColor = new RGBColor(110, 110, 110);

    private void paintLegend() {
        int yy = yLegend;
        Block.Magnitude blockMagnitude = Block.Magnitude.M2;
        for (int i = 1; i < 12; i = i + 1) {
            int value = (int) Math.pow(2, i);
            yy = i + i;
            printTextToTerminal("      ", xLegendTextOffset, yLegendTextOffset + 24 - yy, ColorMap.getColor(blockMagnitude), ColorMap.getColor(blockMagnitude));
            printTextToTerminal((value + ""), xLegendTextOffset + 8 + (4 - (value+"").length() ), yLegendTextOffset + 24 - yy, new RGBColor(46, 52, 54), new RGBColor(255, 255, 255));
            blockMagnitude = blockMagnitude.next();
        }

        //Border
        for (int x = xLegendOffset; x < xLegendOffset + legendSize; x++) {
            printTextToTerminal(" ", x, yOffset, borderColor, borderColor);
            printTextToTerminal(" ", x, rowAmount - yOffset - 1, borderColor, borderColor);
        }

        for (int y = yOffset; y < rowAmount - yOffset; y++) {
            printTextToTerminal(" ", xLegendOffset, y, borderColor, borderColor);
            printTextToTerminal(" ", xLegendOffset + 1, y, borderColor, borderColor);
            printTextToTerminal(" ", xLegendOffset + legendSize, y, borderColor, borderColor);
            printTextToTerminal(" ", xLegendOffset + legendSize + 1, y, borderColor, borderColor);
        }
        String score = "Score: " + Game.getScore();
        printTextToTerminal(score, xLegendTextOffset, 5, new RGBColor(46, 52, 54), borderColor);

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
        for (int x = xOffset; x < columnAmount - xOffset; x++) {
            printTextToTerminal(" ", x, yOffset, borderColor, borderColor);
            printTextToTerminal(" ", x, rowAmount - yOffset - 1, borderColor, borderColor);
        }
        for (int y = yOffset; y < rowAmount - yOffset; y++) {
            printTextToTerminal(" ", xOffset, y, borderColor, borderColor);
            printTextToTerminal(" ", xOffset + 1, y, borderColor, borderColor);
            printTextToTerminal(" ", columnAmount - xOffset - 1, y, borderColor, borderColor);
            printTextToTerminal(" ",columnAmount - xOffset - 2, y, borderColor, borderColor);
        }
    }
}
