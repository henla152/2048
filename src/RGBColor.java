public class RGBColor {

    private int r;
    private int g;
    private int b;

    public RGBColor(int r, int g, int b) {
        this.setR(r);
        this.setG(g);
        this.setB(b);
    }

    private void setR(int r) {
        this.r = r;
    }

    private void setG(int g) {
        this.g = g;
    }

    private void setB(int b) {
        this.b = b;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }
}
