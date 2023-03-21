package PaooGame.Objects;

import PaooGame.Handler;

import java.awt.*;

/*! \class Item
    \brief. Implementeaza notiunea abstracta de entitate activa din joc, element cu care se poate interactiona: personaje, obstacole etc.".
 */
public abstract class Item
{

    protected float x;   /*!< Pozitia pe axa X  de joc a imaginii entitatii.*/
    protected float y;    /*!< Pozitia pe axa Y  de joc a imaginii entitatii.*/
    protected int width;                /*!< Latimea imaginii entitatii.*/
    protected int height;               /*!< Inaltimea imaginii entitatii.*/
    protected Rectangle bounds;         /*!< Dreptunghiul curent de coliziune.*/
    protected Rectangle normalBounds;   /*!< Dreptunghiul de coliziune aferent starii obisnuite(spatiul ocupat de entitate in mod normal), poate fi mai mic sau mai mare decat dimensiunea imaginii sale.*/
    protected Rectangle attackBounds;   /*!< Dreptunghiul de coliziune aferent starii de atac.*/
    protected Handler handler;       /*!< O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.*/
    /*! \fn public Item(Handler handler, float x, float y, int width, int height)
            \brief Constructor de initializare a clasei Item

            \param handler Referinta catre obiectul shortcut (care retine alte referinte utile/necesare in joc).
            \param x Pozitia de start pa axa X a caracterului.
            \param y Pozitia de start pa axa Y a caracterului.
            \param width Latimea imaginii caracterului.
            \param height Inaltimea imaginii caracterului.
         */
    public Item(Handler handler, float x, float y, int width, int height)
    {
        this.x = x;             /*!< Retine coordonata pe axa X.*/
        this.y = y;             /*!< Retine coordonata pe axa X.*/
        this.width = width;     /*!< Retine latimea imaginii.*/
        this.height = height;   /*!< Retine inaltimea imaginii.*/
        this.handler = handler; /*!< Retine the "shortcut".*/

            ///dreptunghi de coliziune pentru modul normal
        normalBounds = new Rectangle(0, 0, width, height);

        ///implicit
        bounds = normalBounds;
    }


    public Rectangle getCollisionBounds(float xOffset, float yOffset){
        return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
    }
    /*! \fn public void Update()
           \brief Actualizeaza pozitia item-ului
        */
    public abstract void Update();
    /*! \fn public void Draw(Graphics g)
       \brief Randeaza/deseneaza item-ul in noua pozitie.

       \param g Contextul grafic in care trebuie efectuata desenarea item-ului.
    */
    public abstract void Draw(Graphics g);


    /*! \fn public float GetX()
        \brief Returneaza coordonata pe axa X.
     */
    public float GetX()
    {
        return x;
    }

    /*! \fn public float GetY()
        \brief Returneaza coordonata pe axa Y.
     */
    public float GetY()
    {
        return y;
    }

    /*! \fn public float GetWidth()
        \brief Returneaza latimea entitatii.
     */
    public int GetWidth()
    {
        return width;
    }

    /*! \fn public float GetHeight()
        \brief Returneaza inaltimea entitatii.
     */
    public int GetHeight()
    {
        return height;
    }

    /*! \fn public float SetX()
        \brief Seteaza coordonata pe axa X.
     */
    public void SetX(float x)
    {
        this.x = x;
    }

    /*! \fn public float SetY()
        \brief Seteaza coordonata pe axa Y.
     */
    public void SetY(float y)
    {
        this.y = y;
    }

    /*! \fn public float SetWidth()
        \brief Seteaza latimea imaginii entitatii.
     */
    public void SetWidth(int width)
    {
        this.width = width;
    }

    /*! \fn public float SetHeight()
        \brief Seteaza inaltimea imaginii entitatii.
     */
    public void SetHeight(int height)
    {
        this.height = height;
    }

    /*! \fn public void SetNormalMode()
        \brief Seteaza modul normal de interactiune
     */
    public void SetNormalMode()
    {
        bounds = normalBounds;
    }



}
