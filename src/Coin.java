import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Coin extends Rect {

  Animation hover = new Animation();

  public Coin(int x, int y, int w, int h) {
    super(x, y, w, h);
    setAnimation();
  }

  private void setAnimation() {
    String hoverCoinPath = "/Assets/Coin/COIN_";

    int DURATION = 100;
    int FRAMECOUNT = 8;

    for (int i = 0; i < FRAMECOUNT; i++) {
      hover.addFrame(loadImage(hoverCoinPath + i + ".png"), DURATION);
    }
  }


  public void update(long deltaTime) {
    hover.update(deltaTime);
  }

  public void draw(Graphics pen) {
//    super.drawWithCamera((Graphics2D) pen);
    pen.drawImage(hover.getImage(), x - Camera.x, y - Camera.y, null);
  }

  private BufferedImage loadImage(String fileName) {
    try {
      return ImageIO.read(getClass().getResource(fileName));
    } catch (Exception ex) {
    }

    return null;
  }
}
