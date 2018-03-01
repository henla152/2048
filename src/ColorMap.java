import java.util.HashMap;
import java.util.Map;


public class ColorMap {
    private static final Map<Block.Magnitude, RGBColor> colorMap = new HashMap<>();

    static {
        colorMap.put(Block.Magnitude.M2, new RGBColor(41,165,0));
        colorMap.put(Block.Magnitude.M4, new RGBColor(110, 237, 68));
        colorMap.put(Block.Magnitude.M8, new RGBColor(23,44,233));
        colorMap.put(Block.Magnitude.M16, new RGBColor( 149, 204, 237));
        colorMap.put(Block.Magnitude.M32, new RGBColor(249, 24, 216 ));
        colorMap.put(Block.Magnitude.M64, new RGBColor(249, 167, 237 ));
        colorMap.put(Block.Magnitude.M128, new RGBColor( 255, 17, 17));
        colorMap.put(Block.Magnitude.M256, new RGBColor( 237, 104, 2));
        colorMap.put(Block.Magnitude.M512, new RGBColor( 76, 0, 57));
        colorMap.put(Block.Magnitude.M1024, new RGBColor( 192,192,192));
        colorMap.put(Block.Magnitude.M2048, new RGBColor(255, 215, 0));
    }

    public static RGBColor getColor(Block.Magnitude key) {
        return colorMap.get(key);
    }
}
