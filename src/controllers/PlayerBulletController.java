package controllers;

import models.PlayerBulletModel;
import utils.Utils;
import views.PlayerBulletView;

import java.awt.*;
import java.util.ArrayList;

public class PlayerBulletController {

    private PlayerBulletView view;

    private PlayerBulletModel model;

    private boolean active = true;

    private int moveType;


    public PlayerBulletController(PlayerBulletModel model, PlayerBulletView view) {
        this.model = model;
        this.view = view;

    }

    public PlayerBulletController(int x, int y, int width,int height, int speed, int moveType, Image image){
        this(
                new PlayerBulletModel(x,y,width,height,speed),
                new PlayerBulletView(image)
        );
        this.moveType = moveType;
    }

    public PlayerBulletModel getModel() {
        return model;
    }

    public void setModel(PlayerBulletModel model) {
        this.model = model;
    }

    public PlayerBulletView getView() {
        return view;
    }

    public void setView(PlayerBulletView view) {
        this.view = view;
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

    public void run(){
        switch (moveType) {
            case 0:
                model.moveUp();
                break;
            case 1:
                model.moveRightUp();
                break;
            case 2:
                model.moveLeftUp();
                break;
        }
    }

    public void draw(Graphics graphic){
        view.draw(graphic,model);
    }
}
