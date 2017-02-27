package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Utils {
    public static Image loadImageFromRes(String url) {
        try {
            return ImageIO.read(new File("resources/" + url));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
