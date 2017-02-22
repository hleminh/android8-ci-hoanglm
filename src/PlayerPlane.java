import java.awt.*;
import java.util.ArrayList;

public class PlayerPlane {
    public Image image;
    public int x;
    public int y;
    public int width;
    public int height;
    public int speed;
    ArrayList<PlayerBullet> bulletList = new ArrayList<PlayerBullet>();
    public PlayerPlane(int a, int b, int c, int d, int f, Image g ){
        x = a;
        y = b;
        width = c;
        height = d;
        speed = f;
        image = g;
    }

}
