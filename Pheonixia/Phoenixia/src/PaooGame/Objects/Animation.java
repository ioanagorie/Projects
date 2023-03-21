package PaooGame.Objects;

import java.awt.image.BufferedImage;

/*!\class Animation
   \brief Clasa care se ocupa de caracteristicile afisarii imaginilor
 */
public class Animation {

    private BufferedImage[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    /*! \fn public Animation()
        \brief Constructorul de initializare al clasei Animation.

     */
    public Animation() {

        playedOnce = false;
    }

    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        playedOnce = false;
    }

    /*! \fn  public void setDelay(long d)
        \brief Seteaza delay-ul

        \param d delay-ul

     */
    public void setDelay(long d) {
        delay = d;
    }
    public void setFrame(int i) {
        currentFrame = i;
    }

    /*! \fn  public void update()
        \brief Actualizeaza animatia

     */
    public void update() {

        if(delay == -1) return;

        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if(elapsed > delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame == frames.length) {
            currentFrame = 0;
            playedOnce = true;
        }

    }

    public int getFrame() {
        return currentFrame;
    }
    public BufferedImage getImage() {
        return frames[currentFrame];
    }
    public boolean hasPlayedOnce() {
        return playedOnce;
    }

}
