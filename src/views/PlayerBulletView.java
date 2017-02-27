package views;

import models.PlayerBulletModel;

import java.awt.*;

public class PlayerBulletView {
    private Image image;

    public PlayerBulletView(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void draw(Graphics graphic, PlayerBulletModel model){
        graphic.drawImage(image,model.getX(),model.getY(),model.getWidth(),model.getHeight(),null);
    }

}
