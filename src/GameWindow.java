import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Random;

public class GameWindow extends Frame {
    private static final int SPEED = 8;
    private static final int ENEMY_SPEED = 3;
    private static final int BULLET_SPEED = 10;

    Image backgroundImage;
    Image planeImage;
    Image enemyImage;
    Image bulletImage;

    private static final int windowX = 400;
    private static final int windowY = 600;

    private int planeX = (windowX - 70) / 2;
    private int planeY = windowY - 62;

    private int planeWidth = 70;
    private int planeHeight = 51;

    /*private int bulletX = (windowX - 13) /2;
    private int bulletY = windowY - planeY - 33;
    boolean bulletExist=false;*/

    private BufferedImage backBufferImage;
    private Graphics backGraphics;

    private Graphics g;

    private int clock = 0;

    private PlayerPlane playerPlane;

    ArrayList<EnemyPlane> enemyPlanes = new ArrayList<EnemyPlane>();

    Thread thread;

    public GameWindow() {
        setVisible(true);
        setSize(windowX, windowY);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.out.println("windowClosing");
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                System.out.printf("windowClosed");
            }
        });

        // 1: Load image
        backgroundImage = loadImageFromRes("background.png");

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
                        if (playerPlane.x + playerPlane.speed <= windowX - 70)
                            playerPlane.x += playerPlane.speed;
                        //System.out.println("keyPressed: Right");
                        //System.out.println("PlaneX:" + planeX);
                    }
                    break;
                    case KeyEvent.VK_LEFT: {
                        if (playerPlane.x - playerPlane.speed >= 0)
                            playerPlane.x -= playerPlane.speed;
                        //System.out.println("keyPressed: Left");
                        //System.out.println("PlaneX:" + planeX);
                    }
                    break;
                    case KeyEvent.VK_UP: {
                        if (playerPlane.y - playerPlane.speed >= 28)
                            playerPlane.y -= playerPlane.speed ;
                        //System.out.println("keyPressed: Up");
                        //System.out.println("PlaneY:" + planeY);
                    }
                    break;
                    case KeyEvent.VK_DOWN: {
                        if (playerPlane.y + playerPlane.speed <= windowY - 62)
                            playerPlane.y += playerPlane.speed;
                        //System.out.println("keyPressed: Down");
                        //System.out.println("PlaneY:" + planeY);
                    }
                    break;
                    case KeyEvent.VK_R: {
                        playerPlane.image = loadImageFromRes("plane4.png");
                    }break;
                    case KeyEvent.VK_SPACE: {
                        PlayerBullet playerBullet = new PlayerBullet((playerPlane.x + 35 -6),playerPlane.y - 10,BULLET_SPEED,13,33,loadImageFromRes("bullet.png"));
                        playerPlane.bulletList.add(playerBullet);
                        //bulletExist=true;
                        //System.out.println("keyPressed: Space");
                    }break;
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

                    clock = clock +1;

                    //System.out.println("clock: "+clock);

                    if (clock == 150){
                        Random rd = new Random();
                        Random randomX = new Random();
                        int type = rd.nextInt(3);
                        //System.out.println("Type: "+type);

                        switch(type){
                            case 0: {
                                EnemyPlane enemyPlane = new EnemyPlane(randomX.nextInt(windowX-31), 0, 32, 32, ENEMY_SPEED, loadImageFromRes("enemy_plane_white_1.png"));
                                enemyPlanes.add(enemyPlane);
                                clock = 0;
                            }break;
                            case 1: {
                                EnemyPlane enemyPlane = new EnemyPlane(randomX.nextInt(windowX-31), 0, 32, 32, ENEMY_SPEED, loadImageFromRes("enemy_plane_yellow_1.png"));
                                enemyPlanes.add(enemyPlane);
                                clock = 0;
                            }break;
                            case 2: {
                                EnemyPlane enemyPlane = new EnemyPlane(randomX.nextInt(windowX-27), 0, 32, 32, ENEMY_SPEED, loadImageFromRes("enemy-green-3.png"));
                                enemyPlanes.add(enemyPlane);
                                clock = 0;
                            }break;
                        }
                    }

                    /*bulletY -= BULLET_SPEED;
                    if (bulletY - BULLET_SPEED <= 0){
                        bulletExist = false;
                        bulletY = windowY - planeY - 33;
                    }*/

                    for(PlayerBullet temp:playerPlane.bulletList) {
                        if (temp != null) {
                            temp.y -= temp.speed;
                        }
                    }

                    for(EnemyPlane temp:enemyPlanes){
                        if (temp != null){
                            temp.y += temp.speed;
                        }
                    }

                    for(EnemyPlane temp:enemyPlanes){
                        if (temp != null){
                            if (clock%10==0){
                                //System.out.println("Incoming!");
                                EnemyBullet enemyBullet = new EnemyBullet(temp.x,temp.y+5,32,32,BULLET_SPEED,loadImageFromRes("enemy_bullet.png"));
                                temp.bulletList.add(enemyBullet);
                            }
                        }
                    }

                    for(EnemyPlane temp:enemyPlanes){
                        if (temp != null){
                            for(EnemyBullet tempBullet:temp.bulletList){
                                tempBullet.y += tempBullet.speed;
                            }
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

            backGraphics.drawImage(backgroundImage, 0, 0, windowX, windowY, null);
            backGraphics.drawImage(playerPlane.image, playerPlane.x, playerPlane.y, playerPlane.width, playerPlane.height, null);

            for(EnemyPlane temp:enemyPlanes) {
                if (temp != null) {
                    backGraphics.drawImage(temp.image, temp.x, temp.y,temp.width,temp.height, null);
                }
            }

            /*if (bulletExist)
                backGraphics.drawImage(bulletImage, bulletX, bulletY, 13, 33, null);*/
            /*if (playerBullet != null)
                backGraphics.drawImage(playerBullet.image,playerBullet.x,playerBullet.y,13,33,null);*/

            for(PlayerBullet temp:playerPlane.bulletList) {
                if (temp != null) {
                    backGraphics.drawImage(temp.image,temp.x,temp.y,13,33,null);
                }
            }

            for(EnemyPlane temp:enemyPlanes){
                if (temp != null){
                    for(EnemyBullet tempBullet:temp.bulletList){
                        backGraphics.drawImage(tempBullet.image,tempBullet.x,tempBullet.y,tempBullet.width,tempBullet.height,null);
                    }
                }
            }

            g.drawImage(backBufferImage, 0, 0, null);
        }
    }
}

