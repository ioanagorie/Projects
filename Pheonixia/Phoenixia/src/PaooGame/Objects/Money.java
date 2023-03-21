package PaooGame.Objects;

import PaooGame.Graphics.Assets;
import PaooGame.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

/*!\class Money
   \brief Clasa Money implementeaza notiunea de banut.
 */
public class Money extends Personaj {

    private boolean lovit;
    boolean right;
    private BufferedImage[] sprites;
    private BufferedImage[] hitSprites;//animatia care se joaca
    private BufferedImage image;
    private boolean draw = false;
    private float distanceTravelled = 0;

    /*! \fn public Money(Handler handler, float x, float y, int width, int height)
           \brief Constructorul de initializare al clasei Money.

           \param handler Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
           \param x Pozitia initiala pe axa X a banutului.
           \param y Pozitia initiala pe axa Y a banutului.
           \param width Latimea banutului
           \param height Lungimea banutului
        */
    public Money(Handler handler, float x, float y, int width, int height) {
        ///Apel al constructorului clasei de baza
        super(handler, x, y, width, height);

        image = Assets.money;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        speed=3f;

        try {
            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(70);//intarziere

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    /*! \fn public void Update()
       \brief Se deplaseaza banutul pe ecran din dreapta spre stanga
    */
    @Override
    public void Update() {
        x = x - speed;
        distanceTravelled += Math.abs(speed);
        if (distanceTravelled < 0){
            remove = true;
        }

    }
    /*! \fn public void setHit()
        \brief Daca se detecteaza o coliziune intre banut si player,
        se seteaza flagul lovit
     */
    public void setHit() {
        if (lovit) return;
        lovit = true;
        //animation.setFrames(hitSprites);
        animation.setDelay(0);
        x = 0;
    }

    /*! \fn public boolean ToBeRemoved()
       \brief Se returneaza functia de remove
    */
    public boolean ToBeRemoved() {
        return remove;
    }

    /*! \fn public void setDraw(boolean val)
      \brief Se marcheaza daca trebuie sa se realizeze sau nu desenarea banutului
      \param val referinta catre valoarea curenta care indica daca trebuie sau nu desenat banutul
    */
    public void setDraw(boolean val) {
        draw = val;
    }
    /*! \fn public void getDraw()
      \brief Se returneaza 1 daca trebuie sa se realizeze desenarea banutului, si 0 in caz contrat

    */

    public boolean getDraw() {
        return draw;
    }

    /*! \fn public void setX(int x)
      \brief Se seteaza pozitia curenta pe axa X a banutului
      \param x referinta catre valoarea curenta pe axa X a banutului
    */

    public void setX(int x) {
        this.x = x;
    }
    /*! \fn public void setY(int y)
      \brief Se seteaza pozitia curenta pe axa Y a banutului
      \param y referinta catre valoarea curenta pe axa Y a banutului
    */

    public void setY(int y) {
        this.y = y;
    }

    /*! \fn public void Draw(Graphics g)
       \brief Randeaza/deseneaza banutul in noua pozitie.

       \param g Contextul grafic in care trebuie efectuata desenarea banutului.
    */
    public void Draw(Graphics g) {

        g.drawImage(image, (int) x, (int) y, 40, 40, null);

    }
}
