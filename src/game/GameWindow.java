package game;

import controllers.*;
import utils.Utils;

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
import java.util.Random;
import java.util.Scanner;

public class GameWindow extends Frame {
    public static final int SPEED = 6;
    public static final int BULLET_SPEED = 10;
    public static final int ENEMY_SPEED = 4;
    public static final int ENEMY_BULLET_SPEED = 5;
    public static final int BACKGROUND_SPEED = 1;

    public static final int windowX = 400;
    public static final int windowY = 600;

    public static final int planeWidth = 60;
    public static final int planeHeight = 41;

    public static final int planeX = (windowX - planeWidth) / 2;
    public static final int planeY = windowY - planeHeight - 5;


    private BufferedImage backBufferImage;
    private Graphics backGraphics;

    private Graphics g;

    private int clockEnemy = 0;

    private int clockIsland = 0;

    private int clockBomb = 0;

    private int clockPowerUp = 0;

    private int clockBoss = 0;

    private int clockDank = 0;

    private boolean bossActive = false;

    private boolean active = true;

    private BackgroundController background;
    private BackgroundController backgroundBuffer;
    private BackgroundController paused;
    private BackgroundController wasted;

    //private Vector<EnemyPlaneController> enemyPlaneControllers = new Vector<EnemyPlaneController>();

    //private Vector<IslandController> isLands = new Vector<IslandController>();

    //private Vector<BombController> Bombs = new Vector<BombController>();

    //private Vector<PowerUpController> powerUps = new Vector<PowerUpController>();

    //private Vector<EnemyBulletController> enemyBulletStray = new Vector<EnemyBulletController>();

    private PlayerPlaneController playerPlaneController;

    private ControllerManager gameControllers;

    private Thread thread;

    public GameWindow() {
        gameControllers = new ControllerManager();
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
        gameControllers.getBackgroundControllers().add(background);
        backgroundBuffer = new BackgroundController(0,-600,windowX,windowY,BACKGROUND_SPEED,Utils.loadImageFromRes("background.png"));
        gameControllers.getBackgroundControllers().add(backgroundBuffer);
        paused = new BackgroundController(0,0,windowX,windowY,BACKGROUND_SPEED,Utils.loadImageFromRes("paused.png"));
        wasted = new BackgroundController(0,0,windowX,windowY,BACKGROUND_SPEED,Utils.loadImageFromRes("wasted.png"));

        // 2: Draw image
        repaint();

        // 3: Initialize player plane
        playerPlaneController = new PlayerPlaneController(planeX,planeY,planeWidth,planeHeight,SPEED,Utils.loadImageFromRes("plane3.png"),gameControllers);
        gameControllers.getGameControllers().add(playerPlaneController);
        gameControllers.getCollidables().add(playerPlaneController);

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
                            playerPlaneController.setRight(true);
                    }
                    break;
                    case KeyEvent.VK_LEFT: {
                        if (playerPlaneController.getModel().getX() - playerPlaneController.getModel().getSpeed() >= 3 && active == true)
                            playerPlaneController.setLeft(true);
                    }
                    break;
                    case KeyEvent.VK_UP: {
                       if (playerPlaneController.getModel().getY() - playerPlaneController.getModel().getSpeed() >= playerPlaneController.getModel().getHeight()/2 && active == true)
                           playerPlaneController.setUp(true);
                    }
                    break;
                    case KeyEvent.VK_DOWN: {
                        if (playerPlaneController.getModel().getY()+ playerPlaneController.getModel().getSpeed() <= background.getModel().getHeight() - playerPlaneController.getModel().getHeight() - 5 && active == true)
                            playerPlaneController.setDown(true);
                    }
                    break;
                    case KeyEvent.VK_ENTER: {
                        if (active == true){
                            active = false;
                            System.out.println("Paused");
                        }
                        else{
                            active = true;
                            System.out.println("Unpaused");
                        }
                    }break;
                    case KeyEvent.VK_SPACE: {
                        if (active == true)
                           playerPlaneController.setSpace(true);
                    }
                    break;
                    case KeyEvent.VK_CONTROL: {
                        playerPlaneController.setControl(true);
                    }
                    break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT: {
                        playerPlaneController.setRight(false);
                    }
                    break;
                    case KeyEvent.VK_LEFT: {
                        playerPlaneController.setLeft(false);
                    }
                    break;
                    case KeyEvent.VK_UP: {
                        playerPlaneController.setUp(false);
                    }
                    break;
                    case KeyEvent.VK_DOWN: {
                        playerPlaneController.setDown(false);
                    }
                    case KeyEvent.VK_SPACE: {
                        playerPlaneController.setSpace(false);
                    }
                    break;
                    case KeyEvent.VK_CONTROL: {
                        playerPlaneController.setControl(false);
                    }
                    break;
                }
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

                        clockDank = clockDank + 1;


                        if (clockBoss == 900 && bossActive == false){
                            Random randomX = new Random();
                            EnemyPlaneController enemyPlaneController = new EnemyPlaneController(randomX.nextInt(background.getModel().getWidth() - 42),0,60,41,ENEMY_SPEED,Utils.loadImageFromRes("plane1.png"),gameControllers);
                            enemyPlaneController.setBoss(true);
                            enemyPlaneController.setMoveType(0);
                            bossActive = true;
                            enemyPlaneController.setLife(2);
                            gameControllers.getGameControllers().add(enemyPlaneController);
                            gameControllers.getCollidables().add(enemyPlaneController);
                            clockBoss = 0;
                            clockEnemy = 0;
                        }

                        if (clockDank == 500){
                            Random randomX = new Random();
                            EnemyPlaneController enemyPlaneController = new EnemyPlaneController(randomX.nextInt(background.getModel().getWidth() - 42),0,40,40,ENEMY_SPEED,Utils.loadImageFromRes("unnamed.png"),gameControllers);
                            enemyPlaneController.setDank(true);
                            enemyPlaneController.setMoveType(4);
                            enemyPlaneController.setLife(1);
                            gameControllers.getGameControllers().add(enemyPlaneController);
                            gameControllers.getCollidables().add(enemyPlaneController);
                            clockDank = 0;
                            clockEnemy = 0;
                        }

                        if (clockEnemy == 35) {
                            Random rd = new Random();
                            Random randomX = new Random();
                            int type = rd.nextInt(3);

                            switch (type) {
                                case 0: {
                                    EnemyPlaneController enemyPlaneController = new EnemyPlaneController(randomX.nextInt(background.getModel().getWidth()), 0, 32, 32, ENEMY_SPEED, Utils.loadImageFromRes("enemy_plane_white_3.png"), gameControllers);
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
                                    gameControllers.getGameControllers().add(enemyPlaneController);
                                    gameControllers.getCollidables().add(enemyPlaneController);
                                    clockEnemy = 0;
                                    break;
                                }
                                case 1: {
                                    EnemyPlaneController enemyPlaneController = new EnemyPlaneController(randomX.nextInt(background.getModel().getWidth()), 0, 32, 32, ENEMY_SPEED, Utils.loadImageFromRes("enemy_plane_yellow_3.png"),gameControllers);
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
                                    gameControllers.getGameControllers().add(enemyPlaneController);
                                    gameControllers.getCollidables().add(enemyPlaneController);
                                    clockEnemy = 0;
                                    break;
                                }
                                case 2: {
                                    EnemyPlaneController enemyPlaneController = new EnemyPlaneController(randomX.nextInt(background.getModel().getWidth()), 0, 32, 32, ENEMY_SPEED, Utils.loadImageFromRes("enemy-green-3.png"),gameControllers);
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
                                    gameControllers.getGameControllers().add(enemyPlaneController);
                                    gameControllers.getCollidables().add(enemyPlaneController);
                                    clockEnemy = 0;
                                    break;
                                }
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
                                    gameControllers.getIslandControllers().add(isLandController);
                                    clockIsland = 0;
                                    break;
                                }
                                case 1: {
                                    IslandController isLandController = new IslandController(randomX.nextInt(background.getModel().getWidth() - 47), -51, 48, 51, BACKGROUND_SPEED, Utils.loadImageFromRes("island-2.png"));
                                    if (isLandController.getModel().getX() == 0) isLandController.getModel().setX(5);
                                    gameControllers.getIslandControllers().add(isLandController);
                                    clockIsland = 0;
                                    break;
                                }
                            }
                        }

                        if (clockBomb == 300) {
                            Random randomX = new Random();
                            BombController bombController = new BombController(randomX.nextInt(background.getModel().getWidth() - 31), -32, 32, 32, BACKGROUND_SPEED, Utils.loadImageFromRes("mine.png"));
                            if (bombController.getModel().getX() == 0) bombController.getModel().setX(5);
                            gameControllers.getGameControllers().add(bombController);
                            gameControllers.getCollidables().add(bombController);
                            clockBomb = 0;
                        }

                        if (clockPowerUp == 500) {
                            Random randomX = new Random();
                            PowerUpController powerUp = new PowerUpController(randomX.nextInt(background.getModel().getWidth() - 23), -25, 24, 25, BACKGROUND_SPEED, Utils.loadImageFromRes("power-up.png"));
                            if (powerUp.getModel().getX() == 0) powerUp.getModel().setX(5);
                            gameControllers.getGameControllers().add(powerUp);
                            gameControllers.getCollidables().add(powerUp);
                            clockPowerUp = 0;
                        }


                        if (background.getModel().getY() == 600) {
                            backgroundBuffer.getModel().setY(-600);
                            background.getModel().setY(0);
                        }

                        if (playerPlaneController.isActive() == false){
                            background.getView().setImage(Utils.loadImageFromRes("gameover.png"));
                            backgroundBuffer.getView().setImage(Utils.loadImageFromRes("gameover.png"));
                        }

                        if (playerPlaneController.isActive() == false && playerPlaneController.getKill() == 0) {
                            System.out.println("YOU LOST");
                            System.out.println("FINAL SCORE: " + gameControllers.getScore());
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
                            name = name + gameControllers.getScore() + "\n";
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
                        gameControllers.run();
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

            gameControllers.draw(backGraphics);

            if (active == false){
                paused.getView().draw(backGraphics,paused.getModel());
            }

            if (playerPlaneController.isActive() == false){
                wasted.getView().draw(backGraphics,wasted.getModel());
            }

            g.drawImage(backBufferImage, 0, 0, null);
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }
}