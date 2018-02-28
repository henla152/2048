public class RGBColor {

    private int red;
    private int green;
    private int blue;

    public RGBColor(int red, int green, int blue) {
        this.setRed(red);
        this.setGreen(green);
        this.setBlue(blue);
    }

    private void setRed(int red) {
        this.red = red;
    }

    private void setGreen(int green) {
        this.green = green;
    }

    private void setBlue(int blue) {
        this.blue = blue;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}
