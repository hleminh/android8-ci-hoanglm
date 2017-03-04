package models;

public class IslandModel extends GameModel {

    public IslandModel(int x, int y, int width, int height, int speed) {
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
