package strategies;

import models.GameModel;

public class MoveRightDownBehavior extends MoveBehavior {
    @Override
    public void move(GameModel model) {
        model.moveRightDown();
    }
}
