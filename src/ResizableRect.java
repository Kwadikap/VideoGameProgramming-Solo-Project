import java.awt.Graphics2D;

public class ResizableRect extends Rect {

  Rect resizer;
  int rectSize = 10;

  public ResizableRect(int x, int y, int w, int h) {
    super(x, y, w, h);

    resizer = new Rect(x + w - rectSize, y + h - rectSize, rectSize, rectSize);
  }

  public ResizableRect(int x, int y, int w, int h, Type type) {
    super(x, y, w, h, type);

    resizer = new Rect(x + w - rectSize, y + h - rectSize, rectSize, rectSize, type);
  }


  public void moveBy(int dx, int dy) {
    super.moveBy(dx, dy);

    resizer.moveBy(dx, dy);
  }

  public void resizeBy(int dx, int dy) {
    super.resizeBy(dx, dy);

    resizer.moveBy(dx, dy);
  }

  public void draw(Graphics2D pen) {
    super.draw(pen);

    resizer.draw(pen);
  }

}
