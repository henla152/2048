import java.util.Random;

public class Block {

    public int currentValue;
    private Position position;
    private RGBColor color;
    private boolean newlyCombined = false;
    private static Random random = new Random();

    public Block(Position position) {
        currentValue = ((random.nextInt(2) + 1) * 2); // initial value set to 2 or 4
        this.position = position;
        this.color = ColorMap.getColor(currentValue);

    }

    public Block(Position position, int currentValue) {

    }

    //Debug
    public void print() {
        System.out.println("Block\t" + "Position " + position.getX() + ", " + position.getY() + "\tcurrentValue " + currentValue);
    }

    public Position getPosition() {
        return position;
    }

    public RGBColor getColor() {
        return color;
    }
}
