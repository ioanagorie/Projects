package PaooGame.Objects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import PaooGame.Handler;
import PaooGame.Graphics.Assets;

/*! \class Player
    \brief Implementeaza notiunea de player/jucator (personajul controlat de jucator).

    Elementele suplimentare pe care le aduce fata de clasa de baza sunt:
        imaginea (acest atribut poate fi ridicat si in clasa de baza)
        grafica
        vietile
        dreptunghiul de coliziune
 */
public class Player extends Personaj
{
    private BufferedImage image;    /*!< Referinta catre imaginea curenta a eroului.*/
    private Graphics g;
    private Life heart;
    private ArrayList<Life> hearts = new ArrayList<>();

    private boolean FlameCollision = true;

    /*! \fn public Player(Handler handler, int x, int y)
        \brief Constructorul de initializare al clasei Player.

        \param hanlder Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a eroului.
        \param y Pozitia initiala pe axa Y a eroului.
     */
    public Player(Handler handler, int x, int y)
    {
        ///Apel al constructorului clasei de baza
        super(handler, x,y, Personaj.PersonajWidthImplicit, Personaj.PersonajHeightImplicit);
        ///Seteaza imaginea de start a eroului
        // image = Assets.pasare;
        Image image= Toolkit.getDefaultToolkit().getImage("res/others/personaj.gif");

        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea implicita(normala)
        normalBounds.x = 0;
        normalBounds.y = 0;
        normalBounds.width = 130;
        normalBounds.height = 130;

       ///Instantiaza cele trei inimi care vor aparea pe ecran permanent, dand de stire
        ///utilizatorului cate vieti i-au mai ramas in nivelul curent
        hearts.add(new Life(handler, 10, 10, 40, 40));
        hearts.add(new Life(handler, 60, 10, 40, 40));
        hearts.add(new Life(handler, 110, 10, 40, 40));


    }
    public Image getPlayerImg() {
        Image img = Toolkit.getDefaultToolkit().getImage("res/others/personaj.gif");
        return img;
    }

    public Life getHeart() {
        return heart;
    }

    public ArrayList<Life> getHearts() {
        return hearts;
    }



    public void setHeart1(Life heart1) {

        this.heart = heart;
    }


    public void disableEnemyCollision() {
        FlameCollision = false;
    }

    /*! \fn public void Move()
       \brief Implementeaza metoda Move din clasa de baza Personaj
    */
    public void Move() {
        MoveX();
        MoveY();

    }

    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea eroului.
     */
    @Override
    public void Update()
    {
            ///Verifica daca a fost apasata o tasta
        GetInput();
            ///Actualizeaza pozitia
        Move();

        if (handler.GetKeyManager().up)  {
            image = Assets.pasare;
        }
        if (handler.GetKeyManager().down) {
            image = Assets.pasare;
        }


    }
    /*! \fn public void setGraphics(Graphics g)
        \brief Seteaza referinta catre grafica curenta.

        \param g  Referinta catre grafica cuenta.
     */
    public void setGraphics(Graphics g) {
        this.g = g;
    }

    /*! \fn private void GetInput()
        \brief Verifica daca a fost apasata o tasta din cele stabilite pentru controlul eroului.
     */
    private void GetInput()
    {
            ///Implicit eroul nu trebuie sa se deplaseze daca nu este apasata o tasta
        //xMove = 0;
        yMove = 0;
            ///Verificare apasare tasta "sus"

        if(handler.GetKeyManager().up && this.y >= 20)
        {
            System.out.println(yMove);
            yMove = -speed;
        }
            ///Verificare apasare tasta "jos"
        if(handler.GetKeyManager().down && this.y <= 420)
        {
            System.out.println(yMove);
            yMove = speed;
        }

    }
    /*! \fn public void destroy()
       \brief Randeaza/deseneaza eroul in noua pozitie.

    */
    public void destroy()
    {
        image = Assets.destroyed;
    }

    /*! \fn public void Draw(Graphics g)
        \brief Randeaza/deseneaza eroul in noua pozitie.

        \param g Contextul grafic in care trebuie efectuata desenarea eroului.
     */
    @Override
    public void Draw(Graphics g)
    {
        g.drawImage(getPlayerImg(), (int)x, (int)y, 170, 170, null);

        for (Life heart: hearts) g.drawImage(heart.getImage(), (int)heart.x, (int)heart.y, heart.width, heart.height, null);
    }


}
