package strategies;

import models.GameModel;

public class MoveLeftUpBehavior extends MoveBehavior {
    @Override
    public void move(GameModel model) {
        model.moveLeftUp();
    }
}
