import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public abstract class Entity extends Rect {

  public static final int STATE_NORMAL = 0;
  public static final int STATE_HURT = 1;
  public static final int STATE_DYING = 2;
  public static final int STATE_DEAD = 3;
  public int health = 3;
  public int damage = 1;
  public boolean MV_LT, MV_RT, MV_DN, JMP;
  protected Animation anim;
  //States
  protected int state;

  protected int direction = 1;


  public Entity(int x, int y, int width, int height) {
    super(x, y, width, height);
  }

  public int getWidth() {
    return this.w;
  }

  public int getHeight() {
    return this.h;
  }


  public void setAnimations() {
  }


  public void TakeDamage(int damage) {
    this.health -= damage;
    setState(STATE_HURT);
    if (this.health <= 0) {
      setState(STATE_DEAD);
    }
  }

  public void spawn() {
    MV_LT = true;
  }

  public void Update(long deltaTime) {
    if (getVX() > 0) {
      direction = 1;
    } else if (getVX() < 0) {
      direction = -1;
    }

    //Flip animation
    if (anim != null) {
      if (direction == -1 && !anim.isMirrored()) {
        anim.mirrorAnimation();
      } else if (direction == 1 && anim.isMirrored()) {
        anim.mirrorAnimation();
      }
    }
  }

  public abstract Object clone(int x, int y);

  // draw character sprite
  public void draw(Graphics2D pen) {
//    super.drawWithCamera(pen);
    pen.drawImage(anim.getImage(), x - Camera.x, y - Camera.y, null);
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    if (this.state != state) {
      this.state = state;
    }
  }

  public boolean isAlive() {
    return state == STATE_NORMAL;
  }

  protected BufferedImage loadImage(String fileName) {
    try {
      return ImageIO.read(getClass().getResource(fileName));
    } catch (Exception ex) {
    }

    return null;
  }
}
