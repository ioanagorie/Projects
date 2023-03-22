package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.ImageLoader;
import PaooGame.Objects.*;
import PaooGame.Handler;
import PaooGame.Maps.Map;
import PaooGame.UI_Objects.UIImageButton;
import PaooGame.UI_Objects.UIManager;
import PaooGame.Database.GlobalInit;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*! \class PlayState2
    \brief Implementeaza/controleaza nivelul 2 al jocului.
 */
public class PlayState2 extends State
{
    private Player player;
    private final ArrayList<Flower> flori = new ArrayList<>(); /*!< Referinta catre mai multe obiecte de tip flori.*/
    private final ArrayList<Tree> copaci = new ArrayList<>();  /*!< Referinta catre mai multe obiecte de tip copaci.*/
    private final ArrayList<Land> pamanturi = new ArrayList<>(); /*!< Referinta catre mai multe obiecte de tip pamanturi.*/
    private final ArrayList<Pine> brazi = new ArrayList<>();     /*!< Referinta catre mai multe obiecte de tip brazi.*/
    private final ArrayList<Money> premii = new ArrayList<>();   /*!< Referinta catre mai multe obiecte de tip banuti.*/
    private final Enemy2 enemy = new Enemy2(handler,800,30); /*!< Referinta catre adversar.*/
    private final BufferedImage fundal;                           /*!< Referinta catre imagine de tip BufferedImage.*/
    private final UIManager uiManager;                            /*!< Referinta catre manager.*/
    private final Map map;                                         /*!< Referinta catre harta.*/
    private static final int contor=0;                            /*!< Contor care tine evidenta numarului de ori in care pasarea a fost lovita.*/
    int pasareLovita = GlobalInit.lives;
    int contorGun = GlobalInit.flames;
    int contorPremiiCastigate = 0;
    Boolean firstupdate=true;

    /*! \fn public PlayState2(Handler handler)
          \brief Constructorul de initializare al clasei

          \param handler o referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
       */
    public PlayState2(Handler handler)
    {
        ///Apel al constructorului clasei de baza
        super(handler);
        map = new Map(handler);
        handler.SetMap(map);
        ///Incarca imaginea de fundal a nivelului
        fundal = ImageLoader.LoadImage("/Backgrounds/img3.png");
        ///Construieste jucatorul/playerul si ii seteaza pozitia pe harta
        player = new Player(handler,0, 200); ///pozitia eroului pe harta
        /////Initializarea managerului
        uiManager=new UIManager(handler);
        handler.GetMouseManager().setUiManager(uiManager);

        ///Adaugare buton care il trimite pe utilizator inapoi catre meniu
        uiManager.addObject(new UIImageButton(840, 12, 100, 50, Assets.buttonExit, () -> {
            GlobalInit.coins = GlobalInit.coins + contorPremiiCastigate;
            GlobalInit.flames = contorGun;//cate flame vrei tu
            GlobalInit.lives = pasareLovita;
            handler.GetMouseManager().setUiManager(null);
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
            for(int i = 0; i < pasareLovita; ++i)
                player.getHearts().get(i).setHit(true);
        }
        if (GlobalInit.startNewGame){
            GlobalInit.initGlobals();
            pasareLovita = 0;                                        /*!< Contor care tine evidenta numarului de ori in care pasarea a fost lovita.*/
            contorGun = 1_0;                                          /*!< Contor care tine evidenta numarului de "gloante" ramase inamicului.*/
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
            boolean haveDamagedHeart = false;

            for (Flower floare: flori) {
                if (player.getRectangle().intersects(floare.getRectangle())) {
                    for (Life heart : player.getHearts()){
                        if (heart.getHit() == false){
                            haveDamagedHeart= true;
                            heart.setHit(true);    //daca jucatorul este atinge un obstacol, setam un flag
                                                  // care va indica ca inima respectiva trebuie crapata (o viata se pierde)
                            break;
                        }
                    }
                    System.out.println("lovit");
                    pasareLovita++; //incrementam contorul care tine evidenta
                                    //numarului de ori in care jucatorul a fost lovit
                                    //setam flagul care va indica faptul ca flacara care l-a atins
                                    //pe jucator trebuie sa dispara (sa nu mai fie desenata pe ecran)
                    floare.setRemoveFlag();
                }
            }
            for (Tree copac: copaci) {
                if (player.getRectangle().intersects(copac.getRectangle())) {
                    for (Life heart : player.getHearts()){
                        if (heart.getHit() == false){
                            haveDamagedHeart= true;
                            heart.setHit(true);
                            break;
                        }
                    }
                    System.out.println("lovit");
                    pasareLovita++;
                    copac.setRemoveFlag();
                }
            }
            for (Land pamant: pamanturi) {
                if (player.getRectangle().intersects(pamant.getRectangle())) {
                    for (Life heart : player.getHearts()){
                        if (heart.getHit() == false){
                            haveDamagedHeart= true;
                            heart.setHit(true);
                            break;
                        }
                    }
                    System.out.println("lovit");
                    pasareLovita++;
                    pamant.setRemoveFlag();
                }
            }
            for (Pine brad: brazi) {
                if (player.getRectangle().intersects(brad.getRectangle())) {
                    for (Life heart : player.getHearts()){
                        if (heart.getHit() == false){
                            haveDamagedHeart= true;
                            heart.setHit(true);
                            break;
                        }
                    }
                    System.out.println("lovit");
                    pasareLovita++;
                    brad.setRemoveFlag();
                }
            }
            for (Money premiu: premii) {
                if (player.getRectangle().intersects(premiu.getRectangle())) {
                    contorPremiiCastigate++;
                    handler.setContorCurent(contorPremiiCastigate);

                    //hero.setRemoveFlag();
                    System.out.println("bănuț");
                    premiu.setRemoveFlag();
                }
            }
            if (pasareLovita >= 3){
                System.out.println("Pasare omorata. Ati pierdut!");
                GlobalInit.level = 0;
                GlobalInit.coins = 0;
                GlobalInit.lives = 0;
                GlobalInit.flames = 2_0;
                pasareLovita = 0;                                        /*!< Contor care tine evidenta numarului de ori in care pasarea a fost lovita.*/
                contorGun = 1_0;                                          /*!< Contor care tine evidenta numarului de "gloante" ramase inamicului.*/
                contorPremiiCastigate=0;                         /*!< Contor care tine evidenta numarului de banuti colectati pe parcursul primului nivel.*/
                for (Life heart : player.getHearts()) {
                    heart.setHit(false);
                    heart.Update();
                }
//                GlobalInit.coins = 0;
//                GlobalInit.flames = 15;
//                GlobalInit.lives = 0;
//                GlobalInit.level = 0;

                //player.destroy();
                //player = null;
                //System.exit(0);
                //jocul se inchide automat daca se pierd cele trei vieti
                State.SetState(handler.GetGame().getGameOverState());
            }
        }



        for (Flower floare: flori) {
            floare.Update();
        }
        for (Tree copac: copaci) {
            copac.Update();
        }
        for (Land pamant: pamanturi){
            pamant.Update();
        }
        for (Pine brad: brazi){
            brad.Update();
        }
        for (Money premiu: premii) {
            premiu.Update();
        }
        enemy.Update();



        flori.removeIf(Flower::getRemoveFlag);
        copaci.removeIf(Tree::getRemoveFlag);
        pamanturi.removeIf(Land::getRemoveFlag);
        brazi.removeIf(Pine::getRemoveFlag);
        premii.removeIf(Money::getRemoveFlag);


        //if (handler.GetMouseManager().getUiManager() == null)
            handler.GetMouseManager().setUiManager(uiManager);

        uiManager.Update();


    }
    /*! \fn public void Draw(Graphics g)
       \brief Deseneaza (randeaza) pe ecran starea curenta a jocului.

       \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
    */
    @Override
    public void Draw(Graphics g)
    {

        g.drawImage(fundal,0,0,null);
        if (pasareLovita == 0){
            System.out.println("Draw");
            for(Life heart : player.getHearts()) {
                System.out.println("heart");

                heart.setHit(false);
                heart.Draw(g);
            }
        }
        if (pasareLovita < 3) {
            player.Draw(g);
            for(Life heart: player.getHearts())
                heart.Draw(g);

            player.setGraphics(g);
        }


        g.drawImage(Assets.flame, 742,19,45,45, null);
        //flacari ramase la elicopter

        for (Money premiu: premii) premiu.Draw(g);
        //money1.Draw(g);
        g.drawImage(Assets.money,170,19,35,35, null);
        //flacari ramase la elicopter

        for (Flower floare: flori) {
            floare.Draw(g);
        }
        for (Tree copac: copaci) copac.Draw(g);
        for (Land pamant: pamanturi) pamant.Draw(g);
        for (Pine brad: brazi)  brad.Draw(g);
        for (Money premiu: premii) premiu.Draw(g);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", 1, 30));
        g.drawString("" + contorGun,795, 49);

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

    public void addFlower(Flower floare) {
        this.flori.add(floare);
        contorGun--;

        if(contorGun < 0) {
            System.out.println("Ati castigat!");
            int var= GlobalInit.coins + contorPremiiCastigate;
            handler.setContorTotal(var);
            State.SetState(handler.GetGame().getWinState2());
        }

    }
    public void addTree(Tree copac){
        this.copaci.add(copac);
        contorGun--;

        if(contorGun < 0) {
            System.out.println("Ati castigat!");
            int var=handler.getContorTotal()+handler.getContorCurent();
            handler.setContorTotal(var);
            State.SetState(handler.GetGame().getWinState2());
        }

    }

    public void addLand(Land pamant){
        this.pamanturi.add(pamant);
        contorGun--;

        if(contorGun < 0) {
            System.out.println("Ati castigat!");
            int var=handler.getContorTotal()+handler.getContorCurent();
            handler.setContorTotal(var);
            State.SetState(handler.GetGame().getWinState2());
        }

    }
    public void addPine(Pine brad){
        this.brazi.add(brad);
        contorGun--;

        if(contorGun < 0) {
            System.out.println("Ati castigat!");
            int var=handler.getContorTotal()+handler.getContorCurent();
            handler.setContorTotal(var);
            State.SetState(handler.GetGame().getWinState2());
        }
    }
    public void addMoney(Money premiu) {
        this.premii.add(premiu);

    }




}