package strategies;

import models.GameModel;

public class MoveRightUpBehavior extends MoveBehavior{
    @Override
    public void move(GameModel model) {
        model.moveRightUp();
    }
}
