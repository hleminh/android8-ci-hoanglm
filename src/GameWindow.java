import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

    public class GameWindow extends Frame {
        Image backgroundImage;
        Image planeImage;
        private int planeX = (400 - 35) / 2;

        public GameWindow() {
            setVisible(true);
            setSize(400, 600);

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);System.out.println("windowClosing");
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
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        // TODO: move plane to right
                        planeX += 10;
                        repaint();
                        System.out.println("keyPressed");
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    super.keyReleased(e);
                }
            });
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
            g.drawImage(backgroundImage, 0, 0, 400, 600, null);
            g.drawImage(planeImage, planeX, 600 - 25, 35, 25, null);
        }
    }

