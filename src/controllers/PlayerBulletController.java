package controllers;

import models.PlayerBulletModel;
import strategies.*;
import utils.Utils;
import views.GameView;

import java.awt.*;

public class PlayerBulletController extends GameController {

    private int moveType;
    private int kill = 3;
    private GameController enemy;
    private boolean isRocket = false;
    private boolean triggered = false;
    private boolean isNuclear = false;
    MoveBehavior moveBehavior;

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

    public boolean isNuclear() {
        return isNuclear;
    }

    public void setNuclear(boolean nuclear) {
        isNuclear = nuclear;
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

    public boolean isTriggered() {
        return triggered;
    }

    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }

    public void setMoveBehavior(MoveBehavior moveBehavior) {
        this.moveBehavior = moveBehavior;
    }

    public void onContact(GameController other) {
        if (other instanceof EnemyPlaneController) {
            if (isNuclear() == false)
                setActive(false);
            if (((EnemyPlaneController) other).isBoss() == false && ((EnemyPlaneController) other).isDank() == false) {
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
            if (isRocket() == false && isNuclear() == false)
                setActive(false);
            other.setActive(false);
        }
        if (other instanceof PlayerBulletController){
            if (((PlayerBulletController) other).isNuclear() == true){
                setActive(false);
                if (((PlayerBulletController) other).isTriggered()==false)
                    ((PlayerBulletController) other).setTriggered(true);
            }
        }
    }

    public void run() {
        if (model instanceof PlayerBulletModel) {
            if (isActive() == false && isNuclear()==false) {
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
            if (isNuclear() == true && isTriggered() == true){
                model.setWidth(400);
                model.setHeight(600);
                model.setX(0);
                model.setY(0);
                switch (getKill()) {
                    case 14:
                        getView().setImage(Utils.loadImageFromRes("0.png"));
                        setKill(getKill() - 1);
                        break;
                    case 13:
                        getView().setImage(Utils.loadImageFromRes("1.png"));
                        setKill(getKill() - 1);
                        break;
                    case 12:
                        getView().setImage(Utils.loadImageFromRes("2.png"));
                        setKill(getKill() - 1);
                        break;
                    case 11:
                        getView().setImage(Utils.loadImageFromRes("3.png"));
                        setKill(getKill() - 1);
                        break;
                    case 10:
                        getView().setImage(Utils.loadImageFromRes("4.png"));
                        setKill(getKill() - 1);
                        break;
                    case 9:
                        getView().setImage(Utils.loadImageFromRes("5.png"));
                        setKill(getKill() - 1);
                        break;
                    case 8:
                        getView().setImage(Utils.loadImageFromRes("6.png"));
                        setKill(getKill() - 1);
                        break;
                    case 7:
                        getView().setImage(Utils.loadImageFromRes("7.png"));
                        setKill(getKill() - 1);
                        break;
                    case 6:
                        getView().setImage(Utils.loadImageFromRes("8.png"));
                        setKill(getKill() - 1);
                        break;
                    case 5:
                        getView().setImage(Utils.loadImageFromRes("9.png"));
                        setKill(getKill() - 1);
                        break;
                    case 4:
                        getView().setImage(Utils.loadImageFromRes("10.png"));
                        setKill(getKill() - 1);
                        break;
                    case 3:
                        getView().setImage(Utils.loadImageFromRes("11.png"));
                        setKill(getKill() - 1);
                        break;
                    case 2:
                        getView().setImage(Utils.loadImageFromRes("12.png"));
                        setKill(getKill() - 1);
                        break;
                    case 1:
                        getView().setImage(Utils.loadImageFromRes("13.png"));
                        setKill(getKill() - 1);
                        break;
                    case 0:
                        setActive(false);
                        break;
                }
            }
            if (getModel().getY() < 0) setActive(false);
            switch (moveType) {
                case 0:
                    setMoveBehavior(new MoveUpBehavior());
                    moveBehavior.move(this.model);
                    break;
                case 1:
                    setMoveBehavior(new MoveRightUpBehavior());
                    moveBehavior.move(this.model);
                    break;
                case 2:
                    setMoveBehavior(new MoveLeftUpBehavior());
                    moveBehavior.move(this.model);
                    break;
                case 3:
                    ((PlayerBulletModel) model).moveToEnemy(enemy);
                    break;
            }
        }
    }
}
