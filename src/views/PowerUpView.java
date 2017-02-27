package views;

import models.PowerUpModel;

import java.awt.*;

public class PowerUpView {
    private Image image;

    public  PowerUpView(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void draw(Graphics graphic, PowerUpModel model){
        graphic.drawImage(image,model.getX(),model.getY(),model.getWidth(),model.getHeight(),null);
    }
}
