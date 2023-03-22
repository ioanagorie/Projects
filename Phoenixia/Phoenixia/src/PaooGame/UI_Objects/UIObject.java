package PaooGame.UI_Objects;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
/*!
\class UIObject
\brief Clasa abstracta ce defineste caracteristicile unui obiect pentru UI.

Ea va fi extinsa de UIImageButton.
 */
public abstract class UIObject {
    /// Coordonata x a obiectului UI.
    /// Coordonata y a obiectului UI.
    protected float x, y;

    // Latimea obiectului UI
    protected int width, height;

    /// Dreptunghiul de coliziune pentru obiectul UI.
    protected Rectangle bounds;
    protected boolean hovering = false;

    /*!
\fn UIObject( float x, float y, int width, int height)
\brief Constructor ce seteaza coordonatele obiectului UI si construieste si dreptunghiul de coliziune

 */

    public UIObject(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle((int) x, (int) y, width, height);
    }

    /*!
       \fn public abstract void tick()
       Metoda abstracta menita sa fie suprascrisa in clasele concrete. Se va ocupa cu actualizarea obiectului la input.
        */
    public abstract void tick();
    /*!
\fn public abstract void render()
Metoda abstracta menita sa fie suprascrisa in clasele concrete. Se va ocupa cu desenarea obiectului.
 */
    public abstract void render(Graphics g);
    /*!
  \fn public abstract void Clicked()
  Metoda abstracta menita sa fie suprascrisa in clasele concrete. Se va ocupa cu accesarea metodei Clicked() din subclase (ele vor avea o referinta catre ClickListener )
   */
    public abstract void onClick();
    /*!
\fn public void onMouseMove()
Metoda ce e apelata la orice eveniment de miscare al mouseului.
Ea decide daca mouseul este deasupra obiectului UI sau nu.
 */
    public void onMouseMove(MouseEvent e){
        if(bounds.contains(e.getX(), e.getY()))
            hovering = true;
        else
            hovering = false;
    }

    /*!
\fn public void onMouseRelease()
Metoda ce e apelata la orice eveniment de terminare al clickului al mouseului.
Ea apeleaza metoda Clicked() suprascrisa in fiecare subclasa, oferind raspuns versatil fiecarui obiect UI implementat.
*/
    public void onMouseRelease(MouseEvent e){
        if(hovering)
            onClick();
    }

    // Getters and setters

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isHovering() {
        return hovering;
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }

}