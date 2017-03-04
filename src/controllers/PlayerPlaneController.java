package controllers;

import game.GameWindow;
import models.PlayerPlaneModel;
import utils.Utils;
import views.GameView;

import java.awt.*;
import java.util.Vector;

public class PlayerPlaneController extends GameController {
    private int kill = 6;
    private boolean power = false;
    private int invulnerable = 300;
    private boolean right = false;
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;
    private boolean space = false;
    private int bulletDelay = 5;
    private static int playerX;
    private static int playerY;


    private Vector<GameController> bulletList;
    private Vector<GameController> collidables;

    public PlayerPlaneController(GameView view, PlayerPlaneModel model) {
        super(view, model);
    }

    public PlayerPlaneController(int x, int y, int width, int height, int speed, Image image, Vector<GameController> playerBulletControllers, Vector<GameController> collidables) {
        this(
                new GameView(image),
                new PlayerPlaneModel(x, y, width, height, speed)
        );
        this.bulletList = playerBulletControllers;
        this.collidables = collidables;
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

    public void setBulletList(Vector<GameController> bulletList) {
        this.bulletList = bulletList;
    }

    public void run() {
        if (model instanceof PlayerPlaneModel) {
            if (bulletDelay > 0) bulletDelay--;
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
                    getBulletList().add(playerBulletController);
                    collidables.add(playerBulletController);
                    bulletDelay = 5;
                }
                if (isPower() == true && bulletDelay == 0) {
                    PlayerBulletController playerBulletController1 = new PlayerBulletController(getModel().getX() + (getModel().getWidth() - 10) / 2, getModel().getY() - 10, 10, 30, GameWindow.BULLET_SPEED, 0, Utils.loadImageFromRes("bullet.png"));
                    PlayerBulletController playerBulletController2 = new PlayerBulletController(getModel().getX() + (getModel().getWidth() - 30) / 2, getModel().getY() - 10, 30, 30, GameWindow.BULLET_SPEED, 1, Utils.loadImageFromRes("bulletright.png"));
                    PlayerBulletController playerBulletController3 = new PlayerBulletController(getModel().getX() + (getModel().getWidth() - 30) / 2, getModel().getY() - 10, 30, 30, GameWindow.BULLET_SPEED, 2, Utils.loadImageFromRes("bulletleft.png"));
                    getBulletList().add(playerBulletController1);
                    getBulletList().add(playerBulletController2);
                    getBulletList().add(playerBulletController3);
                    collidables.add(playerBulletController1);
                    collidables.add(playerBulletController2);
                    collidables.add(playerBulletController3);
                    bulletDelay = 5;
                }
            }
            playerX = model.getX();
            playerY = model.getY();
        }
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

    public Vector<GameController> getBulletList() {
        return bulletList;
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
