import java.awt.Graphics;
import java.awt.Graphics2D;

public class Rat extends Entity {

  // states
  public static int SPEED = 5;
  public boolean isMoving = false;

  // Animations
  Animation idle = new Animation();
  Animation run = new Animation();
  Animation hurt = new Animation();
  Animation die = new Animation();

  private int floorY;

  public Rat(int x, int y, int width, int height) {
    super(x, y, width, height);
    setAnimations();
  }

  public void setAnimations() {
    // Animation image paths
    String idleImagePath = "/Assets/Rat/IDLE_";
    String runImagePath = "/Assets/Rat/MV_";
    String hurtImagePath = "/Assets/Rat/HURT_";
    String dieImagePath = "/Assets/Rat/DIE_";

    // Animation duration
    int DURATION = 100;
    int RUN_DURATION = 70;
    int HURT_DURATION = 160;
    int DIE_DURATION = 220;

    // initialize idle animation
    int IDLE_FRAME_COUNT = 4;
    for (int i = 0; i < IDLE_FRAME_COUNT; i++) {
      idle.addFrame(loadImage(idleImagePath + i + ".png"), DURATION);
    }

    // initialize run animation
    int RUN_FRAME_COUNT = 4;
    for (int i = 0; i < RUN_FRAME_COUNT; i++) {
      run.addFrame(loadImage(runImagePath + i + ".png"), RUN_DURATION);
    }

    // initialize hurt animation
    int HURT_FRAME_COUNT = 2;
    for (int i = 0; i < HURT_FRAME_COUNT; i++) {
      hurt.addFrame(loadImage(hurtImagePath + i + ".png"), HURT_DURATION);
    }

    // initialize die animation
    int DIE_FRAME_COUNT = 4;
    for (int i = 0; i < DIE_FRAME_COUNT; i++) {
      die.addFrame(loadImage(dieImagePath + i + ".png"), DIE_DURATION);
    }

  }

  // Set the floor location where the player will start and land after jump
  public void setFloorY(int floorY) {
    this.floorY = floorY;
    setY(floorY);
  }

  // Update animation and state
  public void update(long deltaTime) {
    super.Update(deltaTime);

    // Update animation
    Animation newAnimation = anim;
    isMoving = MV_LT || MV_RT;

    if (state == STATE_NORMAL && !isMoving) {
      newAnimation = idle;
    }
    if (state == STATE_NORMAL && isMoving) {
      newAnimation = run;
    }
    if (state == STATE_HURT) {
      newAnimation = hurt;
    }
    if (state == STATE_DEAD) {
      newAnimation = die;
    }

    if (isGrounded()) {
      setVX(0);
    }

    // check if player has landed
//    if (getY() >= floorY) {
//      setVY(0);
//    }

    // update animation
    if (anim != newAnimation) {
      anim = newAnimation;
      anim.start();
    } else {
      anim.update(deltaTime);
    }

    if (state == STATE_HURT && anim.ended()) {
      resetState();
    }

    if (state == STATE_DEAD && anim.ended()) {
      resetState();
    }
  }


  public boolean isGrounded() {
    return y == floorY;
  }

  public void move() {
    if (state == STATE_NORMAL) {
      if (MV_RT) {
        setVX(SPEED);
      }
      if (MV_LT) {
        setVX(-SPEED);
      }
      if (JMP) {
        jump(15);
      }
      super.move();
    }
  }


  public void resetState() {
    setState(STATE_NORMAL);
  }

  public void draw(Graphics g) {
    super.draw((Graphics2D) g);
  }

  @Override
  public Object clone(int x, int y) {
    return null;
  }
}

