import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class Program {
    public static void main(String[] args){
        System.out.println("                        <<====+  T O P  G U N  +===>>");
        GameWindow gamewindow = new GameWindow();
        gamewindow.start();
    }
}