package models;

import controllers.PlayerPlaneController;

import java.awt.*;

public class GameModel {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int speed;

    public GameModel(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
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

    public double getMidX() {
        return x + width / 2.0;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public boolean intersects(GameModel other) {
        Rectangle rect1 = this.getRect();
        Rectangle rect2 = other.getRect();
        return rect1.intersects(rect2);
    }

    public void moveUp() {

        y -= speed;
    }

    public void moveDown() {

        y += speed;
    }

    public void moveRight() {

        x += speed;
    }

    public void moveLeft() {

        x -= speed;
    }

    public void moveRightDown() {
        x += (speed - 3);
        y += speed;
    }

    public void moveLeftDown() {
        x -= (speed - 3);
        y += speed;
    }

    public void moveRightUp() {
        y -= speed;
        x += speed - 3;
    }

    public void moveLeftUp() {
        y -= speed;
        x -= speed - 3;
    }

    public void moveToPlayer() {
        double dist = Math.sqrt(Math.pow(PlayerPlaneController.getPlayerY() - y, 2.0) + Math.pow(x - PlayerPlaneController.getPlayerX(), 2.0));
        double tempX = speed * (x - PlayerPlaneController.getPlayerX()) / dist;
        double tempY = speed * (PlayerPlaneController.getPlayerY() - y) / dist;
        x -= tempX;
        y += tempY;
    }

    public void moveAwayFromPlayer() {
        double dist = Math.sqrt(Math.pow(PlayerPlaneController.getPlayerY() - y, 2.0) + Math.pow(x - PlayerPlaneController.getPlayerX(), 2.0));
        double tempX = speed * (x - PlayerPlaneController.getPlayerX()) / dist;
        double tempY = speed * (PlayerPlaneController.getPlayerY() - y) / dist;
        x += tempX;
        y += tempY;
    }

    public void move(int x, int y){
        this.x += x;
        this.y += y;
    }
}
