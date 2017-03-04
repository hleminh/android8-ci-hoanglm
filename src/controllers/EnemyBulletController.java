package controllers;

import game.GameWindow;
import models.EnemyBulletModel;
import utils.Utils;
import views.GameView;

import java.awt.*;

public class EnemyBulletController extends GameController{
    private int moveType;

    private int kill = 3;

    public EnemyBulletController(GameView view, EnemyBulletModel model) {
        super(view, model);
    }

    public EnemyBulletController(int x, int y, int width, int height, int speed, int moveType, Image image) {
        this(
                new GameView(image),
                new EnemyBulletModel(x,y,width,height,speed)
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

    public synchronized void run() {
        if (model instanceof EnemyBulletModel) {
            if (getModel().getY() > GameWindow.windowY + 10 || getModel().getX() > GameWindow.windowX + 10 || getModel().getX() < -10)
                setActive(false);
            if (isActive() == false){
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
                    ((EnemyBulletModel) model).moveDown();
                    break;
                case 1:
                    view.setImage(Utils.loadImageFromRes("bullet-right.png"));
                    model.setWidth(13);
                    model.setHeight(13);
                    ((EnemyBulletModel) model).moveRightDown();
                    break;
                case 2:
                    view.setImage(Utils.loadImageFromRes("bullet-left.png"));
                    model.setWidth(13);
                    model.setHeight(13);
                    ((EnemyBulletModel) model).moveLeftDown();
                    break;
                case 3:
                    ((EnemyBulletModel) model).moveToPlayer();
                    break;
            }
        }
    }

}
