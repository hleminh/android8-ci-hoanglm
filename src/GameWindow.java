import javax.imageio.ImageIO;
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

    /*Image backgroundImage;
    Image planeImage;
    Image enemyImage;
    Image bulletImage;*/

    private static final int windowX = 400;
    private static final int windowY = 600;

    private int planeWidth = 60;
    private int planeHeight = 41;

    private int planeX = (windowX - planeWidth) / 2;
    private int planeY = windowY - planeHeight - 5;

    /*private int bulletX = (windowX - 13) /2;
    private int bulletY = windowY - planeY - 33;
    boolean bulletExist=false;*/

    private BufferedImage backBufferImage;
    private Graphics backGraphics;

    private Graphics g;

    private int clockEnemy = 0;

    private int clockIsland = 0;

    private int clockBomb = 0;

    private int clockPowerUp = 0;

    public boolean active = true;

    private PlayerPlane playerPlane;

    private Background background;
    private Background backgroundBuffer;
    private Background gameover;
    private Background paused;
    private Background wasted;

    ArrayList<EnemyPlane> enemyPlanes = new ArrayList<EnemyPlane>();

    ArrayList<Island> isLands = new ArrayList<Island>();

    ArrayList<Bomb> Bombs = new ArrayList<Bomb>();

    ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();

    Thread thread;

    public GameWindow() {
        setVisible(true);
        setSize(windowX, windowY);
        System.out.println("RULES:\n - Shoot down enemy's airplanes and bombs.\n - Avoid enemy's bullets and bombs.\n - Collect power-ups to be invunerable for a short period of time.\n - Each diagonally moving airplane rewards 1pt since they're easier to shoot.\n - Each straight moving airplane rewards 2pt since they're harder to shoot.\n - Each bomb rewards 1pt.\n Good luck!\nCONTROLS:\n - ARROW KEYS to move.\n - SPACE to shoot.\n - ENTER to pause (experimental).\nGAME LOGS:");
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
        //backgroundImage = loadImageFromRes("background.png");
        background = new Background(0,0,windowX,windowY,BACKGROUND_SPEED,loadImageFromRes("background.png"));
        backgroundBuffer = new Background(0,-600,windowX,windowY,BACKGROUND_SPEED,loadImageFromRes("background.png"));
        gameover = new Background(0,0,windowX,windowY,BACKGROUND_SPEED,loadImageFromRes("gameover.png"));
        paused = new Background(0,0,windowX,windowY,BACKGROUND_SPEED,loadImageFromRes("paused.png"));
        wasted = new Background(0,0,windowX,windowY,BACKGROUND_SPEED,loadImageFromRes("wasted.png"));

        // 2: Draw image
        repaint();

        // 3: Initialize player plane
        playerPlane = new PlayerPlane(planeX,planeY,planeWidth,planeHeight,SPEED,loadImageFromRes("plane3.png"));

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
                        if (playerPlane.x + playerPlane.speed <= background.width - playerPlane.width - 3 && active == true)
                            playerPlane.moveRight();
                        //System.out.println("keyPressed: Right");
                        //System.out.println("PlaneX:" + planeX);
                    }
                    break;
                    case KeyEvent.VK_LEFT: {
                        if (playerPlane.x - playerPlane.speed >= 3 && active == true)
                            playerPlane.moveLeft();
                        //System.out.println("keyPressed: Left");
                        //System.out.println("PlaneX:" + planeX);
                    }
                    break;
                    case KeyEvent.VK_UP: {
                       if (playerPlane.y - playerPlane.speed >= playerPlane.height/2 && active == true)
                           playerPlane.moveUp();
                        //System.out.println("keyPressed: Up");
                        //System.out.println("PlaneY:" + planeY);
                    }
                    break;
                    case KeyEvent.VK_DOWN: {
                        if (playerPlane.y + playerPlane.speed <= background.height - playerPlane.height - 5 && active == true)
                            playerPlane.moveDown();
                        //System.out.println("keyPressed: Down");
                        //System.out.println("PlaneY:" + planeY);
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
                        //System.out.println(active);
                    }break;
                    case KeyEvent.VK_SPACE: {
                        if (active == true) {
                            PlayerBullet playerBullet = new PlayerBullet((playerPlane.x + (playerPlane.width - 8) / 2), playerPlane.y - 10, 9, 20, BULLET_SPEED, loadImageFromRes("bullet-single.png"));
                            if (playerPlane.power == true) {
                                playerBullet.image = loadImageFromRes("bullet-double.png");
                                playerBullet.width = 17;
                                playerBullet.height = 16;
                                playerBullet.x = playerPlane.x + (playerPlane.width - 16) / 2;
                                playerBullet.y = playerPlane.y - 10;
                                playerBullet.speed = playerBullet.speed + 10;
                            }
                            playerPlane.bulletList.add(playerBullet);
                            //bulletExist=true;
                            //System.out.println("keyPressed: Space");
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

                        clockEnemy = clockEnemy + 1;

                        clockIsland = clockIsland + 1;

                        clockBomb = clockBomb + 1;

                        clockPowerUp = clockPowerUp + 1;

                        //System.out.println("clockEnemy: " + clockEnemy);

                        if (clockEnemy == 150) {
                            Random rd = new Random();
                            Random randomX = new Random();
                            int type = rd.nextInt(3);
                            //System.out.println("New enemy");
                            //System.out.println("Type: " + type);

                            switch (type) {
                                case 0: {
                                    EnemyPlane enemyPlane = new EnemyPlane(randomX.nextInt(background.width), 0, 32, 32, ENEMY_SPEED, loadImageFromRes("enemy_plane_white_3.png"));
                                    if (enemyPlane.x == 0) enemyPlane.x = 5;
                                    if (enemyPlane.x <= 100) enemyPlane.moveType = 1;
                                    else if (enemyPlane.x > 100 && enemyPlane.x <= 300) enemyPlane.moveType = 0;
                                    else if (enemyPlane.x > 300 && enemyPlane.x < background.width)
                                        enemyPlane.moveType = 2;
                                    switch (enemyPlane.moveType) {
                                        case 0:
                                            break;
                                        case 1:
                                            enemyPlane.image = loadImageFromRes("enemy_plane_white_1.png");
                                            break;
                                        case 2:
                                            enemyPlane.image = loadImageFromRes("enemy_plane_white_2.png");
                                            break;
                                    }
                                    //System.out.println("EnemyX: " + enemyPlane.x);
                                    //System.out.println("Enemy state: " + enemyPlane.active);
                                    enemyPlanes.add(enemyPlane);
                                    clockEnemy = 0;
                                }
                                break;
                                case 1: {
                                    EnemyPlane enemyPlane = new EnemyPlane(randomX.nextInt(background.width), 0, 32, 32, ENEMY_SPEED, loadImageFromRes("enemy_plane_yellow_3.png"));
                                    if (enemyPlane.x == 0) enemyPlane.x = 5;
                                    if (enemyPlane.x <= 100) enemyPlane.moveType = 1;
                                    else if (enemyPlane.x > 100 && enemyPlane.x <= 300) enemyPlane.moveType = 0;
                                    else if (enemyPlane.x > 300 && enemyPlane.x < background.width)
                                        enemyPlane.moveType = 2;
                                    switch (enemyPlane.moveType) {
                                        case 0:
                                            break;
                                        case 1:
                                            enemyPlane.image = loadImageFromRes("enemy_plane_yellow_1.png");
                                            break;
                                        case 2:
                                            enemyPlane.image = loadImageFromRes("enemy_plane_yellow_2.png");
                                            break;
                                    }
                                    //System.out.println("EnemyX: " + enemyPlane.x);
                                    //System.out.println("Enemy state: " + enemyPlane.active);
                                    enemyPlanes.add(enemyPlane);
                                    clockEnemy = 0;
                                }
                                break;
                                case 2: {
                                    EnemyPlane enemyPlane = new EnemyPlane(randomX.nextInt(background.width), 0, 32, 32, ENEMY_SPEED, loadImageFromRes("enemy-green-3.png"));
                                    if (enemyPlane.x == 0) enemyPlane.x = 5;
                                    if (enemyPlane.x <= 100) enemyPlane.moveType = 1;
                                    else if (enemyPlane.x > 100 && enemyPlane.x <= 300) enemyPlane.moveType = 0;
                                    else if (enemyPlane.x > 300 && enemyPlane.x < background.width)
                                        enemyPlane.moveType = 2;
                                    switch (enemyPlane.moveType) {
                                        case 0:
                                            break;
                                        case 1:
                                            enemyPlane.image = loadImageFromRes("enemy-green-1.png");
                                            break;
                                        case 2:
                                            enemyPlane.image = loadImageFromRes("enemy-green-2.png");
                                            break;
                                    }
                                    //System.out.println("EnemyX: " + enemyPlane.x);
                                    //System.out.println("Enemy state: " + enemyPlane.active);
                                    enemyPlanes.add(enemyPlane);
                                    clockEnemy = 0;
                                }
                                break;
                            }
                        }

                        if (clockIsland == 100) {
                            Random rd = new Random();
                            Random randomX = new Random();
                            int type = rd.nextInt(2);
                            //System.out.println("New island");
                            //System.out.println("Type: "+type);

                            switch (type) {
                                case 0: {
                                    Island isLand = new Island(randomX.nextInt(background.width - 63), -60, 64, 60, BACKGROUND_SPEED, loadImageFromRes("island.png"));
                                    if (isLand.x == 0) isLand.x = 5;
                                    isLands.add(isLand);
                                    clockIsland = 0;
                                }
                                break;
                                case 1: {
                                    Island isLand = new Island(randomX.nextInt(background.width - 47), -51, 48, 51, BACKGROUND_SPEED, loadImageFromRes("island-2.png"));
                                    if (isLand.x == 0) isLand.x = 5;
                                    isLands.add(isLand);
                                    clockIsland = 0;
                                }
                                break;
                            }
                        }

                        if (clockBomb == 300) {
                            Random randomX = new Random();
                            //System.out.println("New Bomb");
                            Bomb bomb = new Bomb(randomX.nextInt(background.width - 31), -32, 32, 32, BACKGROUND_SPEED, loadImageFromRes("mine.png"));
                            if (bomb.x == 0) bomb.x = 5;
                            Bombs.add(bomb);
                            clockBomb = 0;
                        }

                        if (clockPowerUp == 500) {
                            Random randomX = new Random();
                            //System.out.println("New Power-up");
                            PowerUp powerUp = new PowerUp(randomX.nextInt(background.width - 23), -25, 24, 25, BACKGROUND_SPEED, loadImageFromRes("power-up.png"));
                            if (powerUp.x == 0) powerUp.x = 5;
                            powerUps.add(powerUp);
                            clockPowerUp = 0;
                        }

                    /*bulletY -= BULLET_SPEED;
                    if (bulletY - BULLET_SPEED <= 0){
                        bulletExist = false;
                        bulletY = windowY - planeY - 33;
                    }*/

                        for (PlayerBullet temp : playerPlane.bulletList) {
                            if (temp != null) {
                                temp.y -= temp.speed;
                                if (temp.y < 0) temp.active = false;
                            }
                        }

                        for (Bomb temp : Bombs) {
                            if (temp != null) {
                                temp.y += temp.speed;
                                if (temp.y > background.height) {
                                    temp.active = false;
                                    temp.kill = 0;
                                }
                            }
                        }

                        for (PowerUp temp : powerUps) {
                            if (temp != null) {
                                temp.y += temp.speed;
                                if (temp.y > background.height) {
                                    temp.active = false;
                                }
                            }
                        }

                        for (EnemyPlane temp : enemyPlanes) {
                            if (temp != null && temp.y <= background.height) {
                                //System.out.println("moveType: " + temp.moveType);
                                switch (temp.moveType) {
                                    case 0:
                                        temp.moveDown();
                                        break;
                                    case 1:
                                        temp.moveRightDown();
                                        break;
                                    case 2:
                                        temp.moveLeftDown();
                                        break;
                                }
                                if (temp.y > background.height || temp.x > background.width || temp.x < 0) {
                                    temp.active = false;
                                    temp.kill = 0;
                                }
                            }
                        }

                        for (EnemyPlane temp : enemyPlanes) {
                            if (temp != null && temp.y <= background.height) {
                                if (clockEnemy % 50 == 0) {
                                    //System.out.println("Incoming!");
                                    EnemyBullet enemyBullet = new EnemyBullet(temp.x, temp.y + 5, 32, 32, ENEMY_BULLET_SPEED, loadImageFromRes("enemy_bullet.png"));
                                    temp.bulletList.add(enemyBullet);
                                }
                            }
                        }

                        for (EnemyPlane temp : enemyPlanes) {
                            if (temp != null) {
                                for (EnemyBullet tempBullet : temp.bulletList) {
                                    if (tempBullet != null && tempBullet.y <= background.height)
                                        tempBullet.y += tempBullet.speed;
                                    if (temp.y > background.height + 10 || temp.x > background.width + 10 || temp.x < -10)
                                        temp.active = false;
                                }
                            }
                        }

                        for (Island temp : isLands) {
                            if (temp != null && temp.y <= background.height)
                                temp.y += temp.speed;
                            if (temp.y > background.height || temp.x > background.width || temp.x < 0)
                                temp.active = false;
                        }

                        for (PlayerBullet temp1 : playerPlane.bulletList) {
                            for (EnemyPlane temp2 : enemyPlanes) {
                                if (temp1 != null && temp2 != null) {
                                    //System.out.println("Hit");
                                    if ((temp1.y + temp1.height > temp2.y && temp1.y < temp2.y + temp2.height) && (temp1.x + temp1.width > temp2.x && temp1.x < temp2.x + temp2.width)) {
                                        //System.out.println("Bullet x: " + temp1.x +" Bullet y: " +temp1.y);
                                        //System.out.println("Enemy x: " + temp2.x +" Enemy y: " +temp2.y);
                                        temp2.active = false;
                                        temp1.active = false;
                                        if (temp2.moveType == 0) {
                                            score = score + 2;
                                            System.out.println("Score: " + score + " (+2)");
                                        } else {
                                            score++;
                                            System.out.println("Score: " + score + " (+1)");
                                        }
                                    }
                                }
                            }
                        }

                        for (EnemyPlane temp1 : enemyPlanes) {
                            for (EnemyBullet temp2 : temp1.bulletList) {
                                if (temp1 != null && temp2 != null) {
                                    //System.out.println("Hit");
                                    if ((temp2.y + temp2.height > playerPlane.y && temp2.y < playerPlane.y + playerPlane.height) && (temp2.x + temp2.width > playerPlane.x && temp2.x < playerPlane.x + playerPlane.width)) {
                                        //System.out.println("Bullet x: " + temp2.x + " Bullet y: " + temp2.y);
                                        //System.out.println("Player x: " + playerPlane.x +" Player y: " + playerPlane.y);
                                        if (playerPlane.power == false)
                                            playerPlane.active = false;
                                        temp2.active = false;
                                    }
                                }
                            }
                        }

                        for (Bomb temp : Bombs) {
                            if (temp != null)
                                //System.out.println("Hit");
                                if ((temp.y + temp.height > playerPlane.y && temp.y < playerPlane.y + playerPlane.height) && (temp.x + temp.width > playerPlane.x && temp.x < playerPlane.x + playerPlane.width)) {
                                    //System.out.println("Bomb x: " + temp.x + " Bomb y: " + temp.y);
                                    //System.out.println("Player x: " + playerPlane.x +" Player y: " + playerPlane.y);
                                    if (playerPlane.power == false)
                                        playerPlane.active = false;
                                    temp.active = false;
                                }
                        }

                        for (PowerUp temp : powerUps) {
                            if (temp != null)
                                //System.out.println("Hit");
                                if ((temp.y + temp.height > playerPlane.y && temp.y < playerPlane.y + playerPlane.height) && (temp.x + temp.width > playerPlane.x && temp.x < playerPlane.x + playerPlane.width)) {
                                    //System.out.println("Power-up x: " + temp.x + " Power-up y: " + temp.y);
                                    //System.out.println("Player x: " + playerPlane.x +" Player y: " + playerPlane.y);
                                    temp.active = false;
                                    playerPlane.power = true;
                                }
                        }

                        if (playerPlane.power == true) {
                            playerPlane.image = loadImageFromRes("plane4.png");
                            playerPlane.invulnerable--;
                            if (playerPlane.invulnerable == 0) {
                                playerPlane.image = loadImageFromRes("plane3.png");
                                playerPlane.power = false;
                                playerPlane.invulnerable = 300;
                            }
                        }

                        for (PlayerBullet temp1 : playerPlane.bulletList) {
                            for (Bomb temp2 : Bombs) {
                                if (temp1 != null && temp2 != null) {
                                    //System.out.println("Hit");
                                    if ((temp1.y + temp1.height > temp2.y && temp1.y < temp2.y + temp2.height) && (temp1.x + temp1.width > temp2.x && temp1.x < temp2.x + temp2.width)) {
                                        //System.out.println("Bullet x: " + temp1.x +" Bullet y: " +temp1.y);
                                        //System.out.println("Bomb x: " + temp2.x +" Bomb y: " +temp2.y);
                                        temp2.active = false;
                                        temp1.active = false;
                                        score++;
                                        System.out.println("Score: " + score + " (+1)");
                                    }
                                }
                            }
                        }

                        if (playerPlane.power == true) {
                            //System.out.println("Enemy kill 1: " + temp.kill);
                            switch (playerPlane.invulnerable) {
                                case 50:
                                    playerPlane.image = loadImageFromRes("plane3.png");
                                    break;
                                case 40:
                                    playerPlane.image = loadImageFromRes("plane3.png");
                                    break;
                                case 30:
                                    playerPlane.image = loadImageFromRes("plane3.png");
                                    break;
                                case 20:
                                    playerPlane.image = loadImageFromRes("plane3.png");
                                    break;
                                case 10:
                                    playerPlane.image = loadImageFromRes("plane3.png");
                                    break;
                            }
                        }

                        Iterator<EnemyPlane> enemyPlaneKillIterator = enemyPlanes.iterator();

                        while (enemyPlaneKillIterator.hasNext()) {
                            EnemyPlane temp = enemyPlaneKillIterator.next();
                            if (temp.active == false) {
                                //System.out.println("Enemy kill 1: " + temp.kill);
                                switch (temp.kill) {
                                    case 6:
                                        temp.image = loadImageFromRes("explosion6.png");
                                        temp.kill = temp.kill - 1;
                                        break;
                                    case 5:
                                        temp.image = loadImageFromRes("explosion5.png");
                                        temp.kill = temp.kill - 1;
                                        break;
                                    case 4:
                                        temp.image = loadImageFromRes("explosion4.png");
                                        temp.kill = temp.kill - 1;
                                        break;
                                    case 3:
                                        temp.image = loadImageFromRes("explosion3.png");
                                        temp.kill = temp.kill - 1;
                                        break;
                                    case 2:
                                        temp.image = loadImageFromRes("explosion2.png");
                                        temp.kill = temp.kill - 1;
                                        break;
                                    case 1:
                                        temp.image = loadImageFromRes("explosion1.png");
                                        temp.kill = temp.kill - 1;
                                        break;
                                }
                            }
                        }

                        Iterator<Bomb> bombIteratorKill = Bombs.iterator();

                        while (bombIteratorKill.hasNext()) {
                            Bomb temp = bombIteratorKill.next();
                            if (temp.active == false) {
                                //System.out.println("Bomb kill: " + temp.kill);
                                switch (temp.kill) {
                                    case 6:
                                        temp.image = loadImageFromRes("explosion6.png");
                                        temp.kill = temp.kill - 1;
                                        break;
                                    case 5:
                                        temp.image = loadImageFromRes("explosion5.png");
                                        temp.kill = temp.kill - 1;
                                        break;
                                    case 4:
                                        temp.image = loadImageFromRes("explosion4.png");
                                        temp.kill = temp.kill - 1;
                                        break;
                                    case 3:
                                        temp.image = loadImageFromRes("explosion3.png");
                                        temp.kill = temp.kill - 1;
                                        break;
                                    case 2:
                                        temp.image = loadImageFromRes("explosion2.png");
                                        temp.kill = temp.kill - 1;
                                        break;
                                    case 1:
                                        temp.image = loadImageFromRes("explosion1.png");
                                        temp.kill = temp.kill - 1;
                                        break;
                                }
                            }
                        }

                        for (EnemyPlane temp1 : enemyPlanes) {
                            Iterator<EnemyBullet> enemyBulletIterator = temp1.bulletList.iterator();
                            while (enemyBulletIterator.hasNext()) {
                                EnemyBullet temp2 = enemyBulletIterator.next();
                                if (temp2.active == false) enemyBulletIterator.remove();
                            }
                        }

                        Iterator<EnemyPlane> enemyPlaneIterator = enemyPlanes.iterator();

                        Iterator<PlayerBullet> bulletIterator = playerPlane.bulletList.iterator();

                        Iterator<Island> islandIterator = isLands.iterator();

                        Iterator<Bomb> bombIterator = Bombs.iterator();

                        Iterator<PowerUp> powerUpIterator = powerUps.iterator();

                        while (powerUpIterator.hasNext()) {
                            PowerUp temp = powerUpIterator.next();
                            //System.out.println("Power-up state: " + temp.active);
                            if (temp.active == false) powerUpIterator.remove();
                        }

                        while (enemyPlaneIterator.hasNext()) {
                            EnemyPlane temp = enemyPlaneIterator.next();
                            //System.out.println("Enemy state 2: "+ temp.active);
                            //System.out.println("Enemy kill 2: "+ temp.kill);
                            if (temp.active == false && temp.kill == 0) enemyPlaneIterator.remove();
                        }

                        while (bulletIterator.hasNext()) {
                            PlayerBullet temp = bulletIterator.next();
                            //System.out.println("Bullet state: " + temp.active);
                            if (temp.active == false) bulletIterator.remove();
                        }

                        while (bombIterator.hasNext()) {
                            Bomb temp = bombIterator.next();
                            //System.out.println("Bomb state: " + temp.active);
                            if (temp.active == false && temp.kill == 0) bombIterator.remove();
                        }

                        while (islandIterator.hasNext()) {
                            Island temp = islandIterator.next();
                            //System.out.println("Island state: " + temp.active);
                            if (temp.active == false) islandIterator.remove();
                        }

                        background.y += background.speed;
                        backgroundBuffer.y += background.speed;
                        if (background.y == 600) {
                            backgroundBuffer.y = -600;
                            background.y = 0;
                        }

                        if (playerPlane.active == false) {
                            playerPlane.width = 64;
                            playerPlane.height = 64;
                            //System.out.println("Player kill : " + playerPlane.kill);
                            switch (playerPlane.kill) {
                                case 6:
                                    playerPlane.image = loadImageFromRes("explosion6.png");
                                    playerPlane.kill = playerPlane.kill - 1;
                                    break;
                                case 5:
                                    playerPlane.image = loadImageFromRes("explosion5.png");
                                    playerPlane.kill = playerPlane.kill - 1;
                                    break;
                                case 4:
                                    playerPlane.image = loadImageFromRes("explosion4.png");
                                    playerPlane.kill = playerPlane.kill - 1;
                                    break;
                                case 3:
                                    playerPlane.image = loadImageFromRes("explosion3.png");
                                    playerPlane.kill = playerPlane.kill - 1;
                                    break;
                                case 2:
                                    playerPlane.image = loadImageFromRes("explosion2.png");
                                    playerPlane.kill = playerPlane.kill - 1;
                                    break;
                                case 1:
                                    playerPlane.image = loadImageFromRes("explosion1.png");
                                    playerPlane.kill = playerPlane.kill - 1;
                                    break;
                            }
                        }

                        if (playerPlane.active == false && playerPlane.kill == 0) {
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

    private Image loadImageFromRes(String url) {
        try {
            return ImageIO.read(new File("resources/" + url));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Graphics g) {
        if (backBufferImage != null) {
            backGraphics = backBufferImage.getGraphics();

            backGraphics.drawImage(background.image, background.x, background.y, background.width, background.height, null);
            backGraphics.drawImage(backgroundBuffer.image, backgroundBuffer.x, backgroundBuffer.y, backgroundBuffer.width, backgroundBuffer.height, null);

            if (playerPlane.active == false){
                backGraphics.drawImage(gameover.image, gameover.x, gameover.y, gameover.width, gameover.height, null);
            }

            for(Island temp:isLands) {
                if (temp != null) {
                    backGraphics.drawImage(temp.image, temp.x, temp.y,temp.width,temp.height, null);
                }
            }

            for(EnemyPlane temp:enemyPlanes) {
                if (temp != null) {
                    backGraphics.drawImage(temp.image, temp.x, temp.y, temp.width, temp.height, null);
                }
            }

            /*if (bulletExist)
                backGraphics.drawImage(bulletImage, bulletX, bulletY, 13, 33, null);*/
            /*if (playerBullet != null)
                backGraphics.drawImage(playerBullet.image,playerBullet.x,playerBullet.y,13,33,null);*/

            for(PlayerBullet temp:playerPlane.bulletList) {
                if (temp != null) {
                    backGraphics.drawImage(temp.image,temp.x,temp.y,temp.width,temp.height,null);
                }
            }

            for(EnemyPlane temp:enemyPlanes){
                if (temp != null){
                    for(EnemyBullet tempBullet:temp.bulletList){
                        if (tempBullet != null)
                            backGraphics.drawImage(tempBullet.image, tempBullet.x, tempBullet.y, tempBullet.width, tempBullet.height,null);
                    }
                }
            }

            for(PowerUp temp:powerUps) {
                if (temp != null) {
                    backGraphics.drawImage(temp.image,temp.x,temp.y,temp.width,temp.height,null);
                }
            }

            for(Bomb temp:Bombs) {
                if (temp != null) {
                    backGraphics.drawImage(temp.image,temp.x,temp.y,temp.width,temp.height,null);
                }
            }

            backGraphics.drawImage(playerPlane.image, playerPlane.x, playerPlane.y, playerPlane.width, playerPlane.height, null);

            if (active == false){
                backGraphics.drawImage(paused.image, paused.x, paused.y, paused.width, paused.height, null);
            }

            if (playerPlane.active == false){
                backGraphics.drawImage(wasted.image, wasted.x, wasted.y, wasted.width, wasted.height, null);
            }

            g.drawImage(backBufferImage, 0, 0, null);
        }
    }
}