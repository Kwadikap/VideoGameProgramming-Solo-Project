import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLayer {

  public Image image;

  int x;
  int y;


  public ImageLayer(String filename, int x, int y) {
    try {
      image = ImageIO.read(getClass().getResource(filename));
    } catch (IOException ignored) {
    }
    this.x = x;
    this.y = y;
  }


  public void draw(Graphics pen) {
    pen.drawImage(image, x, y, null);
  }

  public void drawWithCamera(Graphics pen) {
    for (int i = 0; i < 10; i++) {
      pen.drawImage(image, x + i * 5520 - Camera.x, y - Camera.y, 5520, 920, null);
    }
  }

}