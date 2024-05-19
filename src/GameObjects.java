import java.util.ArrayList;

public class GameObjects {

  private static final ResizableRect[] gameObjects = {
      // floors
      new ResizableRect(0, 517, 170, 49, Type.FLOOR),
      new ResizableRect(153, 574, 184, 49, Type.FLOOR),
      new ResizableRect(-2, 865, 402, 94, Type.FLOOR),
      new ResizableRect(1, 668, 340, 72, Type.FLOOR),
      new ResizableRect(404, 576, 1200, 294, Type.FLOOR),
      new ResizableRect(1625, 636, 1200, 294, Type.FLOOR),

      // blocks
      new ResizableRect(113, 748, 59, 114, Type.BLOCK),
      new ResizableRect(977, 457, 173, 113, Type.BLOCK),
      new ResizableRect(1321, 456, 58, 109, Type.BLOCK),

      // ladder
      new ResizableRect(349, 576, 46, 283, Type.LADDER),

      // spikes
      new ResizableRect(1149, 556, 164, 17, Type.SPIKES),

  };

  private static final ArrayList<Coin> coinObjects = new ArrayList<Coin>();

  public GameObjects() {
    coinObjects.add(new Coin(561, 407, 34, 33));
    coinObjects.add(new Coin(215, 773, 34, 33));
    coinObjects.add(new Coin(1038, 316, 34, 33));
    coinObjects.add(new Coin(1212, 310, 34, 33));

  }


  public static ResizableRect[] getGameObjects() {
    return gameObjects;
  }

  public ArrayList<Coin> getCoinsObjects() {
    return coinObjects;
  }
}
