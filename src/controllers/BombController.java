package controllers;

import game.GameWindow;
import models.BombModel;
import utils.Utils;
import views.GameView;

import java.awt.*;

public class BombController extends GameController{
    private int kill = 6;
    private boolean power = false;
    private int invulnerable = 300;

    public BombController(GameView view, BombModel model) {
        super(view, model);
    }

    public BombController(int x, int y, int width, int height, int speed, Image image) {
        this(
                new GameView(image),
                new BombModel(x,y,width,height,speed)
        );
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

    public void onContact(GameController other){
        if (other instanceof EnemyPlaneController){
            setActive(false);
            if (((EnemyPlaneController) other).isBoss() == false) {
                other.setActive(false);
                ((EnemyPlaneController) other).setLife(0);
            }
        }
    }

    public void run() {
        if (model instanceof BombModel) {
            ((BombModel) model).moveDown();
            if (getModel().getY() > GameWindow.windowY) {
                setActive(false);
                setKill(0);
            }
            if (isActive() == false) {
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

}
