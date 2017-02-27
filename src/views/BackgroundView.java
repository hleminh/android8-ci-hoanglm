package views;

import models. BackgroundModel;

import java.awt.*;

public class BackgroundView {
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public  BackgroundView(Image image) {
        this.image = image;
    }

    public void draw(Graphics graphic,  BackgroundModel model){
        graphic.drawImage(image,model.getX(),model.getY(),model.getWidth(),model.getHeight(),null);
    }
}