package PaooGame.Objects;

import PaooGame.Graphics.Assets;
import PaooGame.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

/*!\class Life
   \brief Clasa Life implementeaza notiunea de viata.
 */
public class Life extends Item{
    private BufferedImage image;
    private boolean isHit = false;
    /*! \fn public Life(Handler handler, float x, float y, int width, int height)
           \brief Constructor de initializare a clasei Life

           \param handler Referinta catre obiectul shortcut (care retine alte referinte utile/necesare in joc).
           \param x Pozitia de start pa axa X a vietii.
           \param y Pozitia de start pa axa Y a vietii.
           \param width Latimea imaginii vietii.
           \param height Inaltimea imaginii vietii.
        */
    public Life(Handler handler, float x, float y, int width, int height){
        ///Apel al constructorului clasei de baza
        super(handler, x, y, width, height);
        image = Assets.inimafull;
    }
    /*! \fn public void Update()
          \brief Actualizeaza pozitia vietii.
       */
    @Override
    public void Update() {
        if (isHit)
            image = Assets.inimacrapata;
        else
            image = Assets.inimafull;
    }
    /*! \fn public void Draw(Graphics g)
        \brief Randeaza/deseneaza viata in noua pozitie.

        \param g Contextul grafic in care trebuie efectuata desenarea vietii.
     */
    @Override
    public void Draw(Graphics g) {
        g.drawImage(image, (int)x, (int)y, width, height, null);
    }

    public BufferedImage getImage() {
        return image;
    }


    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public boolean getHit(){
        return this.isHit;
    }
}
