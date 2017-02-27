package views;

import models.EnemyPlaneModel;

import java.awt.*;

public class EnemyPlaneView {
    private Image image;

    public EnemyPlaneView(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void draw(Graphics graphic, EnemyPlaneModel model){
        graphic.drawImage(image,model.getX(),model.getY(),model.getWidth(),model.getHeight(),null);
    }
}
