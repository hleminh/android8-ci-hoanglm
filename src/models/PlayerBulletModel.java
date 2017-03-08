package models;

import controllers.GameController;

public class PlayerBulletModel extends GameModel {

    private double enemyX;
    private double enemyY;
    private boolean flag = false;

    public PlayerBulletModel(int x, int y, int width, int height, int speed) {
        super(x, y, width, height, speed);
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



    public void moveToEnemy(GameController enemy) {
        if (enemy != null) {
            if (flag == false) {
                double dist = Math.sqrt(Math.pow(enemy.getModel().getY() - y, 2.0) + Math.pow(x - enemy.getModel().getX(), 2.0));
                double tempX = speed * (x - enemy.getModel().getX()) / dist;
                double tempY = speed * (enemy.getModel().getY() - y) / dist;
                x -= tempX;
                y += tempY;
                enemyX = tempX;
                enemyY = tempY;
            }
            if (flag == true){
                x -= enemyX;
                y += enemyY;
            }
            if (flag == false) flag = true;
        }
        if (enemy == null) y -= speed;
    }
}
