import java.awt.*;
import java.util.ArrayList;

public class PlayerPlane {
    private Image image;
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;
    private int shield;
    private boolean active = true;
    private int kill = 6;
    private boolean power = false;
    private int invulnerable = 300;
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
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage(){
        return image;
    }

    public ArrayList<PlayerBullet> getBulletList() {
        return bulletList;
    }
    public int getSpeed() {
        return speed;
    }

    public int getShield() {
        return shield;
    }

    public boolean isActive() {
        return active;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    public void setInvulnerable(int invulnerable) {
        this.invulnerable = invulnerable;
    }

    public int getKill() {
        return kill;
    }

    public boolean isPower() {
        return power;
    }

    public int getInvulnerable() {
        return invulnerable;
    }
    public void draw(Graphics graphic){
        graphic.drawImage(image,x,y,width,height,null);
    }
}
