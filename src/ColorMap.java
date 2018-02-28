import java.util.HashMap;
import java.util.Map;

public class ColorMap {
    private static final Map<Integer, RGBColor> colorMap = new HashMap<>();

    static {
        colorMap.put(2, new RGBColor(41,165,0));
        colorMap.put(4, new RGBColor(110, 237, 68));
        /*this.colorMap.put(8, new RGBColor());
        this.colorMap.put(16, new RGBColor( ));
        this.colorMap.put(32, new RGBColor( ));
        this.colorMap.put(64, new RGBColor( ));
        this.colorMap.put(128, new RGBColor( ));
        this.colorMap.put(256, new RGBColor( ));
        this.colorMap.put(512, new RGBColor( ));
        this.colorMap.put(1024, new RGBColor( ));*/
        colorMap.put(2048, new RGBColor(255, 255, 255));
    }

    public static RGBColor getColor(int key) {
        return colorMap.get(key);
    }
}
