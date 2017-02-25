import java.awt.*;
import java.util.ArrayList;

public class PlayerPlane {
    public Image image;
    public int x;
    public int y;
    public int width;
    public int height;
    public int speed;
    public int shield;
    public boolean active = true;
    public int kill = 6;
    public boolean power = false;
    public int invulnerable = 300;
    ArrayList<PlayerBullet> bulletList = new ArrayList<PlayerBullet>();
    public PlayerPlane(int a, int b, int c, int d, int f, Image g ){
        x = a;
        y = b;
        width = c;
        height = d;
        speed = f;
        image = g;
    }
    void moveUp(){
        y -= speed;
    }
    void moveDown(){
        y += speed;
    }
    void moveRight(){
        x += speed;
    }
    void moveLeft(){
        x -= speed;
    }
}
