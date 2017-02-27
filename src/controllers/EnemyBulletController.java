package controllers;

import models.EnemyBulletModel;
import utils.Utils;
import views.EnemyBulletView;

import java.awt.*;
import java.util.ArrayList;

public class EnemyBulletController {
    private EnemyBulletModel model;
    private EnemyBulletView view;
    private boolean active = true;
    private int moveType;

    public EnemyBulletController(EnemyBulletModel model, EnemyBulletView view) {
            this.model = model;
            this.view = view;
    }

    public EnemyBulletController(int x, int y, int width,int height, int speed, int moveType, Image image){
        this(
                new EnemyBulletModel(x,y,width,height,speed),
                new EnemyBulletView(image)
        );
        this.moveType = moveType;
    }

    public EnemyBulletModel getModel() {
        return model;
    }

    public EnemyBulletView getView() {
        return view;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getMoveType() {
        return moveType;
    }

    public void setMoveType(int moveType) {
        this.moveType = moveType;
    }

    public void run() {
        switch (moveType) {
            case 0:
                model.moveDown();
                break;
            case 1:
                view.setImage(Utils.loadImageFromRes("bullet-right.png"));
                model.setWidth(13);
                model.setHeight(13);
                model.moveRightDown();
                break;
            case 2:
                view.setImage(Utils.loadImageFromRes("bullet-left.png"));
                model.setWidth(13);
                model.setHeight(13);
                model.moveLeftDown();
                break;
        }
    }

    public void draw(Graphics graphic){
            view.draw(graphic,model);
        }
}
