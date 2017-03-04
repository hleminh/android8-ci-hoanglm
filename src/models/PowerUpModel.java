package models;

public class PowerUpModel extends GameModel{


    public PowerUpModel(int x, int y, int width, int height, int speed) {
        super(x, y, width, height, speed);
    }

    public void moveUp(){

        y -= speed;
    }
    public void moveDown(){

        y += speed;
    }
    public void moveRight(){

        x += speed;
    }
    public void moveLeft(){

        x -= speed;
    }
}
