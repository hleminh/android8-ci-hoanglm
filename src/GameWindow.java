import controllers.PlayerBulletController;
import models.*;
import views.*;
import controllers.*;

import utils.Utils;
import views.PlayerBulletView;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Random;

public class GameWindow extends Frame {
    private static final int SPEED = 10;
    private static final int BULLET_SPEED = 10;
    private static final int ENEMY_SPEED = 4;
    private static final int ENEMY_BULLET_SPEED = 5;
    private static final int BACKGROUND_SPEED = 1;
    private int score = 0;

    private static final int windowX = 400;
    private static final int windowY = 600;

    private int planeWidth = 60;
    private int planeHeight = 41;

    private int planeX = (windowX - planeWidth) / 2;
    private int planeY = windowY - planeHeight - 5;


    private BufferedImage backBufferImage;
    private Graphics backGraphics;

    private Graphics g;

    private int clockEnemy = 0;

    private int clockIsland = 0;

    private int clockBomb = 0;

    private int clockPowerUp = 0;

    private int clockBoss = 0;

    private boolean bossActive = false;

    private boolean active = true;

    private PlayerPlane playerPlane;

    private BackgroundController background;
    private BackgroundController backgroundBuffer;
    private BackgroundController gameover;
    private BackgroundController paused;
    private BackgroundController wasted;

    private ArrayList<EnemyPlaneController> enemyPlaneControllers = new ArrayList<EnemyPlaneController>();

    private ArrayList<IslandController> isLands = new ArrayList<IslandController>();

    private ArrayList<BombController> Bombs = new ArrayList<BombController>();

    private ArrayList<PowerUpController> powerUps = new ArrayList<PowerUpController>();

    private ArrayList<EnemyBulletController> enemyBulletStray = new ArrayList<EnemyBulletController>();

    private PlayerPlaneController playerPlaneController;

    private Thread thread;

    public GameWindow() {
        setVisible(true);
        setSize(windowX, windowY);
        System.out.println("RULES:\n - Shoot down enemy's airplanes and bomb.\n - Avoid enemy's bullets and bomb.\n - Collect power-ups to be invunerable for a short period of time and shoots stronger bullets.\n - Each diagonally moving airplane rewards 1pt since they're easier to shoot.\n - Each straightly moving airplane rewards 2pt since they're harder to shoot.\n - Each bomb rewards 1pt.\n - Each boss rewards 10pt.\n Good luck!\nCONTROLS:\n - ARROW KEYS to move.\n - SPACE to shoot.\n - ENTER to pause.\nGAME LOGS:");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                //System.out.println("windowClosing");
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                System.out.printf("windowClosed");
            }
        });

        // 1: Load image
        background = new BackgroundController(0,0,windowX,windowY,BACKGROUND_SPEED, Utils.loadImageFromRes("background.png"));
        backgroundBuffer = new BackgroundController(0,-600,windowX,windowY,BACKGROUND_SPEED,Utils.loadImageFromRes("background.png"));
        gameover = new BackgroundController(0,0,windowX,windowY,BACKGROUND_SPEED,Utils.loadImageFromRes("gameover.png"));
        paused = new BackgroundController(0,0,windowX,windowY,BACKGROUND_SPEED,Utils.loadImageFromRes("paused.png"));
        wasted = new BackgroundController(0,0,windowX,windowY,BACKGROUND_SPEED,Utils.loadImageFromRes("wasted.png"));

        // 2: Draw image
        repaint();

        // 3: Initialize player plane
        PlayerPlaneModel model = new PlayerPlaneModel(planeX,planeY,planeWidth,planeHeight,SPEED);
        PlayerPlaneView view = new PlayerPlaneView(Utils.loadImageFromRes("plane3.png"));
        playerPlaneController = new PlayerPlaneController(model,view);

        // 4: Initialize moving function
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT: {
                        if (playerPlaneController.getModel().getX() + playerPlaneController.getModel().getSpeed() <= background.getModel().getWidth() - playerPlaneController.getModel().getWidth() - 3 && active == true)
                            playerPlaneController.getModel().moveRight();
                    }
                    break;
                    case KeyEvent.VK_LEFT: {
                        if (playerPlaneController.getModel().getX() - playerPlaneController.getModel().getSpeed() >= 3 && active == true)
                            playerPlaneController.getModel().moveLeft();
                    }
                    break;
                    case KeyEvent.VK_UP: {
                       if (playerPlaneController.getModel().getY() - playerPlaneController.getModel().getSpeed() >= playerPlaneController.getModel().getHeight()/2 && active == true)
                           playerPlaneController.getModel().moveUp();
                    }
                    break;
                    case KeyEvent.VK_DOWN: {
                        if (playerPlaneController.getModel().getY()+ playerPlaneController.getModel().getSpeed() <= background.getModel().getHeight() - playerPlaneController.getModel().getHeight() - 5 && active == true)
                            playerPlaneController.getModel().moveDown();
                    }
                    break;
                    case KeyEvent.VK_ENTER: {
                        if (active == true){
                            System.out.println("Paused");
                            active = false;
                        }
                        else{
                            active = true;
                            System.out.println("Unpaused");
                        }
                    }break;
                    case KeyEvent.VK_SPACE: {
                        if (active == true) {
                            if (playerPlaneController.isPower() == false) {
                                PlayerBulletController playerBulletController = new PlayerBulletController((playerPlaneController.getModel().getX() + (playerPlaneController.getModel().getWidth() - 10) / 2), playerPlaneController.getModel().getY() - 7, 10, 30, BULLET_SPEED, 0, Utils.loadImageFromRes("bullet.png"));
                                playerPlaneController.getBulletList().add(playerBulletController);
                            }
                            if (playerPlaneController.isPower() == true) {
                                PlayerBulletController playerBulletController1 = new PlayerBulletController(playerPlaneController.getModel().getX() + (playerPlaneController.getModel().getWidth() - 10) / 2, playerPlaneController.getModel().getY() - 10, 10, 30, BULLET_SPEED, 0,Utils.loadImageFromRes("bullet.png"));
                                PlayerBulletController playerBulletController2 = new PlayerBulletController(playerPlaneController.getModel().getX() + (playerPlaneController.getModel().getWidth() - 30) / 2, playerPlaneController.getModel().getY() - 10, 30, 30, BULLET_SPEED, 1,Utils.loadImageFromRes("bulletright.png"));
                                PlayerBulletController playerBulletController3 = new PlayerBulletController(playerPlaneController.getModel().getX() + (playerPlaneController.getModel().getWidth() - 30) / 2, playerPlaneController.getModel().getY() - 10, 30, 30, BULLET_SPEED, 2,Utils.loadImageFromRes("bulletleft.png"));
                                playerPlaneController.getBulletList().add(playerBulletController1);
                                playerPlaneController.getBulletList().add(playerBulletController2);
                                playerPlaneController.getBulletList().add(playerBulletController3);
                            }
                        }
                    }
                    break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });

        // 5: Initialize game loop
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(17);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(active == true) {

                        //Generate stuffs

                        clockEnemy = clockEnemy + 1;

                        clockIsland = clockIsland + 1;

                        clockBomb = clockBomb + 1;

                        clockPowerUp = clockPowerUp + 1;

                        clockBoss = clockBoss + 1;

                        if (clockBoss == 900 && bossActive == false){
                            Random randomX = new Random();
                            EnemyPlaneController enemyPlaneController = new EnemyPlaneController(randomX.nextInt(background.getModel().getWidth() - 42),0,60,41,ENEMY_SPEED,Utils.loadImageFromRes("plane1.png"));
                            enemyPlaneController.setBoss(true);
                            enemyPlaneController.setMoveType(0);
                            bossActive = true;
                            enemyPlaneController.setLife(2);
                            enemyPlaneControllers.add(enemyPlaneController);
                        }

                        if (clockEnemy == 150) {
                            Random rd = new Random();
                            Random randomX = new Random();
                            int type = rd.nextInt(3);

                            switch (type) {
                                case 0: {
                                    EnemyPlaneController enemyPlaneController = new EnemyPlaneController(randomX.nextInt(background.getModel().getWidth()), 0, 32, 32, ENEMY_SPEED, Utils.loadImageFromRes("enemy_plane_white_3.png"));
                                    if (enemyPlaneController.getModel().getX() == 0)
                                        enemyPlaneController.getModel().setX(5);
                                    if (enemyPlaneController.getModel().getX() <= 100)
                                        enemyPlaneController.setMoveType(1);
                                    else if (enemyPlaneController.getModel().getX() > 100 && enemyPlaneController.getModel().getX() <= 300)
                                        enemyPlaneController.setMoveType(0);
                                    else if (enemyPlaneController.getModel().getX() > 300 && enemyPlaneController.getModel().getX() < background.getModel().getWidth())
                                        enemyPlaneController.setMoveType(2);
                                    switch (enemyPlaneController.getMoveType()) {
                                        case 0:
                                            break;
                                        case 1:
                                            enemyPlaneController.getView().setImage(Utils.loadImageFromRes("enemy_plane_white_1.png"));
                                            break;
                                        case 2:
                                            enemyPlaneController.getView().setImage(Utils.loadImageFromRes("enemy_plane_white_2.png"));
                                            break;
                                    }
                                    enemyPlaneControllers.add(enemyPlaneController);
                                    clockEnemy = 0;
                                }
                                break;
                                case 1: {
                                    EnemyPlaneController enemyPlaneController = new EnemyPlaneController(randomX.nextInt(background.getModel().getWidth()), 0, 32, 32, ENEMY_SPEED, Utils.loadImageFromRes("enemy_plane_yellow_3.png"));
                                    if (enemyPlaneController.getModel().getX() == 0)
                                        enemyPlaneController.getModel().setX(5);
                                    if (enemyPlaneController.getModel().getX() <= 100)
                                        enemyPlaneController.setMoveType(1);
                                    else if (enemyPlaneController.getModel().getX() > 100 && enemyPlaneController.getModel().getX() <= 300)
                                        enemyPlaneController.setMoveType(0);
                                    else if (enemyPlaneController.getModel().getX() > 300 && enemyPlaneController.getModel().getX() < background.getModel().getWidth())
                                        enemyPlaneController.setMoveType(2);
                                    switch (enemyPlaneController.getMoveType()) {
                                        case 0:
                                            break;
                                        case 1:
                                            enemyPlaneController.getView().setImage(Utils.loadImageFromRes("enemy_plane_yellow_1.png"));
                                            break;
                                        case 2:
                                            enemyPlaneController.getView().setImage(Utils.loadImageFromRes("enemy_plane_yellow_2.png"));
                                            break;
                                    }
                                    enemyPlaneControllers.add(enemyPlaneController);
                                    clockEnemy = 0;
                                }
                                break;
                                case 2: {
                                    EnemyPlaneController enemyPlaneController = new EnemyPlaneController(randomX.nextInt(background.getModel().getWidth()), 0, 32, 32, ENEMY_SPEED, Utils.loadImageFromRes("enemy-green-3.png"));
                                    if (enemyPlaneController.getModel().getX() == 0)
                                        enemyPlaneController.getModel().setX(5);
                                    if (enemyPlaneController.getModel().getX() <= 100)
                                        enemyPlaneController.setMoveType(1);
                                    else if (enemyPlaneController.getModel().getX() > 100 && enemyPlaneController.getModel().getX() <= 300)
                                        enemyPlaneController.setMoveType(0);
                                    else if (enemyPlaneController.getModel().getX() > 300 && enemyPlaneController.getModel().getX() < background.getModel().getWidth())
                                        enemyPlaneController.setMoveType(2);
                                    switch (enemyPlaneController.getMoveType()) {
                                        case 0:
                                            break;
                                        case 1:
                                            enemyPlaneController.getView().setImage(Utils.loadImageFromRes("enemy-green-1.png"));
                                            break;
                                        case 2:
                                            enemyPlaneController.getView().setImage(Utils.loadImageFromRes("enemy-green-2.png"));
                                            break;
                                    }
                                    enemyPlaneControllers.add(enemyPlaneController);
                                    clockEnemy = 0;
                                }
                                break;
                            }
                        }

                        if (clockIsland == 200) {
                            Random rd = new Random();
                            Random randomX = new Random();
                            int type = rd.nextInt(2);
                            switch (type) {
                                case 0: {
                                    IslandController isLandController = new IslandController(randomX.nextInt(background.getModel().getWidth() - 63), -60, 64, 60, BACKGROUND_SPEED, Utils.loadImageFromRes("island.png"));
                                    if (isLandController.getModel().getX() == 0) isLandController.getModel().setX(5);
                                    isLands.add(isLandController);
                                    clockIsland = 0;
                                }
                                break;
                                case 1: {
                                    IslandController isLandController = new IslandController(randomX.nextInt(background.getModel().getWidth() - 47), -51, 48, 51, BACKGROUND_SPEED, Utils.loadImageFromRes("island-2.png"));
                                    if (isLandController.getModel().getX() == 0) isLandController.getModel().setX(5);
                                    isLands.add(isLandController);
                                    clockIsland = 0;
                                }
                                break;
                            }
                        }

                        if (clockBomb == 300) {
                            Random randomX = new Random();
                            BombController bombController = new BombController(randomX.nextInt(background.getModel().getWidth() - 31), -32, 32, 32, BACKGROUND_SPEED, Utils.loadImageFromRes("mine.png"));
                            if (bombController.getModel().getX() == 0) bombController.getModel().setX(5);
                            Bombs.add(bombController);
                            clockBomb = 0;
                        }

                        if (clockPowerUp == 500) {
                            Random randomX = new Random();
                            PowerUpController powerUp = new PowerUpController(randomX.nextInt(background.getModel().getWidth() - 23), -25, 24, 25, BACKGROUND_SPEED, Utils.loadImageFromRes("power-up.png"));
                            if (powerUp.getModel().getX() == 0) powerUp.getModel().setX(5);
                            powerUps.add(powerUp);
                            clockPowerUp = 0;
                        }

                        // Generate bullets
                        for (EnemyPlaneController temp : enemyPlaneControllers) {
                            if (temp != null) {
                                if (clockEnemy % 50 == 0) {
                                    if (temp.isBoss()==false) {
                                        EnemyBulletController enemyBullet = new EnemyBulletController((temp.getModel().getX() + (temp.getModel().getWidth() - 10) / 2), temp.getModel().getY() + 10, 9, 9, ENEMY_BULLET_SPEED, temp.getMoveType(), Utils.loadImageFromRes("bullet-round.png"));
                                        temp.getBulletList().add(enemyBullet);
                                    }
                                    if (temp.isBoss()==true){
                                        EnemyBulletController enemyBullet1 = new EnemyBulletController((temp.getModel().getX() + (temp.getModel().getWidth() - 10) / 2), temp.getModel().getY() + 10, 9, 9, ENEMY_BULLET_SPEED, 0, Utils.loadImageFromRes("bullet-round.png"));
                                        EnemyBulletController enemyBullet2 = new EnemyBulletController((temp.getModel().getX() + (temp.getModel().getWidth() - 10) / 2), temp.getModel().getY() + 10, 9, 9, ENEMY_BULLET_SPEED, 1, Utils.loadImageFromRes("bullet-left.png"));
                                        EnemyBulletController enemyBullet3 = new EnemyBulletController((temp.getModel().getX() + (temp.getModel().getWidth() - 10) / 2), temp.getModel().getY() + 10, 9, 9, ENEMY_BULLET_SPEED, 2, Utils.loadImageFromRes("bullet-right.png"));
                                        temp.getBulletList().add(enemyBullet1);
                                        temp.getBulletList().add(enemyBullet2);
                                        temp.getBulletList().add(enemyBullet3);
                                    }
                                }
                            }
                        }

                        for (PlayerBulletController temp : playerPlaneController.getBulletList()) {
                            if (temp != null) {
                                if (temp.getModel().getY() < 0) temp.setActive(false);
                            }
                        }

                        // Check conditions to evaluate for deletion
                        for (BombController temp : Bombs) {
                            if (temp != null) {
                                if (temp.getModel().getY() > background.getModel().getHeight()) {
                                    temp.setActive(false);
                                    temp.setKill(0);
                                }
                            }
                        }

                        for (PowerUpController temp : powerUps) {
                            if (temp != null) {
                                if (temp.getModel().getY() > background.getModel().getHeight()) {
                                    temp.setActive(false);
                                }
                            }
                        }

                        for (EnemyPlaneController temp : enemyPlaneControllers) {
                            if (temp != null) {
                                if (temp.getModel().getY() > background.getModel().getHeight() || temp.getModel().getX() > background.getModel().getWidth() || temp.getModel().getX() < 0) {
                                    temp.setActive(false);
                                    temp.setKill(0);
                                    temp.setLife(0);
                                }
                            }
                        }

                        for (EnemyPlaneController temp : enemyPlaneControllers) {
                            if (temp != null) {
                                for (EnemyBulletController tempBullet : temp.getBulletList()) {
                                    if (tempBullet.getModel().getY() > background.getModel().getHeight() + 10 || tempBullet.getModel().getX() > background.getModel().getWidth() + 10 || tempBullet.getModel().getX() < -10)
                                        tempBullet.setActive(false);
                                }
                            }
                        }

                        for (EnemyBulletController temp: enemyBulletStray){
                            if (temp.getModel().getY() > background.getModel().getHeight() + 10 || temp.getModel().getX() > background.getModel().getWidth() + 10 || temp.getModel().getX() < -10)
                                temp.setActive(false);
                            if ((temp.getModel().getY() + temp.getModel().getHeight() > playerPlaneController.getModel().getY() && temp.getModel().getY() < playerPlaneController.getModel().getY() + playerPlaneController.getModel().getHeight()) && (temp.getModel().getX() + temp.getModel().getWidth() > playerPlaneController.getModel().getX() && temp.getModel().getX() < playerPlaneController.getModel().getX() + playerPlaneController.getModel().getWidth())){
                                if (playerPlaneController.isPower() == false)
                                    playerPlaneController.setActive(false);
                                temp.setActive(false);
                            }
                        }

                        for (IslandController temp : isLands) {
                            if (temp.getModel().getY() > background.getModel().getHeight() || temp.getModel().getX() > background.getModel().getWidth() || temp.getModel().getX() < 0)
                                temp.setActive(false);
                        }

                        for (PlayerBulletController temp1 : playerPlaneController.getBulletList()) {
                            for (EnemyPlaneController temp2 : enemyPlaneControllers) {
                                if (temp1 != null && temp2 != null) {
                                    if ((temp1.getModel().getY() + temp1.getModel().getHeight() > temp2.getModel().getY() && temp1.getModel().getY() < temp2.getModel().getY() + temp2.getModel().getHeight()) && (temp1.getModel().getX() + temp1.getModel().getWidth() > temp2.getModel().getX() && temp1.getModel().getX() < temp2.getModel().getX() + temp2.getModel().getWidth())) {
                                        temp1.setActive(false);
                                        if (temp2.isBoss() == false) {
                                            temp2.setActive(false);
                                        }
                                        temp2.setLife(temp2.getLife() - 1);
                                        if (temp2.isBoss() == true && temp2.getLife()==0){
                                            temp2.setActive(false);
                                            bossActive = false;
                                            clockBoss = 0;
                                        }
                                        if (temp2.getMoveType() == 0) {
                                            if (temp2.isBoss() == false) {
                                                score = score + 2;
                                                System.out.println("Score: " + score + " (+2)");
                                            }
                                            if (temp2.isBoss() == true && temp2.isActive() == false){
                                                score = score + 10;
                                                System.out.println("Score: " + score + " (+10)");
                                            }
                                        } else {
                                            score++;
                                            System.out.println("Score: " + score + " (+1)");
                                        }
                                    }
                                }
                            }
                        }

                        for (EnemyPlaneController temp1 : enemyPlaneControllers) {
                            for (EnemyBulletController temp2 : temp1.getBulletList()) {
                                if (temp1 != null && temp2 != null) {
                                    if ((temp2.getModel().getY() + temp2.getModel().getHeight() > playerPlaneController.getModel().getY() && temp2.getModel().getY() < playerPlaneController.getModel().getY() + playerPlaneController.getModel().getHeight()) && (temp2.getModel().getX() + temp2.getModel().getWidth() > playerPlaneController.getModel().getX() && temp2.getModel().getX() < playerPlaneController.getModel().getX() + playerPlaneController.getModel().getWidth())){
                                        if (playerPlaneController.isPower() == false)
                                            playerPlaneController.setActive(false);
                                        temp2.setActive(false);
                                    }
                                }
                            }
                        }

                        for (BombController temp : Bombs) {
                            if (temp != null)
                                if ((temp.getModel().getY() + temp.getModel().getHeight() > playerPlaneController.getModel().getY() && temp.getModel().getY() < playerPlaneController.getModel().getY() + playerPlaneController.getModel().getHeight()) && (temp.getModel().getX() + temp.getModel().getWidth() > playerPlaneController.getModel().getX() && temp.getModel().getX() < playerPlaneController.getModel().getX() + playerPlaneController.getModel().getWidth())) {
                                    if (playerPlaneController.isPower() == false)
                                        playerPlaneController.setActive(false);
                                    temp.setActive(false);
                                }
                        }

                        for (PowerUpController temp : powerUps) {
                            if (temp != null)
                                if ((temp.getModel().getY() + temp.getModel().getHeight() > playerPlaneController.getModel().getY() && temp.getModel().getY() < playerPlaneController.getModel().getY() + playerPlaneController.getModel().getY()) && (temp.getModel().getY() + temp.getModel().getWidth() > playerPlaneController.getModel().getX() && temp.getModel().getX() < playerPlaneController.getModel().getX() + playerPlaneController.getModel().getWidth())) {
                                    temp.setActive(false);
                                    playerPlaneController.setPower(true);
                                }
                        }

                        for (PlayerBulletController temp1 : playerPlaneController.getBulletList()) {
                            for (BombController temp2 : Bombs) {
                                if (temp1 != null && temp2 != null) {
                                    if ((temp1.getModel().getY() + temp1.getModel().getHeight() > temp2.getModel().getY() && temp1.getModel().getY() < temp2.getModel().getY() + temp2.getModel().getHeight()) && (temp1.getModel().getX() + temp1.getModel().getWidth() > temp2.getModel().getX() && temp1.getModel().getX() < temp2.getModel().getX() + temp2.getModel().getWidth())) {
                                        temp2.setActive(false);
                                        temp1.setActive(false);
                                        score++;
                                        System.out.println("Score: " + score + " (+1)");
                                    }
                                }
                            }
                        }

                        //Run and delete elements
                        Iterator<EnemyPlaneController> enemyPlaneControllerIterator = enemyPlaneControllers.iterator();

                        while (enemyPlaneControllerIterator.hasNext()) {
                            EnemyPlaneController temp = enemyPlaneControllerIterator.next();
                            temp.run();
                            if (temp.isActive() == false && temp.getKill() == 0 && temp.getLife() == 0){
                                for (EnemyBulletController tempBullet:temp.getBulletList()){
                                    enemyBulletStray.add(tempBullet);
                                }
                                enemyPlaneControllerIterator.remove();
                            }
                        }

                        Iterator<BombController> bombControllerIterator = Bombs.iterator();

                        while (bombControllerIterator.hasNext()) {
                            BombController temp = bombControllerIterator.next();
                            temp.run();
                            if (temp.isActive() == false && temp.getKill() == 0) bombControllerIterator.remove();
                        }

                        Iterator<PowerUpController> powerUpIterator = powerUps.iterator();

                        while (powerUpIterator.hasNext()) {
                            PowerUpController temp = powerUpIterator.next();
                            temp.run();
                            if (temp.isActive() == false) powerUpIterator.remove();
                        }

                        for (EnemyPlaneController temp1 : enemyPlaneControllers) {
                            Iterator<EnemyBulletController> enemyBulletIterator = temp1.getBulletList().iterator();
                            while (enemyBulletIterator.hasNext()) {
                                EnemyBulletController temp2 = enemyBulletIterator.next();
                                temp2.run();
                                if (temp2.isActive() == false) enemyBulletIterator.remove();
                            }
                        }

                        Iterator<EnemyBulletController> enemyBulletStrayIterator = enemyBulletStray.iterator();

                        while(enemyBulletStrayIterator.hasNext()){
                            EnemyBulletController temp = enemyBulletStrayIterator.next();
                            temp.run();
                            if (temp.isActive() == false) enemyBulletStrayIterator.remove();
                        }


                        Iterator<PlayerBulletController> bulletIterator = playerPlaneController.getBulletList().iterator();

                        while (bulletIterator.hasNext()) {
                            PlayerBulletController temp = bulletIterator.next();
                            temp.run();
                            if (temp.isActive() == false) bulletIterator.remove();
                        }

                        Iterator<IslandController> islandIterator = isLands.iterator();

                        while (islandIterator.hasNext()) {
                            IslandController temp = islandIterator.next();
                            temp.run();
                            if (temp.isActive() == false) islandIterator.remove();
                        }

                        playerPlaneController.run();

                        background.run();
                        backgroundBuffer.run();

                        if (background.getModel().getY() == 600) {
                            backgroundBuffer.getModel().setY(-600);
                            background.getModel().setY(0);
                        }

                        if (playerPlaneController.isActive() == false && playerPlaneController.getKill() == 0) {
                            System.out.println("YOU LOST");
                            System.out.println("FINAL SCORE: " + score);
                            String name;
                            int len = 0;
                            do {
                                System.out.println("/* If you leave your name empty you will be named Anonymous */");
                                System.out.print("Enter your name: ");
                                Scanner sc = new Scanner(System.in);
                                name = sc.nextLine();
                                len = name.length();
                                if (len >= 17) {
                                    System.out.println("Please use less than 17 characters.\n");
                                }
                            } while (len >= 17);
                            if (name.length() < 1) name = "Anonymous";
                            len = name.length();
                            for (int i = 17; i >= len; i--) {
                                name = name + " ";
                            }
                            name = name + score + "\n";
                            FileWriter writer;
                            try {
                                writer = new FileWriter("highscore.txt", true);
                                writer.write(name);
                                writer.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                Scanner read = new Scanner(new File("highscore.txt"));
                                System.out.println("-----HIGH SCORES-----");
                                while (read.hasNextLine())
                                    System.out.println(read.nextLine());
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            System.exit(0);
                        }
                    }

                    repaint();

                }
            }
        });

        backBufferImage = new BufferedImage(
                windowX,
                windowY,
                BufferedImage.TYPE_INT_ARGB);

    }

    public void start(){
            thread.start();
    }


    @Override
    public void update(Graphics g) {
        if (backBufferImage != null) {
            backGraphics = backBufferImage.getGraphics();

            background.getView().draw(backGraphics,background.getModel());
            backgroundBuffer.getView().draw(backGraphics,backgroundBuffer.getModel());

            if (playerPlaneController.isActive() == false){
                gameover.getView().draw(backGraphics,gameover.getModel());
            }

            for(IslandController temp:isLands) {
                if (temp != null) {
                    temp.getView().draw(backGraphics,temp.getModel());
                }
            }

            for(EnemyPlaneController temp:enemyPlaneControllers) {
                if (temp != null) {
                    temp.getView().draw(backGraphics,temp.getModel());
                }
            }

            for(PlayerBulletController temp:playerPlaneController.getBulletList()) {
                if (temp != null) {
                    temp.getView().draw(backGraphics,temp.getModel());
                }
            }

            for(EnemyPlaneController temp:enemyPlaneControllers){
                if (temp != null){
                    for(EnemyBulletController tempBullet:temp.getBulletList()){
                        if (tempBullet != null)
                            tempBullet.getView().draw(backGraphics,tempBullet.getModel());
                    }
                }
            }

            for(EnemyBulletController temp:enemyBulletStray){
                if (temp != null)
                    temp.getView().draw(backGraphics,temp.getModel());
            }

            for(PowerUpController temp:powerUps) {
                if (temp != null) {
                    temp.getView().draw(backGraphics,temp.getModel());
                }
            }

            for(BombController temp:Bombs) {
                if (temp != null) {
                    temp.getView().draw(backGraphics,temp.getModel());
                }
            }

            playerPlaneController.getView().draw(backGraphics,playerPlaneController.getModel());

            if (active == false){
                paused.getView().draw(backGraphics,paused.getModel());
            }

            if (playerPlaneController.isActive() == false){
                wasted.getView().draw(backGraphics,wasted.getModel());
            }

            g.drawImage(backBufferImage, 0, 0, null);
        }
    }
}