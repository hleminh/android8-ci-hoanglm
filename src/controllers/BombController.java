package controllers;

import models.BombModel;
import utils.Utils;
import views.BombView;

import java.awt.*;

public class BombController {
    private boolean active = true;
    private int kill = 6;
    private boolean power = false;
    private int invulnerable = 300;

    private BombModel model;
    private BombView view;



    public BombController(BombModel model, BombView view) {
        this.model = model;
        this.view = view;

    }

    public BombController(int x, int y,int width, int height, int speed, Image image){
        this(
                new BombModel(x,y,width,height,speed),
                new BombView(image)
        );
    }

    public BombModel getModel() {
        return model;
    }

    public BombView getView() {
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

    public void run(){
        model.moveDown();

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

    public void draw(Graphics graphic){
        view.draw(graphic,model);
    }
}
