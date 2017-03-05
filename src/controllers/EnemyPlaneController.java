package controllers;

import game.GameWindow;
import models.EnemyPlaneModel;
import utils.Utils;
import views.GameView;

import java.awt.*;
import java.util.Random;

public class EnemyPlaneController extends GameController {
    private int kill = 6;
    private int life = 1;
    private boolean power = false;
    private int invulnerable = 300;
    private int moveType;
    private boolean isBoss = false;
    private boolean isDank = false;
    private int bulletDelay = 20;

    private ControllerManager bulletList;

    public EnemyPlaneController(GameView view, EnemyPlaneModel model) {
        super(view, model);
    }

    public EnemyPlaneController(int x, int y, int width, int height, int speed, Image image, ControllerManager gameControllers) {
        this(
                new GameView(image),
                new EnemyPlaneModel(x, y, width, height, speed)
        );
        this.bulletList = gameControllers;
    }


    public boolean isDank() {
        return isDank;
    }

    public void setDank(boolean dank) {
        isDank = dank;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    public int getInvulnerable() {
        return invulnerable;
    }

    public void setInvulnerable(int invulnerable) {
        this.invulnerable = invulnerable;
    }

    public int getMoveType() {
        return moveType;
    }

    public void setMoveType(int moveType) {
        this.moveType = moveType;
    }

    public boolean isBoss() {
        return isBoss;
    }

    public void setBoss(boolean boss) {
        isBoss = boss;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public ControllerManager getBulletList() {
        return bulletList;
    }

    public void setBulletList(ControllerManager bulletList) {
        this.bulletList = bulletList;
    }

    public synchronized void run() {
        if (model instanceof EnemyPlaneModel) {
            if (bulletDelay > 0)
                bulletDelay--;
            if (bulletDelay == 0) {
                if (isBoss() == false && isDank() == false) {
                    EnemyBulletController enemyBullet = new EnemyBulletController((getModel().getX() + (getModel().getWidth() - 10) / 2), getModel().getY() + 10, 9, 9, GameWindow.ENEMY_BULLET_SPEED, getMoveType(), Utils.loadImageFromRes("bullet-round.png"));
                    bulletList.getEnemyBulletControllers().add(enemyBullet);
                    bulletList.getCollidables().add(enemyBullet);
                    bulletDelay = 30;
                }
                if (isBoss() == true) {
                    EnemyBulletController enemyBullet1 = new EnemyBulletController((getModel().getX() + (getModel().getWidth() - 10) / 2), getModel().getY() + 10, 9, 9, GameWindow.ENEMY_BULLET_SPEED, 0, Utils.loadImageFromRes("bullet-round.png"));
                    EnemyBulletController enemyBullet2 = new EnemyBulletController((getModel().getX() + (getModel().getWidth() - 10) / 2), getModel().getY() + 10, 9, 9, GameWindow.ENEMY_BULLET_SPEED, 1, Utils.loadImageFromRes("bullet-left.png"));
                    EnemyBulletController enemyBullet3 = new EnemyBulletController((getModel().getX() + (getModel().getWidth() - 10) / 2), getModel().getY() + 10, 9, 9, GameWindow.ENEMY_BULLET_SPEED, 2, Utils.loadImageFromRes("bullet-right.png"));
                    bulletList.getEnemyBulletControllers().add(enemyBullet1);
                    bulletList.getCollidables().add(enemyBullet1);
                    bulletList.getEnemyBulletControllers().add(enemyBullet2);
                    bulletList.getCollidables().add(enemyBullet2);
                    bulletList.getEnemyBulletControllers().add(enemyBullet3);
                    bulletList.getCollidables().add(enemyBullet3);
                    bulletDelay = 30;
                }
                if (isDank() == true) {
                    EnemyBulletController enemyBullet = new EnemyBulletController((getModel().getX() + (getModel().getWidth() - 10) / 2), getModel().getY() + 10, 32, 31, GameWindow.ENEMY_BULLET_SPEED, 3, Utils.loadImageFromRes("dank.png"));
                    bulletList.getEnemyBulletControllers().add(enemyBullet);
                    bulletList.getCollidables().add(enemyBullet);
                    bulletDelay = 70;
                }
            }
            switch (moveType) {
                case 0:
                    ((EnemyPlaneModel) model).moveDown();
                    break;
                case 1:
                    ((EnemyPlaneModel) model).moveRightDown();
                    break;
                case 2:
                    ((EnemyPlaneModel) model).moveLeftDown();
                    break;
                case 3: {
                    ((EnemyPlaneModel) model).moveDown();
                    if (model.getY() >= 600) {
                        model.setY(0);
                        Random randomX = new Random();
                        model.setX(randomX.nextInt(400 - 42));
                    }
                    break;
                }
                case 4:
                    ((EnemyPlaneModel) model).moveToPlayer();
                    break;
                case 5:
                    ((EnemyPlaneModel) model).moveAwayFromPlayer();
                    break;
            }
        }
        if (getModel().getY() > GameWindow.windowY || getModel().getX() > GameWindow.windowX || getModel().getX() < 0) {
            if (isBoss() == false) {
                setActive(false);
                setKill(0);
                setLife(0);
            }
        }
        if (isActive() == false && isBoss() == false && getLife() == 0) {
            switch (getKill()) {
                case 6:
                    getView().setImage(Utils.loadImageFromRes("explosion6.png"));
                    setKill(getKill() - 1);
                    break;
                case 5:
                    getView().setImage(Utils.loadImageFromRes("explosion5.png"));
                    setKill(getKill() - 1);
                    break;
                case 4:
                    getView().setImage(Utils.loadImageFromRes("explosion4.png"));
                    setKill(getKill() - 1);
                    break;
                case 3:
                    getView().setImage(Utils.loadImageFromRes("explosion3.png"));
                    setKill(getKill() - 1);
                    break;
                case 2:
                    getView().setImage(Utils.loadImageFromRes("explosion2.png"));
                    setKill(getKill() - 1);
                    break;
                case 1:
                    getView().setImage(Utils.loadImageFromRes("explosion1.png"));
                    setKill(getKill() - 1);
                    break;
            }
        }
        if (isBoss() == true && getLife() == 1) {
            getView().setImage(Utils.loadImageFromRes("plane1-2.png"));
        }
        if (isActive() == false && isBoss() == true && getLife() == 0) {
            switch (getKill()) {
                case 6:
                    getView().setImage(Utils.loadImageFromRes("explosion6.png"));
                    setKill(getKill() - 1);
                    break;
                case 5:
                    getView().setImage(Utils.loadImageFromRes("explosion5.png"));
                    setKill(getKill() - 1);
                    break;
                case 4:
                    getView().setImage(Utils.loadImageFromRes("explosion4.png"));
                    setKill(getKill() - 1);
                    break;
                case 3:
                    getView().setImage(Utils.loadImageFromRes("explosion3.png"));
                    setKill(getKill() - 1);
                    break;
                case 2:
                    getView().setImage(Utils.loadImageFromRes("explosion2.png"));
                    setKill(getKill() - 1);
                    break;
                case 1:
                    getView().setImage(Utils.loadImageFromRes("explosion1.png"));
                    setKill(getKill() - 1);
                    break;
            }
        }
    }
}
