import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class GameEngine implements Runnable, MouseListener, MouseMotionListener {

  protected ScreenManager screenManager;
  protected SoundManager soundManager;
  int mx = -1;
  int my = -1;

  ResizableRect[] gameObjects = GameObjects.getGameObjects();

  private boolean isRunning;

  public void run() {
    init();
    GameLoop();
  }

  public void init() {
    screenManager = new ScreenManager();
    soundManager = new SoundManager();
    isRunning = true;

  }

  public void GameLoop() {
    long currentTime = System.currentTimeMillis();

    while (isRunning) {
      long elapsedTime = System.currentTimeMillis() - currentTime;
      currentTime += elapsedTime;

      //update
      update(elapsedTime);

      // draw to screen
      Graphics2D pen = screenManager.getGraphics();
      draw(pen);
      pen.dispose();
      screenManager.update();

      try {
        Thread.sleep(16);
      } catch (InterruptedException ex) {
      }
    }
  }

  public void update(long elapsedTime) {
  }

  public abstract void draw(Graphics2D pen);


  public void stop() {
    isRunning = false;
  }

  public void mouseDragged(MouseEvent e) {
    int nx = e.getX();
    int ny = e.getY();

    int dx = nx - mx;
    int dy = ny - my;

    for (int i = 0; i < gameObjects.length; i++) {
      if (gameObjects[i].resizer.held) {
        gameObjects[i].resizeBy(dx, dy);
      } else if (gameObjects[i].held) {
        gameObjects[i].moveBy(dx, dy);
      }
    }

    mx = nx;
    my = ny;
  }

  public void mousePressed(MouseEvent e) {
    mx = e.getX();
    my = e.getY();

    for (int i = 0; i < gameObjects.length; i++) {
      if (gameObjects[i].contains(mx, my)) {
        gameObjects[i].grabbed();
      }
      if (gameObjects[i].resizer.contains(mx, my)) {
        gameObjects[i].resizer.grabbed();
      }
    }
  }

  public void mouseReleased(MouseEvent e) {
    for (int i = 0; i < gameObjects.length; i++) {
      gameObjects[i].dropped();
      gameObjects[i].resizer.dropped();
    }
  }

  public void mouseClicked(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  public void mouseMoved(MouseEvent e) {
  }
}
