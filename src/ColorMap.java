import java.util.HashMap;
import java.util.Map;

public class ColorMap {
    private static final Map<Integer, RGBColor> colorMap = new HashMap<>();

    static {
        colorMap.put(2, new RGBColor(41,165,0));
        colorMap.put(4, new RGBColor(110, 237, 68));
        colorMap.put(8, new RGBColor(23,44,233));
        colorMap.put(16, new RGBColor( 149, 204, 237));
        colorMap.put(32, new RGBColor(249, 24, 216 ));
        colorMap.put(64, new RGBColor(249, 167, 237 ));
        colorMap.put(128, new RGBColor( 255, 17, 17));
        colorMap.put(256, new RGBColor( 237, 104, 2));
        colorMap.put(512, new RGBColor( 76, 0, 57));
        colorMap.put(1024, new RGBColor( 192,192,192));
        colorMap.put(2048, new RGBColor(255, 215, 0));
    }

    public static RGBColor getColor(int key) {
        return colorMap.get(key);
    }
}
