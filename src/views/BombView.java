package views;

import models. BombModel;

import java.awt.*;

public class BombView {
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public  BombView(Image image) {
        this.image = image;
    }

    public void draw(Graphics graphic,  BombModel model){
        graphic.drawImage(image,model.getX(),model.getY(),model.getWidth(),model.getHeight(),null);
    }
}
