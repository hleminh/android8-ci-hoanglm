package controllers;

import game.GameWindow;
import models.PlayerPlaneModel;
import utils.Utils;
import views.GameView;

import java.awt.*;

public class PlayerPlaneController extends GameController {
    private int kill = 6;
    private boolean power = false;
    private int invulnerable = 300;
    private boolean right = false;
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;
    private boolean space = false;
    private boolean control = false;
    private boolean shift = false;
    private int bulletDelay = 5;
    private int rocketDelay = 10;
    private int nuclearDelay = 100;
    private static int playerX;
    private static int playerY;

    private ControllerManager bulletList;

    public PlayerPlaneController(GameView view, PlayerPlaneModel model) {
        super(view, model);
    }

    public PlayerPlaneController(int x, int y, int width, int height, int speed, Image image, ControllerManager gameControllers) {
        this(
                new GameView(image),
                new PlayerPlaneModel(x, y, width, height, speed)
        );
        this.bulletList = gameControllers;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setSpace(boolean space) {
        this.space = space;
    }

    public boolean isSpace() {
        return space;
    }

    public int getBulletDelay() {
        return bulletDelay;
    }

    public void setBulletDelay(int bulletDelay) {
        this.bulletDelay = bulletDelay;
    }

    public ControllerManager getBulletList() {
        return bulletList;
    }

    public boolean isShift() {
        return shift;
    }

    public void setShift(boolean shift) {
        this.shift = shift;
    }

    public void run() {
        if (model instanceof PlayerPlaneModel) {
            if (bulletDelay > 0) bulletDelay--;
            if (rocketDelay > 0) rocketDelay--;
            if (nuclearDelay > 0) nuclearDelay--;
            if (right == true && (getModel().getX() + getModel().getSpeed() <= GameWindow.windowX - getModel().getWidth() - 3 && super.isActive() == true))
                model.setX(model.getX() + model.getSpeed());
            if (left == true && (getModel().getX() - getModel().getSpeed() >= 3 && super.isActive() == true))
                model.setX(model.getX() - model.getSpeed());
            if (up == true && (getModel().getY() - getModel().getSpeed() >= getModel().getHeight() / 2 && super.isActive() == true))
                model.setY(model.getY() - model.getSpeed());
            if (down == true && (getModel().getY() + getModel().getSpeed() <= GameWindow.windowY - getModel().getHeight() - 5 && super.isActive() == true))
                model.setY(model.getY() + model.getSpeed());

            if (isActive() == false) {
                getModel().setWidth(64);
                getModel().setHeight(64);
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

            if (isPower() == true) {
                getView().setImage(Utils.loadImageFromRes("plane4.png"));
                setInvulnerable(getInvulnerable() - 1);
                if (getInvulnerable() == 0) {
                    getView().setImage(Utils.loadImageFromRes("plane3.png"));
                    setPower(false);
                    setInvulnerable(300);
                }
            }

            if (isPower() == true) {
                switch (getInvulnerable()) {
                    case 50:
                        getView().setImage(Utils.loadImageFromRes("plane3.png"));
                        break;
                    case 40:
                        getView().setImage(Utils.loadImageFromRes("plane3.png"));
                        break;
                    case 30:
                        getView().setImage(Utils.loadImageFromRes("plane3.png"));
                        break;
                    case 20:
                        getView().setImage(Utils.loadImageFromRes("plane3.png"));
                        break;
                    case 10:
                        getView().setImage(Utils.loadImageFromRes("plane3.png"));
                        break;
                }
            }
            if (space == true) {
                if (isPower() == false && bulletDelay == 0) {
                    PlayerBulletController playerBulletController = new PlayerBulletController((getModel().getX() + (getModel().getWidth() - 10) / 2), getModel().getY() - 7, 10, 30, GameWindow.BULLET_SPEED, 0, Utils.loadImageFromRes("bullet.png"));
                    bulletList.getPlayerBulletControllers().add(playerBulletController);
                    bulletList.getCollidables().add(playerBulletController);
                    bulletDelay = 5;
                }
                if (isPower() == true && bulletDelay == 0) {
                    PlayerBulletController playerBulletController1 = new PlayerBulletController(getModel().getX() + (getModel().getWidth() - 10) / 2, getModel().getY() - 10, 10, 30, GameWindow.BULLET_SPEED, 0, Utils.loadImageFromRes("bullet.png"));
                    PlayerBulletController playerBulletController2 = new PlayerBulletController(getModel().getX() + (getModel().getWidth() - 30) / 2, getModel().getY() - 10, 30, 30, GameWindow.BULLET_SPEED, 1, Utils.loadImageFromRes("bulletright.png"));
                    PlayerBulletController playerBulletController3 = new PlayerBulletController(getModel().getX() + (getModel().getWidth() - 30) / 2, getModel().getY() - 10, 30, 30, GameWindow.BULLET_SPEED, 2, Utils.loadImageFromRes("bulletleft.png"));
                    bulletList.getPlayerBulletControllers().add(playerBulletController1);
                    bulletList.getCollidables().add(playerBulletController1);
                    bulletList.getPlayerBulletControllers().add(playerBulletController2);
                    bulletList.getCollidables().add(playerBulletController2);
                    bulletList.getPlayerBulletControllers().add(playerBulletController3);
                    bulletList.getCollidables().add(playerBulletController3);
                    bulletDelay = 5;
                }
            }
            if (control == true && rocketDelay == 0) {
                PlayerBulletController playerBulletController = new PlayerBulletController(getModel().getX() + (getModel().getWidth() - 14) / 2, getModel().getY() - 10, 14, 61, GameWindow.BULLET_SPEED, 3, Utils.loadImageFromRes("rocket.png"));
                playerBulletController.setEnemy(bulletList.getEnemy());
                playerBulletController.setRocket(true);
                bulletList.getPlayerBulletControllers().add(playerBulletController);
                bulletList.getCollidables().add(playerBulletController);
                rocketDelay = 10;
            }
            if (shift == true && nuclearDelay == 0) {
                PlayerBulletController playerBulletController = new PlayerBulletController(getModel().getX() + (getModel().getWidth() - 32) / 2, getModel().getY() - 10, 32, 64, GameWindow.BULLET_SPEED - 5, 3, Utils.loadImageFromRes("nuclear.png"));
                playerBulletController.setNuclear(true);
                playerBulletController.setKill(14);
                bulletList.getPlayerBulletControllers().add(playerBulletController);
                bulletList.getCollidables().add(playerBulletController);
                nuclearDelay = 100;
            }
            playerX = model.getX();
            playerY = model.getY();
        }
    }

    public void onContact(GameController other) {
        if (other instanceof EnemyBulletController) {
            if (isPower() == false)
                setActive(false);
            other.setActive(false);
        }
        if (other instanceof PowerUpController) {
            setPower(true);
            other.setActive(false);
        }
        if (other instanceof EnemyPlaneController) {
            if (isPower() == false)
                setActive(false);
            if (((EnemyPlaneController) other).isBoss() == false) {
                other.setActive(false);
                ((EnemyPlaneController) other).setLife(0);
            } else {
                ((EnemyPlaneController) other).setLife(((EnemyPlaneController) other).getLife() - 1);
            }
        }
        if (other instanceof BombController) {
            setActive(false);
            other.setActive(false);
        }
    }

    public boolean isControl() {
        return control;
    }

    public void setControl(boolean control) {
        this.control = control;
    }

    public static int getPlayerX() {
        return playerX;
    }

    public static int getPlayerY() {
        return playerY;
    }

    public int getKill() {
        return kill;
    }

    public boolean isPower() {
        return power;
    }

    public int getInvulnerable() {
        return invulnerable;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    public void setInvulnerable(int invulnerable) {
        this.invulnerable = invulnerable;
    }


}
