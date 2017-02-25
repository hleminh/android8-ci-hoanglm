import java.awt.*;
import java.util.*;
public class EnemyPlane extends PlayerPlane{
    int moveType;
    ArrayList<EnemyBullet> bulletList = new ArrayList<EnemyBullet>();
    public EnemyPlane(int a, int b, int c, int d, int f, Image g){
        super(a,b,c,d,f,g);
    }
    void moveRightDown(){
        y += speed - 2;
        x += speed - 3;
    }
    void moveLeftDown(){
        y += speed - 2;
        x -= speed - 3;
    }
}
