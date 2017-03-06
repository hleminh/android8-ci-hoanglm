package controllers;

import java.awt.*;
import java.util.Iterator;
import java.util.Vector;

public class ControllerManager {
    private static Vector<GameController> gameControllers = new Vector<GameController>();
    private static Vector<GameController> playerBulletControllers = new Vector<GameController>();
    private static Vector<GameController> enemyBulletControllers = new Vector<GameController>();
    private static Vector<GameController> islandControllers = new Vector<GameController>();
    private static Vector<GameController> backgroundControllers = new Vector<GameController>();
    private static Vector<GameController> collidables = new Vector<GameController>();

    private static int score;

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

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        ControllerManager.score = score;
    }

    public GameController getEnemy() {
        for (GameController el : gameControllers) {
            if (el.isActive() == true && el instanceof EnemyPlaneController)
                return el;
        }
        return null;
    }

    public synchronized void run() {
        Iterator<GameController> iterator1 = backgroundControllers.iterator();
        while (iterator1.hasNext()) {
            GameController el = iterator1.next();
            el.run();
        }
        Iterator<GameController> iterator2 = islandControllers.iterator();
        while (iterator2.hasNext()) {
            GameController el = iterator2.next();
            el.run();
        }
        Iterator<GameController> iterator3 = gameControllers.iterator();
        while (iterator3.hasNext()) {
            GameController el = iterator3.next();
            el.run();
        }
        Iterator<GameController> iterator4 = enemyBulletControllers.iterator();
        while (iterator4.hasNext()) {
            GameController el = iterator4.next();
            el.run();
        }
        Iterator<GameController> iterator5 = playerBulletControllers.iterator();
        while (iterator5.hasNext()) {
            GameController el = iterator5.next();
            el.run();
        }
        checkCollision();
        checkDeletion();
    }

    public void checkCollision() {
        for (GameController el1 : collidables) {
            if (el1.isActive() == true) {
                for (GameController el2 : collidables) {
                    if (el2.isActive() == true) {
                        if (el1.equals(el2) == false && el1.getModel().intersects(el2.getModel())) {
                            el1.onContact(el2);
                        }
                    }
                }
            }
        }
    }

    public void checkDeletion() {
        Iterator<GameController> iterator2 = islandControllers.iterator();
        while (iterator2.hasNext()) {
            GameController el = iterator2.next();
            if (el.isActive() == false) iterator2.remove();
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
                if (el.isActive() == false && ((PlayerPlaneController) el).getKill() == 0) iterator3.remove();
            }
            if (el instanceof EnemyPlaneController) {
                if (el.isActive() == false && ((EnemyPlaneController) el).getKill() == 0 && ((EnemyPlaneController) el).getLife() == 0)
                    iterator3.remove();
            }
        }
        Iterator<GameController> iterator4 = enemyBulletControllers.iterator();
        while (iterator4.hasNext()) {
            GameController el = iterator4.next();
            if (el.isActive() == false) iterator4.remove();
        }
        Iterator<GameController> iterator5 = playerBulletControllers.iterator();
        while (iterator5.hasNext()) {
            GameController el = iterator5.next();
            if (el.isActive() == false && ((PlayerBulletController) el).getKill() == 0) iterator5.remove();
        }
    }

    public void draw(Graphics graphic) {
        Iterator<GameController> iterator1 = backgroundControllers.iterator();
        while (iterator1.hasNext()) {
            GameController el = iterator1.next();
            el.draw(graphic);
        }
        Iterator<GameController> iterator2 = islandControllers.iterator();
        while (iterator2.hasNext()) {
            GameController el = iterator2.next();
            el.draw(graphic);
        }
        Iterator<GameController> iterator4 = enemyBulletControllers.iterator();
        while (iterator4.hasNext()) {
            GameController el = iterator4.next();
            el.draw(graphic);
        }
        Iterator<GameController> iterator3 = gameControllers.iterator();
        while (iterator3.hasNext()) {
            GameController el = iterator3.next();
            el.draw(graphic);
        }
        Iterator<GameController> iterator5 = playerBulletControllers.iterator();
        while (iterator5.hasNext()) {
            GameController el = iterator5.next();
            el.draw(graphic);
        }
    }
}