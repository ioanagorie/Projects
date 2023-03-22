package PaooGame.Objects;

import PaooGame.Handler;
import PaooGame.Tiles.Tile;

import java.awt.*;

/*! \class  Personaj
    \brief Defineste notiunea abstracta de personaj/caracter din joc.
           Obstacolele din joc sunt considerate a fi Personaje, întrucât ele reprezintă
           o abstractizare a notiunii de inamic, deoarece coliziunea lor cu player-ul
           are un impact asupra acestuia.

    Notiunea este definita de viata, viteza de deplasare si de inaltimea si larimea imaginii care il defineste.
 */
public abstract class Personaj extends Item {

    public static final int LifeImplicit = 3;      /*!< Valoarea implicita a vietii unui personaj.*/
    public static final float SpeedImplicit = 6f;   /*!< Viteza implicita a unui personaj.*/
    public static final int PersonajWidthImplicit = 120;  /*!< Latimea implicita a imaginii caracterului.*/
    public static final int PersonajHeightImplicit = 120; /*!< Inaltimea implicita a imaginii caracterului.*/


    protected int life;

    protected float speed;            /*!< Retine viteza de deplasare caracterului.*/
    protected float yMove;            /*!< Retine noua pozitie a caracterului pe axa Y.*/
    protected float xMove;            /*!< Retine noua pozitie a caracterului pe axa X.*/
    protected Animation animation;
    protected float collisionWidth = 60f;  /*!< Latimea implicita a coleziunii.*/
    protected float collisionHeight = 60f; /*!< Inaltimea implicita a coleziunii.*/
    protected boolean remove = false;

    /*! \fn public Personaj(Handler refLink, float x, float y, int width, int height)
            \brief Constructor de initializare al clasei Character

            \param handler Referinta catre obiectul shortcut (care retine alte referinte utile/necesare in joc).
            \param x Pozitia de start pa axa X a caracterului.
            \param y Pozitia de start pa axa Y a caracterului.
            \param width Latimea imaginii caracterului.
            \param height Inaltimea imaginii caracterului.
         */
    public Personaj(Handler refLink, float x, float y, int width, int height) {
        ///Apel constructor la clasei de baza
        super(refLink, x, y, width, height);
        //Seteaza valorile implicite pentru viata, viteza si distantele de deplasare
        life = LifeImplicit;
        speed = SpeedImplicit;
        xMove = 0;
        yMove = 0;
    }

    /*! \fn public void Move()
       \brief Modifica pozitia caracterului
    */
    public void Move() {

    }
    /*! \fn public void MoveX()
       \brief Modifica pozitia caracterului pe axa X.
    */
    public void MoveX()
    {
        if(xMove > 0) {
            int tx = (int) (x + xMove + normalBounds.x + normalBounds.width) / Tile.TILE_WIDTH;

            x += xMove;

        }
        else if(xMove < 0){
               x += xMove;

        }
        handler.GetMap().setXplayer((int) x);

    }
    /*! \fn public void MoveY()
       \brief Modifica pozitia caracterului pe axa Y.
    */
    public void MoveY()
    {
        if(yMove < 0){
            int ty = (int) (y+ yMove + normalBounds.y) / Tile.TILE_HEIGHT;
            y += yMove;
        }else if(yMove > 0){//down
            int ty = (int) (y+ yMove +normalBounds.y + normalBounds.getHeight()) /Tile.TILE_HEIGHT;
             y += yMove;
        }
        handler.GetMap().setYplayer((int) y);
    }

    /*! \fn public int GetLife()
       \brief Returneaza viata caracterului.
    */
    public int GetLife() {

        return life;
    }
    /*! \fn public int GetSpeed()
     \brief Returneaza viteza caracterului.
  */
    public float GetSpeed() {
        return speed;
    }
    /*! \fn public void SetLife(int life)
        \brief Seteaza viata caracterului.
     */
    public void SetLife(int life) {
        this.life = life;
    }
    /*! \fn public void SetSpeed(float speed)
       \brief Seteaza viteza caracterului.
    */
    public void SetSpeed(float speed) {

        this.speed = speed;
    }
    /*! \fn public float GetXMove()
       \brief Returneaza distanta in pixeli pe axa X cu care este actualizata pozitia caracterului.
    */
    public float GetXMove() {
        return xMove;
    }
    /*! \fn public float GetYMove()
        \brief Returneaza distanta in pixeli pe axa Y cu care este actualizata pozitia caracterului.
     */
    public float GetYMove()
    {
        return yMove;
    }
     /*! \fn public void SetXMove(float xMove)
        \brief Seteaza distanta in pixeli pe axa X cu care va fi actualizata pozitia caracterului.
     */

    public void SetXMove(float xMove)
    {
        this.xMove = xMove;
    }

    /*! \fn public void SetYMove(float yMove)
        \brief Seteaza distanta in pixeli pe axa Y cu care va fi actualizata pozitia caracterului.
     */
    public void SetYMove(float yMove)
    {
        this.yMove = yMove;
    }

    public Rectangle getRectangle() {
        return new Rectangle((int) x, (int) y, (int) collisionWidth, (int) collisionHeight);
    }

    public boolean getRemoveFlag() {
        return remove;
    }

    public void setRemoveFlag() {
        remove = true;
    }

}

