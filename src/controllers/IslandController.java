package controllers;

import models.IslandModel;
import utils.Utils;
import views.IslandView;

import java.awt.*;

public class IslandController {
    private boolean active = true;
    private int kill = 6;
    private boolean power = false;
    private int invulnerable = 300;

    private IslandModel model;
    private IslandView view;



    public IslandController(IslandModel model, IslandView view) {
        this.model = model;
        this.view = view;

    }

    public IslandController(int x, int y, int width, int height, int speed, Image image){
        this(
                new IslandModel(x,y,width,height,speed),
                new IslandView(image)
        );
    }

    public IslandModel getModel() {
        return model;
    }

    public IslandView getView() {
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

