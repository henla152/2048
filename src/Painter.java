import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;

import java.nio.charset.Charset;
import java.util.List;

public class Painter {

    private Terminal terminal;
    private final int xScale = 13;
    private final int yScale = 7;
    private final int xOffset = 13;
    private final int yOffset = 1;

    public Painter() {
        terminal = TerminalFacade.createTerminal(System.in,
                System.out, Charset.forName("UTF8"));
        terminal.enterPrivateMode();
        terminal.setCursorVisible(false);
        border();
    }

    public Terminal getTerminal() {
        return terminal;
    }


    public void paint(List<Block> blockList) {
        for (Block block : blockList) {
            terminal.applyBackgroundColor(block.getColor().getR(), block.getColor().getG(), block.getColor().getB());
            int tempX = (block.getPosition().getX() * xScale) + xOffset;
            int tempY = (block.getPosition().getY() * yScale) + yOffset;
            for (int x = tempX; x < tempX + xScale - 2; x++) {
                for (int y = tempY; y < tempY + yScale - 1; y++) {
                    terminal.moveCursor(x , y);
                    terminal.putCharacter(' ');
                }
            }

        }
    }

    public void border() {
        terminal.applyBackgroundColor(110,110,110);
        for (int i = 0; i < ((xScale * Game.BOARD_SIZE) + 1); i++) {
            int tempX = Math.max((xOffset - 1 + i),0);
            int tempY = Math.min(yOffset - 1,30);
            terminal.moveCursor(tempX , tempY);
            terminal.putCharacter(' ');
            tempY = Math.min(yOffset + (yScale * 4) - 1,30);
            terminal.moveCursor(tempX , tempY);
            terminal.putCharacter(' ');
        }
        for (int i = 0; i < ((yScale * Game.BOARD_SIZE) + 1); i++) {
            int tempX = Math.max((xOffset - 2),0);
            int tempY = Math.min((yOffset - 1 + i),30);
            terminal.moveCursor(tempX , tempY);
            terminal.putCharacter(' ');
            tempX = Math.max((xOffset - 1),0);
            terminal.moveCursor(tempX , tempY);
            terminal.putCharacter(' ');
            tempX = Math.max((xOffset + (xScale * 4) - 1),0);
            terminal.moveCursor(tempX , tempY);
            terminal.putCharacter(' ');
            tempX = Math.max((xOffset + (xScale * 4) - 2),0);
            terminal.moveCursor(tempX , tempY);
            terminal.putCharacter(' ');
        }

    }
}
