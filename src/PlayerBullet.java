import java.awt.*;

public class PlayerBullet {
    public Image image;
    public int x;
    public int y;
    public int width;
    public int height;
    public int speed;
    public PlayerBullet(int a, int b, int c, int d, int f, Image g ){
        x = a;
        y = b;
        width = c;
        height = d;
        speed = f;
        image = g;
    }
}
