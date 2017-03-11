package controllers;

import game.GameWindow;
import models.EnemyBulletModel;
import strategies.*;
import utils.Utils;
import views.GameView;

import java.awt.*;

public class EnemyBulletController extends GameController {
    private int moveType;

    private int kill = 3;

    private MoveBehavior moveBehavior;

    public EnemyBulletController(GameView view, EnemyBulletModel model) {
        super(view, model);
    }

    public EnemyBulletController(int x, int y, int width, int height, int speed, int moveType, Image image) {
        this(
                new GameView(image),
                new EnemyBulletModel(x, y, width, height, speed)
        );
        this.moveType = moveType;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getMoveType() {
        return moveType;
    }

    public void setMoveType(int moveType) {
        this.moveType = moveType;
    }

    public void setMoveBehavior(MoveBehavior moveBehavior) {
        this.moveBehavior = moveBehavior;
    }

    public synchronized void run() {
        if (model instanceof EnemyBulletModel) {
            if (getModel().getY() > GameWindow.windowY + 10 || getModel().getX() > GameWindow.windowX + 10 || getModel().getX() < -10)
                setActive(false);
            if (isActive() == false) {
                switch (getKill()) {
                    case 3:
                        getModel().setWidth(32);
                        getModel().setHeight(31);
                        getView().setImage(Utils.loadImageFromRes("crosshair.png"));
                        setKill(getKill() - 1);
                        break;
                    case 2:
                        setKill(getKill() - 1);
                        break;
                    case 1:
                        setKill(getKill() - 1);
                        break;
                }
            }
            switch (moveType) {
                case 0:
                    setMoveBehavior(new MoveDownBehavior());
                    moveBehavior.move(this.model);
                    break;
                case 1:
                    view.setImage(Utils.loadImageFromRes("bullet-right.png"));
                    model.setWidth(13);
                    model.setHeight(13);
                    setMoveBehavior(new MoveRightDownBehavior());
                    moveBehavior.move(this.model);
                    break;
                case 2:
                    view.setImage(Utils.loadImageFromRes("bullet-left.png"));
                    model.setWidth(13);
                    model.setHeight(13);
                    setMoveBehavior(new MoveLeftDownBehavior());
                    moveBehavior.move(this.model);
                    break;
                case 3:
                    setMoveBehavior(new MoveToPlayerBehavior());
                    moveBehavior.move(this.model);
                    break;
            }
        }
    }

}
