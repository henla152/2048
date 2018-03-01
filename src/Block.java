import java.util.Random;

public class Block {

    public enum Magnitude {
        M2, M4, M8, M16, M32, M64, M128, M256, M512, M1024, M2048;

        private static Magnitude[] values = values();
        public Magnitude next() {
            return values[(this.ordinal()+1) % values.length]; // %length to loop around, might be undesired behaviour.
        }
    }

    public Magnitude currentMagnitude;
    private Position position;
    private RGBColor color;
    private boolean newlyCombined = false;
    private static Random random = new Random();

    public Block(Position position) {
        this(position, (random.nextInt(2) == 0) ? Magnitude.M2 : Magnitude.M4);
    }

    public Block(Position position, Magnitude currentMagnitude) {
        this.currentMagnitude = currentMagnitude;
        this.position = position;
        this.color = ColorMap.getColor(currentMagnitude);
    }

    //Debug
    public void print() {
        System.out.println("Block\t" + "Position " + position.getX() + ", " + position.getY() + "\tcurrentValue " + currentMagnitude);
    }

    public Position getPosition() {
        return position;
    }

    public RGBColor getColor() {
        return color;
    }
}
