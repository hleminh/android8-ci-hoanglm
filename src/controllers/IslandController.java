package controllers;

import game.GameWindow;
import models.IslandModel;
import views.GameView;

import java.awt.*;

public class IslandController extends GameController {
    private boolean active = true;
    private int kill = 6;

    public IslandController(GameView view, IslandModel model) {
        super(view, model);
    }

    public IslandController(int x, int y, int width, int height, int speed, Image image) {
        this(
                new GameView(image),
                new IslandModel(x, y, width, height, speed)
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

    public void run() {
        if (model instanceof IslandModel)
            ((IslandModel) model).moveDown();
        if (getModel().getY() > GameWindow.windowY + 10 || getModel().getX() > GameWindow.windowX + 10 || getModel().getX() < -10)
            setActive(false);
    }

}

