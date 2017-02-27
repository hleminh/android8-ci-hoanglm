package controllers;

import models.BackgroundModel;
import utils.Utils;
import views.BackgroundView;

import java.awt.*;

public class BackgroundController {
    private boolean active = true;
    private int kill = 6;
    private boolean power = false;
    private int invulnerable = 300;

    private BackgroundModel model;
    private BackgroundView view;



    public BackgroundController(BackgroundModel model, BackgroundView view) {
        this.model = model;
        this.view = view;

    }

    public BackgroundController(int x, int y,int width, int height, int speed, Image image){
        this(
                new BackgroundModel(x,y,width,height,speed),
                new BackgroundView(image)
        );
    }

    public BackgroundModel getModel() {
        return model;
    }

    public BackgroundView getView() {
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
    }

    public void draw(Graphics graphic){
        view.draw(graphic,model);
    }
}