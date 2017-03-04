package views;

import models.GameModel;

import java.awt.*;

public class GameView {
    protected Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public  GameView(Image image) {
        this.image = image;
    }

    public void draw(Graphics graphic,  GameModel model){
        graphic.drawImage(image,model.getX(),model.getY(),model.getWidth(),model.getHeight(),null);
    }
}
