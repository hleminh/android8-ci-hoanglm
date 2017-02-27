package views;

import models.PlayerPlaneModel;

import java.awt.*;

public class PlayerPlaneView {
    private Image image;

    public PlayerPlaneView(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void draw(Graphics graphic, PlayerPlaneModel model){
        graphic.drawImage(image,model.getX(),model.getY(),model.getWidth(),model.getHeight(),null);
    }
}
