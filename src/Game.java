import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game extends GameEngine {

  // keep track of counts collected
  public int coinsColleted = 0;
  // Player
  public Cat player;
  public Rat enemy;

  public ArrayList<Coin> coins;
  public Coin displayCoin;

  protected InputManager inputManager;
  // Game actions
  protected GameAction MV_RT, MV_LT, JMP, MV_DN, ATK, PAUSE, EXT, HURT, DIE;
  private ImageLayer bg;
  private boolean paused;

  public static void main(String[] args) {
    new Game().run();
  }

  public void init() {
    super.init();

    createGameActions();
    loadSprite();
    loadMap(1);
    paused = false;
  }

  public void createGameActions() {
    MV_RT = new GameAction("MoveRight");
    MV_LT = new GameAction("MoveLeft");
    MV_DN = new GameAction("MoveDown");
    JMP = new GameAction("Jump", GameAction.INIT_PRESS_ONLY);
    ATK = new GameAction("Attack", GameAction.INIT_PRESS_ONLY);
    PAUSE = new GameAction("Pause", GameAction.INIT_PRESS_ONLY);
    EXT = new GameAction("Exit", GameAction.INIT_PRESS_ONLY);
    HURT = new GameAction("Hurt", GameAction.INIT_PRESS_ONLY);
    DIE = new GameAction("Die", GameAction.INIT_PRESS_ONLY);

    inputManager = new InputManager(screenManager.getWindow());
    screenManager.getWindow().addMouseListener(this);
    screenManager.getWindow().addMouseMotionListener(this);

    inputManager.MapToKey(MV_RT, KeyEvent.VK_RIGHT);
    inputManager.MapToKey(MV_LT, KeyEvent.VK_LEFT);
    inputManager.MapToKey(MV_DN, KeyEvent.VK_DOWN);
    inputManager.MapToKey(JMP, KeyEvent.VK_UP);
    inputManager.MapToKey(ATK, KeyEvent.VK_Z);
    inputManager.MapToKey(PAUSE, KeyEvent.VK_P);
    inputManager.MapToKey(EXT, KeyEvent.VK_ESCAPE);
    inputManager.MapToKey(HURT, KeyEvent.VK_X);
    inputManager.MapToKey(DIE, KeyEvent.VK_C);

  }

  public void loadSprite() {
    player = new Cat(0, 0, 50, 50, "blue");
    enemy = new Rat(300, 0, 32, 32);
  }

  public void loadMap(int lvl) {
    bg = new ImageLayer("/Assets/Maps/mockup.png", 0, 0);
    coins = new GameObjects().getCoinsObjects();
    displayCoin = new Coin(15, 35, 34, 33);
    soundManager.playBackgroundMusic();
  }

  // Checks whether the game is paused or not
  public boolean gamePaused() {
    return paused;
  }

  public void setPaused(boolean p) {
    if (this.paused != p) {
      this.paused = p;
      inputManager.resetGameActions();
    }
  }

  public void update(long elapsedTime) {
    // checks inputs that can happen whether game is paused or not
    checkSystemInput();

    if (!gamePaused()) {
      // game can only be played if not paused
      checkGameInput();

      // update the sprite
      player.update(elapsedTime);
      enemy.update(elapsedTime);

      for (Coin coin : coins) {
        coin.update(elapsedTime);
      }

      displayCoin.update(elapsedTime);

      if (player.getState() == Cat.STATE_DEAD) {
        soundManager.playGameOverSE();
        soundManager.stopBackgroundMusic();
        stop();
      }

      if (player.overlaps(enemy)) {
        player.setState(Cat.STATE_DYING);
      }

      if (player.x < 0) {
        player.setX(0);
      }
    }
    // collision detection
    for (int i = 0; i < coins.size(); i++) {
      if (player.overlaps(coins.get(i))) {
        soundManager.playCoinSE();
        coins.remove(coins.get(i));
        coinsColleted++;
      }
    }

    for (ResizableRect r : gameObjects) {
      if (player.overlaps(r)) {
        if (r.type == Type.SPIKES) {
          player.setState(Cat.STATE_DYING);
        } else if (r.type == Type.LADDER) {
          player.physicsOff();
        } else {
          player.pushedOutOf(r);
        }
      }

      if (enemy.overlaps(r)) {
        enemy.pushedOutOf(r);
      }
    }

  }

  @Override
  public void draw(Graphics2D pen) {
    bg.drawWithCamera(pen);

    for (Coin coin : coins) {
      coin.draw(pen);
    }

    player.draw(pen);
    enemy.draw(pen);

    drawCoinsCollected(pen);

  }

  public void drawCoinsCollected(Graphics2D pen) {
    displayCoin.draw(pen);

    pen.setFont(new Font("Arial", Font.BOLD, 16));
    pen.setColor(Color.ORANGE);
    pen.drawString("" + coinsColleted, 50, 50);
    pen.setColor(Color.BLACK);
  }

  /*
   * checks game actions input that can be pressed whether game is paused or not
   * i.e. pause button
   */
  public void checkSystemInput() {
    if (PAUSE.IsPressed()) {
      setPaused(!gamePaused());
    }
    if (EXT.IsPressed()) {
      stop();
    }

  }

  /*
   * checks game actions input that can only be pressed when the game is not
   * paused
   */
  public void checkGameInput() {

    if (player.isAlive()) {
      player.MV_RT = MV_RT.IsPressed();
      player.MV_LT = MV_LT.IsPressed();
      player.MV_DN = MV_DN.IsPressed();
      player.JMP = JMP.IsPressed();

      player.move();

      enemy.chase(player, 2);

      if (ATK.IsPressed()) {
        player.setState(Cat.STATE_ATTACKING);
      }

      if (HURT.IsPressed()) {
        player.setState(Cat.STATE_HURT);
      }

      if (DIE.IsPressed()) {
        player.setState(Cat.STATE_DEAD);
      }

//      if (player.getX() > bg.image.getWidth(null) * .75) {
      if (MV_RT.IsPressed()) {
        Camera.moveRT(2);
      }

      if (Camera.x > 0 && MV_LT.IsPressed()) {
        Camera.moveLT(2);
      }


    }

  }

}
