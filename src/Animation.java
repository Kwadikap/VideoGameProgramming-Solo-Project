import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
 * This class manages a series of images and the amount of time to display each frame
 */
public class Animation {

  private final ArrayList<AnimFrame> frames;
  private int currFrameIndex;
  private long animTime;
  private long totalDuration;
  private boolean isMirrored;

  public Animation() {
    frames = new ArrayList<AnimFrame>();
    totalDuration = 0;
  }

  private Animation(ArrayList<AnimFrame> frames, long totalDuration) {
    this.frames = frames;
    this.totalDuration = totalDuration;

    start();
  }

  /*
   * Add image to the animation with a specific time to display the image
   */
  public synchronized void addFrame(BufferedImage image, long duration) {
    totalDuration += duration;
    frames.add(new AnimFrame(image, totalDuration));
  }

  /*
   * restarts animation from beginning
   */
  public synchronized void start() {
    animTime = 0;
    currFrameIndex = 0;
  }

  /*
   * updates animation's current image (frame), if needed
   */
  public synchronized void update(long elapsedTime) {
    if (frames.size() > 1) {
      animTime += elapsedTime;

      if (animTime >= totalDuration) {
        animTime = animTime % totalDuration;
        currFrameIndex = 0;
      }
      while (animTime > getFrame(currFrameIndex).endTime) {
        currFrameIndex++;
      }
    }
  }

  /*
   * Gets the animation's current image or null if animation has no images
   */
  public synchronized BufferedImage getImage() {
    if (frames.size() == 0) {
      return null;
    } else {
      return getFrame(currFrameIndex).image;
    }
  }

  private AnimFrame getFrame(int i) {
    return frames.get(i);
  }

  public Object clone() {
    return new Animation(frames, totalDuration);
  }

  public Animation mirrorAnimation() {
    frames.replaceAll(animFrame -> getMirroredFrame(animFrame));

    isMirrored = !isMirrored;

    return this;
  }

  public AnimFrame getMirroredFrame(AnimFrame animFrame) {
    animFrame.image = getMirroredImage(animFrame.image);
    return animFrame;
  }

  // Mirrors an Image
  public BufferedImage getMirroredImage(BufferedImage image) {
    // Flip the image vertically
    AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
    tx.translate(-image.getWidth(null), 0);
    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    image = op.filter(image, null);
    return image;
  }

  public boolean isMirrored() {
    return this.isMirrored;
  }

  public int getLength() {
    return frames.size();
  }

  public int getCurrentFrame() {
    return currFrameIndex;
  }

  public boolean ended() {
    return getLength() - 1 == getCurrentFrame();
  }

  private class AnimFrame {

    BufferedImage image;
    long endTime;


    public AnimFrame(BufferedImage image, long endTime) {
      this.image = image;
      this.endTime = endTime;
    }

  }

}
