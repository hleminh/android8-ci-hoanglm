package controllers;

import models.GameModel;
import views.GameView;

import java.awt.*;

public class GameController {
    protected GameModel model;
    protected GameView view;

    private boolean active = true;

    public GameController(GameView view, GameModel model) {
        this.view = view;
        this.model = model;
    }

    public GameController(int x, int y, int width, int height, int speed, Image image) {
        this.model = new GameModel(x, y, width, height, speed);
        this.view = new GameView(image);
    }

    public GameModel getModel() {
        return model;
    }

    public GameView getView() {
        return view;
    }

    public void setModel(GameModel model) {
        this.model = model;
    }

    public void setView(GameView view) {
        this.view = view;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void run() {
    }

    public void onContact(GameController other) {

    }

    public void draw(Graphics graphic) {
        view.draw(graphic, model);
    }

}
