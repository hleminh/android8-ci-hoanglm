package controllers;

import game.GameWindow;
import models.PowerUpModel;
import views.GameView;

import java.awt.*;

public class PowerUpController extends GameController {
    private int kill = 6;
    private boolean power = false;
    private int invulnerable = 300;

    public PowerUpController(GameView view, PowerUpModel model) {
        super(view, model);
    }

    public PowerUpController(int x, int y, int width, int height, int speed, Image image) {
        this(
                new GameView(image),
                new PowerUpModel(x, y, width, height, speed)
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

    public void run() {
        if (model instanceof PowerUpModel) {
            ((PowerUpModel) model).moveDown();
            if (getModel().getY() > GameWindow.windowY) {
                setActive(false);
            }
        }
    }

}
