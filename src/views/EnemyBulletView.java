package views;

import models.EnemyBulletModel;

import java.awt.*;

public class EnemyBulletView {
    private Image image;

    public EnemyBulletView(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void draw(Graphics graphic, EnemyBulletModel model){
        graphic.drawImage(image,model.getX(),model.getY(),model.getWidth(),model.getHeight(),null);
    }
}
