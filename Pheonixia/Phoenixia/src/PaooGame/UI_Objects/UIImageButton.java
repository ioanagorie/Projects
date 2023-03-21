package PaooGame.UI_Objects;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
/*!
\class UIImageButton
\brief Implementare Concreta a UIObject. Reprezinta un buton.
 */
public class UIImageButton extends UIObject {
    /// Fiecare buton are 2 imagini : una cand mouseul nu este deasupra lui si una cand este. Cele doua imagini sunt stocate in aceasta variabila.
    private BufferedImage images;

    /// Referinta catre un obiect clicker.
    /// Pentru fiecare buton se poate suprascrie metoda clicler.CLicked() oferind astfel comportament dinamic pentru fiecare buton intr-un mod flexibil.
    private ClickListener clicker;

    /*!
    \fn public UIImageButton(float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker)
    \brief Constructor pentru un buton din UI. El seteaza coordonatele, inaltimea, latimea si referinta catre atributul ClickListener.
     */
    public UIImageButton(float x, float y, int width, int height, BufferedImage images, ClickListener clicker) {
        super(x, y, width, height);
        this.images = images;
        this.clicker = clicker;
    }

    /*!
  \fn public void tick()

  Metoda menita sa actualizeze butonul UI. Empty momentan.
   */
    @Override
    public void tick() {}
    /*!
    \fn public void render(Graphics g)

    Metoda menita sa deseneze butonul.
    In functie de pozitia mouseului se deseneaza una din doua imagini dispobile pentru buton.
     */
    @Override
    public void render(Graphics g) {

            g.drawImage(images, (int) x, (int) y, width, height, null);

    }

     /*!
    \fn public void Clicked()
    Metoda menita polimorfismului.
    Cand se construieste un buton se suprascrie metoda Clicked a referintei catre ClickListener din fiecare obiect UIImageButton.
     */

    @Override
    public void onClick() {
        clicker.onClick();
    }

}