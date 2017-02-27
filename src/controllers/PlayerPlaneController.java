package controllers;

import models.PlayerPlaneModel;
import utils.Utils;
import views.PlayerPlaneView;

import java.awt.*;
import java.util.ArrayList;

public class PlayerPlaneController {
    private boolean active = true;
    private int kill = 6;
    private boolean power = false;
    private int invulnerable = 300;

    private PlayerPlaneModel model;
    private PlayerPlaneView view;

    private ArrayList<PlayerBulletController> bulletList = new ArrayList<PlayerBulletController>();

    public PlayerPlaneController(PlayerPlaneModel model, PlayerPlaneView view) {
        this.model = model;
        this.view = view;

    }

    public PlayerPlaneController(int x, int y, int speed){
        this(
                new PlayerPlaneModel(x,y,9,20,speed),
                new PlayerPlaneView(Utils.loadImageFromRes("plane3.png"))
        );
    }

    public void run(){
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
                    setKill( getKill() - 1);
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
            if (getInvulnerable()== 0) {
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
    }

    public boolean isActive() {
        return active;
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

    public PlayerPlaneModel getModel() {
        return model;
    }

    public PlayerPlaneView getView() {
        return view;
    }

    public ArrayList<PlayerBulletController> getBulletList() {
        return bulletList;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public void draw(Graphics graphic){
        view.draw(graphic,model);
    }

}
