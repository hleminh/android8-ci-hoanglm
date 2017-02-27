package views;

import models. IslandModel;

import java.awt.*;

public class IslandView {
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public  IslandView(Image image) {
        this.image = image;
    }

    public void draw(Graphics graphic,  IslandModel model){
        graphic.drawImage(image,model.getX(),model.getY(),model.getWidth(),model.getHeight(),null);
    }
}
