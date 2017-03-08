package models;

import controllers.PlayerPlaneController;

public class EnemyPlaneModel extends GameModel {

    public EnemyPlaneModel(int x, int y, int width, int height, int speed) {
        super(x, y, width, height, speed);
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

}
