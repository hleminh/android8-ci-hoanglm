package controllers;

import models.PowerUpModel;
import utils.Utils;
import views.PowerUpView;

import java.awt.*;

public class PowerUpController {
    private boolean active = true;
    private int kill = 6;
    private boolean power = false;
    private int invulnerable = 300;

    private PowerUpModel model;
    private PowerUpView view;



    public PowerUpController(PowerUpModel model, PowerUpView view) {
        this.model = model;
        this.view = view;

    }

    public PowerUpController(int x, int y, int width, int height, int speed, Image image){
        this(
                new PowerUpModel(x,y,width,height,speed),
                new PowerUpView(image)
        );
    }

    public PowerUpModel getModel() {
        return model;
    }

    public PowerUpView getView() {
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
