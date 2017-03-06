package controllers;

import models.BackgroundModel;
import views.GameView;

import java.awt.*;

public class BackgroundController extends GameController {
    private boolean active = true;
    private int kill = 6;
    private boolean power = false;
    private int invulnerable = 300;

    public BackgroundController(GameView view, BackgroundModel model) {
        super(view, model);
    }

    public BackgroundController(int x, int y, int width, int height, int speed, Image image) {
        this(
                new GameView(image),
                new BackgroundModel(x, y, width, height, speed)
        );
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

    public void run() {
        if (model instanceof BackgroundModel)
            ((BackgroundModel) model).moveDown();
    }

}