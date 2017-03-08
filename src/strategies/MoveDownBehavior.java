package strategies;

import models.GameModel;

public class MoveDownBehavior extends MoveBehavior {
    @Override
    public void move(GameModel model) {
        model.moveDown();
    }
}
