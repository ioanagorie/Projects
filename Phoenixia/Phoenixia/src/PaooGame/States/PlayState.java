package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.ImageLoader;
import PaooGame.Maps.Map;
import PaooGame.Objects.*;
import PaooGame.Handler;
import PaooGame.UI_Objects.UIImageButton;
import PaooGame.UI_Objects.UIManager;
import PaooGame.Database.GlobalInit;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*! \class PlayState
    \brief Implementeaza/controleaza nivelul 1 al jocului.
 */
public class PlayState extends State
{
    private Player player;                                       /*!< Referinta catre player, caracterul controlar de utilizator.*/
    private final ArrayList<Bullet> guns = new ArrayList<>();    /*!< Referinta catre mai multe obiecte de tip arme, din care se lanseaza flacarile.*/
    private final ArrayList<Money> premii = new ArrayList<>();   /*!< Referinta catre mai multe obiecte de tip banuti.*/
    private final Enemy enemy = new Enemy(handler,800,30); /*!< Referinta catre adversar.*/
    private final BufferedImage fundal;                          /*!< Referinta catre imagine de tip BufferedImage.*/
    private final UIManager uiManager;
    private final Map map;
    // private final Bullet flame1;                                 /*!< Referinta catre glonț.*/
    //private final Money money1;                                  /*!< Referinta catre obiect de tip banut.*/

    int pasareLovita = GlobalInit.lives;                                        /*!< Contor care tine evidenta numarului de ori in care pasarea a fost lovita.*/
    int contorGun = GlobalInit.flames;                                          /*!< Contor care tine evidenta numarului de "gloante" ramase inamicului.*/
    private int contorPremiiCastigate=GlobalInit.coins;                         /*!< Contor care tine evidenta numarului de banuti colectati pe parcursul primului nivel.*/

    /*! \fn public PlayState(Handler handler)
        \brief Constructorul de initializare al clasei

        \param handler o referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public PlayState(Handler handler)
    {
        ///Apel al constructorului clasei de baza
        super(handler);
        map = new Map(handler);
        handler.SetMap(map);
        ///Incarca imaginea de fundal a nivelului
        fundal = ImageLoader.LoadImage("/Backgrounds/img2.png");
        ///Construieste jucatorul/playerul si ii seteaza pozitia pe harta
        player = new Player(handler,0, 200);
        /////Initializarea managerului
        uiManager=new UIManager(handler);
        /////Referinta catre manager
        handler.GetMouseManager().setUiManager(uiManager);

        ///Adaugare buton care il trimite pe utilizator inapoi catre meniu
        uiManager.addObject(new UIImageButton(840, 12, 100, 50, Assets.buttonExit, () -> {
            handler.GetMouseManager().setUiManager(null);
            GlobalInit.coins = contorPremiiCastigate;
            GlobalInit.flames = contorGun;
            GlobalInit.lives = pasareLovita;
            State.SetState(handler.GetGame().getMenuState());

        }));
    }

    /*! \fn public void Update()
      \brief Actualizeaza starea curenta a jocului.
   */
    @Override
    public void Update()
    {
        if (GlobalInit.isLoaded)
        {
            pasareLovita = GlobalInit.lives;
            contorGun = GlobalInit.flames;
            contorPremiiCastigate = GlobalInit.coins;
            GlobalInit.isLoaded = false;
        }
        if (GlobalInit.startNewGame){
            GlobalInit.initGlobals();
            pasareLovita = 0;                                        /*!< Contor care tine evidenta numarului de ori in care pasarea a fost lovita.*/
            contorGun = 30;                                          /*!< Contor care tine evidenta numarului de "gloante" ramase inamicului.*/
            contorPremiiCastigate=0;
            GlobalInit.startNewGame = false;
        }
        if (pasareLovita == 0){
            for(Life heart : player.getHearts()) {
                heart.setHit(false);
                heart.Update();
            }
        }
        if (pasareLovita < 3) {
            for(Life heart: player.getHearts())
                heart.Update(); //actualizam vietile playerului
            player.Update(); //actualizam starea playerului
            for (Bullet gun: guns) {
                if (player.getRectangle().intersects(gun.getRectangle())) {
                    for (Life heart : player.getHearts()){
                        if (!heart.getHit()){
                            heart.setHit(true);    //daca jucatorul este atins de glont, setam un flag
                            break;                       // care va indica ca inima respectiva trebuie crapata (o viata se pierde)
                        }
                    }
                    System.out.println("lovit");
                    pasareLovita++;              //incrementam contorul care tine evidenta
                                                 //numarului de ori in care jucatorul a fost lovit
                    gun.setRemoveFlag();         //setam flagul care va indica faptul ca flacara care l-a atins
                                                 //pe jucator trebuie sa dispara (sa nu mai fie desenata pe ecran)
                }
            }
            for (Money premiu: premii) {
                if (player.getRectangle().intersects(premiu.getRectangle())) { //in caz ca player-ul a intrat in contact cu un banut
                    contorPremiiCastigate++;     //se incrementeaza contorul care se ocupa cu numararea banutilor stransi de player in nivelul 1
                    handler.setContorCurent(contorPremiiCastigate); //setam contorul de banuti colectati eferent nivelului 1
                    handler.setContorTotal(contorPremiiCastigate);  //fiind la nivelul 1, contorul total de banuti pentru cele
                                                                    //doua nivele va avea valoarea contorului de banuti din nivelul 1
                    System.out.println("bănuț");
                    premiu.setRemoveFlag();                         //setam flagul care va indica faptul ca banutul care a fost atins
                                                                    //de jucator trebuie sa dispara (sa nu mai fie desenat pe ecran)
                }
            }
            if (pasareLovita >= 3){
                System.out.println("Pasare omorata. Ati pierdut!");
                pasareLovita = 0;                                        /*!< Contor care tine evidenta numarului de ori in care pasarea a fost lovita.*/
                contorGun = 30;                                          /*!< Contor care tine evidenta numarului de "gloante" ramase inamicului.*/
                contorPremiiCastigate=0;                         /*!< Contor care tine evidenta numarului de banuti colectati pe parcursul primului nivel.*/
                for (Life heart : player.getHearts()) {
                    heart.setHit(false);
                    heart.Update();
                }
                GlobalInit.coins = 0;
                GlobalInit.flames = 30;
                GlobalInit.lives = 0;
                GlobalInit.level = 0;

                //player.destroy();       //daca s-au ierosit cele 3 vieti, obiectul player va fi distrus
                //player = null;
                State.SetState(handler.GetGame().getGameOverState()); //se va trece in starea de GameOver
            }
        }

        for (Bullet gun: guns) {
            gun.Update();      // actualizarea gloantelor (flacarilor)
        }
        for (Money premiu: premii) {
            premiu.Update();  //actualizarea banutilor
        }
        enemy.Update();       //actualizarea inamicului


        guns.removeIf(Bullet::getRemoveFlag);   //apel care realizeaza stergere floantelor daca flagul corespunzator a fost setat anterior
        premii.removeIf(Money::getRemoveFlag);  //apel care realizeaza stergere banutilor daca flagul corespunzator a fost setat anterior

        handler.GetMouseManager().setUiManager(uiManager);
        uiManager.Update();                     //actualizarea managerului


    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a jocului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {

        g.drawImage(fundal,0,0,null);   //desenarea fundalului specific nivelului 1
        if (pasareLovita == 0){

            for(Life heart : player.getHearts()) {
                heart.setHit(false);
                heart.Draw(g);
            }
        }
        if (pasareLovita < 3) {    //daca playerul nu si-a pierdut inca toate vietile
            player.Draw(g);        // imaginea playerului apare pe ecran(este redesenata la fiecare update)
            for(Life heart: player.getHearts())
                heart.Draw(g);     //pentru fiecare inima din vectorul de trei, se deseneaza inima in starea
                                   //ei curenta : intreaga sau crapata
            player.setGraphics(g);
        }

        for (Bullet gun: guns) gun.Draw(g); //se deseneaza gloantele pe ecran

        ///in partea de dreapta sus a ecranului se afiseaza simbolul flacarilor
        ///care intruchipeaza "gloantele" insotite de numarul curent
        ///de munitie ramas inamicului. In momentul in care numarul flacarilor
        ///ajunge la 0 si playerul inca nu si-a pierdut toate vietile
        ///inseamna ca nivelul 1 a fost dus la capat cu succes
        g.drawImage(Assets.flame, 742,19,45,45, null);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", 1, 30));
        g.drawString("" + contorGun,795, 49);

        for (Money premiu: premii) premiu.Draw(g);

        ///in partea stanga a ecranului
        g.drawImage(Assets.money,170,19,35,35, null);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", 1, 30));
        g.drawString("" + contorPremiiCastigate,210, 49);

        enemy.Draw(g);
        uiManager.render(g);


    }

    /*public static int getContorPremiiCastigate()
    {
        return contorPremiiCastigate;
    }
     */

    /*! \ fn public void addGun(Bullet gun)
        \brief Creeaza un nou obiect de tipul gun si decrementeaza
          contorul care se ocupa cu memorarea numarului de gloante ramase
          inamicului. Daca s-au terminat gloantele si jucatorul inca mai are
          vieti ramase, aceasta functie il transfera pe jucator in "pagina" in
          care il informeaza ca a finalizat nivelul cu succes si ii aduce la cunostinta
          cati bani a strans.

        \param gun referinta catre un obiect de tip glont.
     */
    public void addGun(Bullet gun) {
        this.guns.add(gun);
        contorGun--;

        if(contorGun < 0) {
            System.out.println("Ati castigat!");
            GlobalInit.coins = contorPremiiCastigate;
            GlobalInit.flames = 1_0; //flames pentru level2
            GlobalInit.lives = 0;
            GlobalInit.level = 1;
            State.SetState(handler.GetGame().getWinState());
        }
        //daca a fost lansata cea de-a 30-a flacara si pasarea inca mai are
        //vieti, inseamna ca jucatorul a castigat acest nivel
    }
    /*! \ fn public void addGun(Bullet gun)
        \brief Creeaza un nou obiect de tipul banut.

        \param premiu referinta catre un obiect de tip Money.
     */
    public void addMoney(Money premiu) {
        this.premii.add(premiu);

    }





}