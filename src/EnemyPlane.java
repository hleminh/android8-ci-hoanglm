import java.awt.*;
import java.util.*;
public class EnemyPlane extends PlayerPlane{
    int moveType;
    ArrayList<EnemyBullet> bulletList = new ArrayList<EnemyBullet>();
    public EnemyPlane(int a, int b, int c, int d, int f, Image g){
        super(a,b,c,d,f,g);
    }
    void moveRightDown(){
        setY(getY() - getSpeed());
        setX(getX() + getSpeed() - 3);
    }
    void moveLeftDown(){
        setY(getY() - getSpeed());
        setX(getX() - getSpeed() - 3);
    }
}
