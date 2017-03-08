package strategies;

import controllers.PlayerPlaneController;
import models.GameModel;

public class MoveToPlayerBehavior extends MoveBehavior {
    @Override
    public void move(GameModel model) {
        double dist = Math.sqrt(Math.pow(PlayerPlaneController.getPlayerY() - model.getY(), 2.0) + Math.pow(model.getX() - PlayerPlaneController.getPlayerX(), 2.0));
        double tempX = model.getSpeed()* (model.getX() - PlayerPlaneController.getPlayerX()) / dist;
        double tempY = model.getSpeed() * (PlayerPlaneController.getPlayerY() - model.getY()) / dist;
        model.setX(model.getX() - (int)tempX);
        model.setY(model.getY() + (int)tempY);
    }
}