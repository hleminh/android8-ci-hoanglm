package controllers;

import java.awt.*;
import java.util.Iterator;
import java.util.Vector;

public class ControllerManager {
    private Vector<GameController> gameControllers = new Vector<GameController>();
    private Vector<GameController> playerBulletControllers = new Vector<GameController>();
    private Vector<GameController> enemyBulletControllers = new Vector<GameController>();
    private Vector<GameController> islandControllers = new Vector<GameController>();
    private Vector<GameController> backgroundControllers = new Vector<GameController>();
    private Vector<GameController> collidables = new Vector<GameController>();

    private int score;

    public Vector<GameController> getGameControllers() {
        return gameControllers;
    }

    public Vector<GameController> getPlayerBulletControllers() {
        return playerBulletControllers;
    }

    public Vector<GameController> getEnemyBulletControllers() {
        return enemyBulletControllers;
    }

    public Vector<GameController> getIslandControllers() {
        return islandControllers;
    }

    public Vector<GameController> getBackgroundControllers() {
        return backgroundControllers;
    }

    public Vector<GameController> getCollidables() {
        return collidables;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public synchronized void run() {
        Iterator<GameController> iterator1 = backgroundControllers.iterator();
        while (iterator1.hasNext()) {
            GameController el = iterator1.next();
            if (el instanceof BackgroundController) {
                ((BackgroundController) el).run();
            }
        }
        Iterator<GameController> iterator2 = islandControllers.iterator();
        while (iterator2.hasNext()) {
            GameController el = iterator2.next();
            if (el instanceof IslandController) {
                ((IslandController) el).run();
            }
        }
        Iterator<GameController> iterator3 = gameControllers.iterator();
        while (iterator3.hasNext()) {
            GameController el = iterator3.next();
            if (el instanceof BombController) {
                ((BombController) el).run();
            }
            if (el instanceof PowerUpController) {
                ((PowerUpController) el).run();
            }
            if (el instanceof PlayerPlaneController) {
                ((PlayerPlaneController) el).run();
            }
            if (el instanceof EnemyPlaneController) {
                ((EnemyPlaneController) el).run();
            }
        }
        Iterator<GameController> iterator4 = enemyBulletControllers.iterator();
        while (iterator4.hasNext()) {
            GameController el = iterator4.next();
            if (el instanceof EnemyBulletController) {
                ((EnemyBulletController) el).run();
            }
        }
        Iterator<GameController> iterator5 = playerBulletControllers.iterator();
        while (iterator5.hasNext()) {
            GameController el = iterator5.next();
            if (el instanceof PlayerBulletController) {
                ((PlayerBulletController) el).run();
            }
        }
        checkCollision();
        checkDeletion();
    }

    public void checkCollision(){
        for(GameController el1: collidables){
            if (el1.isActive() == true) {
                for (GameController el2 : collidables) {
                    if (el2.isActive() == true) {
                        if (el1.equals(el2) == false && el1.getModel().intersects(el2.getModel())) {
                            if ((el1 instanceof PlayerPlaneController && el2 instanceof EnemyBulletController) || (el1 instanceof PlayerPlaneController && el2 instanceof BombController)) {
                                if (((PlayerPlaneController) el1).isPower() == false)
                                    el1.setActive(false);
                                el2.setActive(false);
                            }
                            if (el1 instanceof PlayerBulletController && (el2 instanceof EnemyPlaneController || el2 instanceof BombController)) {
                                el1.setActive(false);
                                if (el2 instanceof EnemyPlaneController) {
                                    if (((EnemyPlaneController) el2).isBoss() == false) {
                                        ((EnemyPlaneController) el2).setLife(0);
                                        el2.setActive(false);
                                        score = score +1;
                                        System.out.println("Score: "+score);
                                    }
                                    else{
                                        if(((EnemyPlaneController) el2).getLife() >0)
                                            ((EnemyPlaneController) el2).setLife(((EnemyPlaneController) el2).getLife() - 1);
                                        if (((EnemyPlaneController) el2).getLife() == 0){
                                            el2.setActive(false);
                                            score = score + 10;
                                            System.out.println("Score: " +score);
                                        }
                                    }
                                }
                                if (el2 instanceof BombController){
                                    el2.setActive(false);
                                    score = score +1;
                                    System.out.println("Score: " +score);
                                }
                            }
                            if (el1 instanceof PlayerPlaneController && el2 instanceof PowerUpController) {
                                ((PlayerPlaneController) el1).setPower(true);
                                el2.setActive(false);
                            }
                            if (el1 instanceof PlayerBulletController && el2 instanceof EnemyBulletController){
                                el1.setActive(false);
                                el2.setActive(false);
                            }
                            if (el1 instanceof PlayerPlaneController && el2 instanceof EnemyPlaneController){
                                if (((PlayerPlaneController) el1).isPower() == false)
                                    el1.setActive(false);
                                if (((EnemyPlaneController) el2).isBoss() == false) {
                                    el2.setActive(false);
                                    ((EnemyPlaneController) el2).setLife(0);
                                }
                                else{
                                    ((EnemyPlaneController) el2).setLife(((EnemyPlaneController) el2).getLife() - 1);
                                }
                            }
                            if (el1 instanceof BombController && el2 instanceof EnemyPlaneController){
                                el1.setActive(false);
                                if (((EnemyPlaneController) el2).isBoss() == false) {
                                    el2.setActive(false);
                                    ((EnemyPlaneController) el2).setLife(0);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void checkDeletion(){
        Iterator<GameController> iterator2 = islandControllers.iterator();
        while (iterator2.hasNext()) {
            GameController el = iterator2.next();
            if (el instanceof IslandController) {
                if (el.isActive() == false) iterator2.remove();
            }
        }
        Iterator<GameController> iterator3 = gameControllers.iterator();
        while (iterator3.hasNext()) {
            GameController el = iterator3.next();
            if (el instanceof BombController) {
                if (el.isActive() == false && ((BombController) el).getKill() == 0) iterator3.remove();
            }
            if (el instanceof PowerUpController) {
                if (el.isActive() == false) iterator3.remove();
            }
            if (el instanceof PlayerPlaneController) {
                if (el.isActive() == false && ((PlayerPlaneController) el).getKill()== 0) iterator3.remove();
            }
            if (el instanceof EnemyPlaneController) {
                if (el.isActive() == false && ((EnemyPlaneController) el).getKill()==0 && ((EnemyPlaneController) el).getLife() ==0) iterator3.remove();
            }
        }
        Iterator<GameController> iterator4 = enemyBulletControllers.iterator();
        while (iterator4.hasNext()) {
            GameController el = iterator4.next();
            if (el instanceof EnemyBulletController) {
                if (el.isActive() == false) iterator4.remove();
            }
        }
        Iterator<GameController> iterator5 = playerBulletControllers.iterator();
        while (iterator5.hasNext()) {
            GameController el = iterator5.next();
            if (el instanceof PlayerBulletController) {
                if (el.isActive() == false && ((PlayerBulletController) el).getKill()==0) iterator5.remove();
            }
        }
    }

    public void draw(Graphics graphic) {
        Iterator<GameController> iterator1 = backgroundControllers.iterator();
        while (iterator1.hasNext()) {
            GameController el = iterator1.next();
            if (el instanceof BackgroundController) {
                ((BackgroundController) el).draw(graphic);
            }
        }
        Iterator<GameController> iterator2 = islandControllers.iterator();
        while (iterator2.hasNext()) {
            GameController el = iterator2.next();
            if (el instanceof IslandController) {
                ((IslandController) el).draw(graphic);
            }
        }

        Iterator<GameController> iterator4 = enemyBulletControllers.iterator();
        while (iterator4.hasNext()) {
            GameController el = iterator4.next();
            if (el instanceof EnemyBulletController) {
                ((EnemyBulletController) el).draw(graphic);
            }
        }

        Iterator<GameController> iterator3 = gameControllers.iterator();
        while (iterator3.hasNext()) {
            GameController el = iterator3.next();
            if (el instanceof BombController) {
                ((BombController) el).draw(graphic);
            }
            if (el instanceof PowerUpController) {
                ((PowerUpController) el).draw(graphic);
            }
            if (el instanceof PlayerPlaneController) {
                ((PlayerPlaneController) el).draw(graphic);
            }
            if (el instanceof EnemyPlaneController) {
                ((EnemyPlaneController) el).draw(graphic);
            }
        }
        Iterator<GameController> iterator5 = playerBulletControllers.iterator();
        while (iterator5.hasNext()) {
            GameController el = iterator5.next();
            if (el instanceof PlayerBulletController) {
                ((PlayerBulletController) el).draw(graphic);
            }
        }
    }
}