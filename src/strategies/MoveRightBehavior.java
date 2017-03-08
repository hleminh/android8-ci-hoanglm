package strategies;

import models.GameModel;

public class MoveRightBehavior extends MoveBehavior {
    @Override
    public void move(GameModel model) {
        model.moveRight();
    }
}
