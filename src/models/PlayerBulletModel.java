package models;

public class PlayerBulletModel extends  GameModel{


    public PlayerBulletModel(int x, int y, int width, int height, int speed) {
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

    public void moveRightUp(){
        y -= speed;
        x += speed - 3;
    }

    public void moveLeftUp(){
        y -= speed;
        x -= speed - 3;
    }
}
