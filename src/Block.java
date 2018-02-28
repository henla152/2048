import java.util.Random;

public class Block {
    public int currentValue;
    private Position position;
    private boolean newlyCombined = false;
    private static Random random = new Random();

    public Block(Position position) {
        currentValue = ((random.nextInt(2) + 1) * 2); // initial value set to 2 or 4
        this.position = position;
    }

    public Block(Position position, int currentValue) {

    }

    public Position getPosition() {
        return position;
    }
}
