import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;

import java.nio.charset.Charset;
import java.util.List;

public class Painter {

    private Terminal terminal;

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

    }
}
