import java.awt.*;

public class Island {
    private Image image;
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;
    private boolean active = true;
    public Island(int a, int b, int c, int d, int f, Image g ){
        x = a;
        y = b;
        width = c;
        height = d;
        speed = f;
        image = g;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
