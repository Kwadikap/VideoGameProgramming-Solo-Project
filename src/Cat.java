import java.awt.Graphics;
import java.awt.Graphics2D;

public class Cat extends Entity {

  // states
  public static final int STATE_ATTACKING = 3;
  public static int SPEED = 5;
  public boolean isMoving = false;
  // cat color
  public String color;
  // Animations
  Animation idle = new Animation();
  Animation run = new Animation();
  Animation atk = new Animation();
  Animation hurt = new Animation();
  Animation die = new Animation();
  private int floorY;

  public Cat(int x, int y, int width, int height, String color) {
    super(x, y, width, height);
    this.color = color;
    setAnimations();
  }

  public void setAnimations() {
    // Animation image paths
    String idleImagePath = "/Assets/Cat/" + color + "/IDL_";
    String runImagePath = "/Assets/Cat/" + color + "/RN_";
    String atkImagePath = "/Assets/Cat/" + color + "/ATK_";
    String hurtImagePath = "/Assets/Cat/" + color + "/HURT_";
    String dieImagePath = "/Assets/Cat/" + color + "/DIE_";

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
    int RUN_FRAME_COUNT = 6;
    for (int i = 0; i < RUN_FRAME_COUNT; i++) {
      run.addFrame(loadImage(runImagePath + i + ".png"), RUN_DURATION);
    }

    // initialize atk animation
    int ATK_FRAME_COUNT = 4;
    for (int i = 0; i < ATK_FRAME_COUNT; i++) {
      atk.addFrame(loadImage(atkImagePath + i + ".png"), DURATION);
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
    if (state == STATE_ATTACKING) {
      newAnimation = atk;
    }
    if (state == STATE_HURT) {
      newAnimation = hurt;
    }
    if (state == STATE_DYING) {
      newAnimation = die;
    }

    if (isGrounded()) {
      setVX(0);
    }

    // update animation
    if (anim != newAnimation) {
      anim = newAnimation;
      anim.start();
    } else {
      anim.update(deltaTime);
    }

    // Stop attacking
    if (state == STATE_ATTACKING && anim.ended()) {
      resetState();
    }

    if (state == STATE_HURT && anim.ended()) {
      resetState();
    }

    if (state == STATE_DYING && anim.ended()) {
      setState(STATE_DEAD);
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
        jump(10);
      }
      if (MV_DN) {
        goDN(5);
      }
      super.move();
    }
  }


  public void resetState() {
    setState(STATE_NORMAL);
  }

  public void draw(Graphics pen) {
    super.draw((Graphics2D) pen);
  }

  @Override
  public Object clone(int x, int y) {
    return null;
  }
}

