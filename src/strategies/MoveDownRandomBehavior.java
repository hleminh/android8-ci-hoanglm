package strategies;

import game.GameWindow;
import models.GameModel;

import java.util.Random;

public class MoveDownRandomBehavior extends MoveBehavior{
    @Override
    public void move(GameModel model) {
        model.moveDown();
        if (model.getY() >= 600) {
            model.setY(0);
            Random randomX = new Random();
            model.setX(randomX.nextInt(GameWindow.windowX - model.getWidth()));
        }
    }
}
