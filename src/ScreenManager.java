import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;

public class ScreenManager {

  JFrame window;
  int windowWidth, windowHeight;
  private final GraphicsDevice device;

  public ScreenManager() {
    GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    device = environment.getDefaultScreenDevice();
    windowWidth = device.getDisplayMode().getWidth();
    windowHeight = device.getDisplayMode().getHeight();
    createWindow("Plat2D");
  }

  public void createWindow(String windowTitle) {

    window = new JFrame(windowTitle);

    window.setFocusable(true);
    window.requestFocusInWindow();

    window.setSize(windowWidth, windowHeight);
    window.setVisible(true);
    window.setFocusable(true);
    window.setResizable(false);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // avoid potential deadlock in 1.4.1_02
    try {
      EventQueue.invokeAndWait(
          new Runnable() {
            public void run() {
              window.createBufferStrategy(2);
            }
          });
    } catch (InterruptedException ex) {
    } catch (InvocationTargetException ex) {
    }
  }

  public Graphics2D getGraphics() {
    if (window != null) {
      BufferStrategy strategy = window.getBufferStrategy();
      return (Graphics2D) strategy.getDrawGraphics();
    } else {
      return null;
    }
  }

  public void update() {
    if (window != null) {
      BufferStrategy strategy = window.getBufferStrategy();
      if (!strategy.contentsLost()) {
        strategy.show();
      }
    }
  }

  public int getWidth() {
    return window.getWidth();
  }

  public int getHeight() {
    return window.getHeight();
  }

  public Container getWindow() {
    return window;
  }

  public void setContentPane(Container contentPane) {
    window.setContentPane(contentPane);
  }

}
