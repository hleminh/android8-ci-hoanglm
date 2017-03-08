package strategies;

import models.GameModel;

public class MoveUpBehavior extends MoveBehavior {
    @Override
    public void move(GameModel model) {
        model.moveUp();
    }
}
