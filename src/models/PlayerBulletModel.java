package models;

import controllers.GameController;

public class PlayerBulletModel extends  GameModel{


    public PlayerBulletModel(int x, int y, int width, int height, int speed) {
        super(x, y, width, height, speed);
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

    public void moveRightUp(){
        y -= speed;
        x += speed - 3;
    }

    public void moveLeftUp(){
        y -= speed;
        x -= speed - 3;
    }
    public void moveToEnemy(GameController enemy) {
        if(enemy!=null){
            double dist = Math.sqrt(Math.pow(enemy.getModel().getY() - y, 2.0) + Math.pow(x - enemy.getModel().getX(), 2.0));
            double tempX = speed * (x - enemy.getModel().getX()) / dist;
            double tempY = speed * (enemy.getModel().getY() - y) / dist;
            x -= tempX;
            y += tempY;
        }
        if (enemy==null) y -= speed;
    }
}
