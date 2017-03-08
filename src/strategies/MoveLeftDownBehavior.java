package strategies;

import models.GameModel;

public class MoveLeftDownBehavior extends MoveBehavior {
    @Override
    public void move(GameModel model) {
        model.moveLeftDown();
    }
}
