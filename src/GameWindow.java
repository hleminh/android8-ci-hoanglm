import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameWindow extends Frame {
    private static final int SPEED = 10;
    Image backgroundImage;
    Image planeImage;
    private static final int windowX = 400;
    private static final int windowY = 600;
    private int planeX = (windowX - 70) / 2;
    private int planeY = windowY - 62;

    private BufferedImage backBufferImage;
    private Graphics backGraphics;

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
        planeImage = loadImageFromRes("plane3.png");

        // 2: Draw image
        repaint();

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
                        if (planeX + SPEED <= windowX - 70)
                            planeX += SPEED;
                        System.out.println("keyPressed: Right");
                        //System.out.println("PlaneX:" + planeX);
                    }
                    break;
                    case KeyEvent.VK_LEFT: {
                        if (planeX - SPEED >= 0)
                            planeX -= SPEED;
                        System.out.println("keyPressed: Left");
                        //System.out.println("PlaneX:" + planeX);
                    }
                    break;
                    case KeyEvent.VK_UP: {
                        if (planeY - SPEED >= 28)
                            planeY -= SPEED;
                        System.out.println("keyPressed: Up");
                        //System.out.println("PlaneY:" + planeY);
                    }
                    break;
                    case KeyEvent.VK_DOWN: {
                        if (planeY + SPEED <= windowY - 62)
                            planeY += SPEED;
                        System.out.println("keyPressed: Down");
                        //System.out.println("PlaneY:" + planeY);
                    }
                    break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(17);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
            Image image = ImageIO.read(new File("resources/" + url));
            return image;
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
            backGraphics.drawImage(planeImage, planeX, planeY, 70, 51, null);

            g.drawImage(backBufferImage, 0, 0, null);
        }
    }
}

