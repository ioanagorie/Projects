package PaooGame.Objects;

import PaooGame.Graphics.Assets;
import PaooGame.Directions.Current_Direction;
import PaooGame.Handler;
import PaooGame.States.PlayState2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/*!\class Enemy2
   \brief Clasa Enemy2 implementeaza notiunea de inamic al jocului
 */
public class Enemy2 extends Personaj {
    private BufferedImage image;
    private Random random = new Random();
    protected Current_Direction directie;
    private int limitLow = 20; //limita de sus
    private  int limitHigh = 400; //limita de jos
    private int floareTimer = 50;
    private int copaciTimer = 150;
    private int pamanturiTimer= 300;
    private int braziTimer= 550;
    private int moneyTimer = 260;
    private boolean isUpLimit = false;  //flag pt limita de sus
    private boolean isDownLimit = false; //flag pt limita de jos
 /*! \fn public Enemy2(Handler handler, int x, int y)
        \brief Constructorul de initializare al clasei Enemy2.

        \param hanlder Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a inamicului.
        \param y Pozitia initiala pe axa Y a inamicului.
     */

    public Enemy2(Handler handler, int x, int y) {
        super(handler, x, y, Personaj.PersonajWidthImplicit, Personaj.PersonajHeightImplicit);
       image = Assets.destroyed;
        //Image image= Toolkit.getDefaultToolkit().getImage("res/others/plane.gif");
        directie = Current_Direction.right;
        speed=6f;
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


        if (floareTimer <= 0) {
            floareTimer = 1000;
            Flower newFlower = new Flower(handler, x+200 , y-30 , 50, 60); //pozitionarea armei pe harta
            ((PlayState2) handler.GetGame().getPlayState2()).addFlower(newFlower);

        }
        floareTimer--;
        if(copaciTimer <=0 )
        {
            copaciTimer = 1000;
            Tree newTree= new Tree(handler, x+200 , y-30 , 400, 400); //pozitionarea armei pe harta
            ((PlayState2) handler.GetGame().getPlayState2()).addTree(newTree);
        }
        copaciTimer --;
        if(pamanturiTimer <=0 )
        {
            pamanturiTimer = 400;
            Land newLand= new Land(handler, x+200, y-30 , 100, 100); //pozitionarea armei pe harta
            ((PlayState2) handler.GetGame().getPlayState2()).addLand(newLand);
        }
        pamanturiTimer --;

        if(braziTimer <=0 )
        {
            braziTimer = 1000;
            Pine newPine= new Pine(handler, x+200 , y-30 , 100, 100); //pozitionarea armei pe harta
            ((PlayState2) handler.GetGame().getPlayState2()).addPine(newPine);
        }
        braziTimer --;
        if(moneyTimer <=0 )
        {
            moneyTimer = 335;
            Money newMoney= new Money(handler, x+200 , y-30 , 100, 100); //pozitionarea armei pe harta
            ((PlayState2) handler.GetGame().getPlayState2()).addMoney(newMoney);
        }
        moneyTimer --;
    }

    /*! \fn public void Draw(Graphics g)
           \brief Randeaza/deseneaza inamicul in noua pozitie.

           \param g Contextul grafic in care trebuie efectuata desenarea inamicului.
        */
    @Override
    public void Draw(Graphics g) {

        g.drawImage(image, (int) x, (int) y, 150, 150, null);

    }
}





