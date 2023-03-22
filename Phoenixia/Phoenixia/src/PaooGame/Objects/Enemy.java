package PaooGame.Objects;

import PaooGame.Graphics.Assets;
import PaooGame.Directions.Current_Direction;
import PaooGame.Handler;
import PaooGame.States.PlayState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/*!\class Enemy
   \brief Clasa Enemy implementeaza notiunea de inamic al player-ului
 */
public class Enemy extends Personaj {
    private BufferedImage image;       /*!< Referinta catre imaginea curenta a inamicului.*/
    private Random random = new Random();
    protected Current_Direction directie;
    private int limitLow = 20; //limita de sus
    private  int limitHigh = 460; //limita de jos
    private int gunTimer = 65;
    private int moneyTimer = 335;
    private boolean isUpLimit = false;  //flag pt limita de sus
    private boolean isDownLimit = false; //flag pt limita de jos

  /*! \fn public Enemy(Handler handler, int x, int y)
        \brief Constructorul de initializare al clasei Enemey.

        \param hanlder Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a inamicului.
        \param y Pozitia initiala pe axa Y a inamicului.
     */

    public Enemy(Handler handler, int x, int y) {
        ///Apel al constructorului clasei de baza
        super(handler, x, y, Personaj.PersonajWidthImplicit, Personaj.PersonajHeightImplicit);
        ///Seteaza imaginea de start a inamicului
        //image = Assets.elicopter;
        Image image= Toolkit.getDefaultToolkit().getImage("res/others/plane.gif");
        ///Seteaza directia inamicului
        directie = Current_Direction.right;
        if (y > limitLow && y < limitHigh){
            isDownLimit = false;
            isUpLimit = true;
        }
        else if (y < limitLow){
            isDownLimit = true;
            isUpLimit = false;
        }
        else if (y > limitHigh){
            isDownLimit = false;
            isUpLimit = true;
        }

    }
    public Image getEnemyImg() {
        Image img = Toolkit.getDefaultToolkit().getImage("res/others/plane.gif");
        return img;
    }
    /*! \fn     public void Update() {
      Se incearca depistarea unor items din imprejurimi si daca se gasesc, in functie de cat timp a trecut de la ultimul atac ataca sau nu.
      Daca nu gaseste itemuri de distrus in jur, se plimba.
     */
    @Override
    public void Update() {

        yMove = 6;
        if (isUpLimit && !(isDownLimit)){
            //System.out.println("Cobor");
            if (y + yMove >= limitHigh){
                isDownLimit = true;
                isUpLimit = false;
            }
            y += yMove;
        }
        else if (!(isUpLimit) && isDownLimit){
            //System.out.println("Urc");
            if (y - yMove <= limitLow){
                isDownLimit = false;
                isUpLimit = true;
            }
            y -= yMove;
        }

        if (gunTimer <= 0) {
            gunTimer = 65;
            Bullet newGun = new Bullet(handler, x , y-30 , 50, 60); //pozitionarea armei pe harta
            ((PlayState) handler.GetGame().getPlayState()).addGun(newGun);

        }
        gunTimer--;

        if(moneyTimer <=0 )
        {
            moneyTimer = 335;
            Money newMoney= new Money(handler, x , y-30 , 40, 40); //pozitionarea armei pe harta
            ((PlayState) handler.GetGame().getPlayState()).addMoney(newMoney);
        }
        moneyTimer --;
    }
    /*! \fn public void Draw(Graphics g)
           \brief Randeaza/deseneaza inamicul in noua pozitie.

           \param g Contextul grafic in care trebuie efectuata desenarea inamicului.
        */
    @Override
    public void Draw(Graphics g) {

        g.drawImage(getEnemyImg(), (int) x, (int) y, 150, 150, null);

    }
}






