package strategies;

import models.GameModel;

public class MoveLeftBehavior extends MoveBehavior {
    @Override
    public void move(GameModel model) {
        model.moveLeft();
    }
}
