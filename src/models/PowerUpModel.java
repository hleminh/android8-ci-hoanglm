package models;

public class PowerUpModel {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;

    public PowerUpModel(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
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

    public int getSpeed() {
        return speed;
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

    public void moveUp(){

        y -= speed;
    }
    public void moveDown(){

        y += speed;
    }
    public void moveRight(){

        x += speed;
    }
    public void moveLeft(){

        x -= speed;
    }
}
