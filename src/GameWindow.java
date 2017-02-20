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
        private int windowX = 400;
        private int windowY = 600;
        private int planeX = (400 - 70) / 2;
        private int planeY = 600 - 62;

        public GameWindow() {
            setVisible(true);
            setSize(windowX, windowY);

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
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_RIGHT: {
                            if (planeX + 10 <= windowX - 70)
                                planeX += 10;
                            repaint();
                            System.out.println("keyPressed: Right");
                            //System.out.println("PlaneX:" + planeX);
                        }
                        break;
                        case KeyEvent.VK_LEFT: {
                            if (planeX - 10 >= 0)
                                planeX -= 10;
                            repaint();
                            System.out.println("keyPressed: Left");
                            //System.out.println("PlaneX:" + planeX);
                        }
                        break;
                        case KeyEvent.VK_UP: {
                            if (planeY - 10 >= 28)
                                planeY -= 10;
                            repaint();
                            System.out.println("keyPressed: Up");
                            //System.out.println("PlaneY:" + planeY);
                        }
                        break;
                        case KeyEvent.VK_DOWN: {
                            if (planeY + 10 <= windowY - 62)
                                planeY += 10;
                            repaint();
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
            g.drawImage(planeImage, planeX, planeY, 70, 51, null);
        }
    }

