package controllers;

import models.PlayerBulletModel;
import utils.Utils;
import views.GameView;

import java.awt.*;

public class PlayerBulletController extends GameController {

    private int moveType;
    private int kill = 3;
    private GameController enemy;
    private boolean isRocket = false;

    public PlayerBulletController(GameView view, PlayerBulletModel model) {
        super(view, model);
    }

    public PlayerBulletController(int x, int y, int width, int height, int speed, int moveType, Image image) {
        this(
                new GameView(image),
                new PlayerBulletModel(x, y, width, height, speed)
        );
        this.moveType = moveType;
    }

    public boolean isRocket() {
        return isRocket;
    }

    public void setRocket(boolean rocket) {
        isRocket = rocket;
    }

    public GameController getEnemy() {
        return enemy;
    }

    public void setEnemy(GameController enemy) {
        this.enemy = enemy;
    }

    public int getMoveType() {
        return moveType;
    }

    public void setMoveType(int moveType) {
        this.moveType = moveType;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public void onContact(GameController other) {
        if (other instanceof EnemyPlaneController) {
            setActive(false);
            if (((EnemyPlaneController) other).isBoss() == false) {
                ((EnemyPlaneController) other).setLife(0);
                other.setActive(false);
                ControllerManager.setScore(ControllerManager.getScore() + 1);
                System.out.println("Score: " + ControllerManager.getScore());
            } else {
                if (((EnemyPlaneController) other).getLife() > 0)
                    ((EnemyPlaneController) other).setLife(((EnemyPlaneController) other).getLife() - 1);
                if (((EnemyPlaneController) other).getLife() == 0) {
                    other.setActive(false);
                    ControllerManager.setScore(ControllerManager.getScore() + 10);
                    System.out.println("Score: " + ControllerManager.getScore());
                }
            }
        }
        if (other instanceof BombController) {
            other.setActive(false);
            ControllerManager.setScore(ControllerManager.getScore() + 1);
            System.out.println("Score: " + ControllerManager.getScore());
        }
        if (other instanceof EnemyBulletController) {
            if (isRocket() == false)
                setActive(false);
            other.setActive(false);
        }
    }

    public void run() {
        if (model instanceof PlayerBulletModel) {
            if (isActive() == false) {
                switch (getKill()) {
                    case 3:
                        getModel().setHeight(31);
                        getModel().setWidth(32);
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
            if (getModel().getY() < 0) setActive(false);
            switch (moveType) {
                case 0:
                    ((PlayerBulletModel) model).moveUp();
                    break;
                case 1:
                    ((PlayerBulletModel) model).moveRightUp();
                    break;
                case 2:
                    ((PlayerBulletModel) model).moveLeftUp();
                    break;
                case 3:
                    ((PlayerBulletModel) model).moveToEnemy(enemy);
                    break;
            }
        }
    }
}
