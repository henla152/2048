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
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void paint(List<Block> blockList) {
        for (Block block : blockList) {
            terminal.applyForegroundColor(block.getColor().getR(), block.getColor().getG(), block.getColor().getB());
            int tempX = block.getPosition().getX() * xScale + xOffset;
            int tempY = block.getPosition().getY() * yScale + yOffset;
            for (int x = tempX; x < tempX + xScale - 1; x++) {
                for (int y = tempY; y < tempY + yScale - 1; y++) {
                    terminal.moveCursor(x, y);
                    terminal.putCharacter('\u2588');
                }
            }

        }
    }
}
