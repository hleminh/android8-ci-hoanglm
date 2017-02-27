package controllers;

import models.EnemyPlaneModel;
import utils.Utils;
import views.EnemyPlaneView;
import java.util.Random;
import java.awt.*;
import java.util.ArrayList;

public class EnemyPlaneController {
    private boolean active = true;
    private int kill = 6;
    private int life = 1;
    private boolean power = false;
    private int invulnerable = 300;
    private int moveType;
    private boolean isBoss = false;

    private EnemyPlaneModel model;
    private EnemyPlaneView view;

    private ArrayList<EnemyBulletController> bulletList = new ArrayList<EnemyBulletController>();


    public EnemyPlaneController(EnemyPlaneModel model, EnemyPlaneView view) {
        this.model = model;
        this.view = view;

    }

    public EnemyPlaneController(int x, int y,int width,int height, int speed, Image image){
        this(
                new EnemyPlaneModel(x,y,width,height,speed),
                new EnemyPlaneView(image)
        );
    }

    public EnemyPlaneModel getModel() {
        return model;
    }

    public EnemyPlaneView getView() {
        return view;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public ArrayList<EnemyBulletController> getBulletList() {
        return bulletList;
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

    public void run(){
        switch (moveType) {
            case 0:
                if (isBoss()==true){
                   model.moveDown();
                   if (model.getY() >= 600){
                       model.setY(0);
                       Random randomX = new Random();
                       model.setX(randomX.nextInt(400 - 42));
                   }
                }
                if (isBoss()==false) {
                    model.moveDown();
                }
                break;
            case 1:
                model.moveRightDown();
                break;
            case 2:
                model.moveLeftDown();
                break;
        }
        if (isActive() == false && isBoss()==false && getLife() == 0) {
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
        if (isActive() == false && isBoss()==true && getLife()==0) {
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

    public void draw(Graphics graphic){
        view.draw(graphic,model);
    }
}
