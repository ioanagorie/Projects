package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.ImageLoader;
import PaooGame.Handler;
import PaooGame.UI_Objects.UIImageButton;
import PaooGame.UI_Objects.UIManager;
import PaooGame.Database.Commands;
import PaooGame.Database.GlobalInit;
import PaooGame.Database.User;

import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class MenuState
    \brief Implementeaza notiunea de meniu al jocului.
 */
public class MenuState extends State
{
    private UIManager uiManager;
    private BufferedImage menu;
    private BufferedImage about;
    private BufferedImage win;
    private BufferedImage loose;

      /*! \ fn public MenuState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param handler referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */

    public MenuState(Handler handler)
    {
        ///Apel al constructorului clasei de baza.
        super(handler);
        ///Initializarea backgroundului meniului
        menu=ImageLoader.LoadImage("/Backgrounds/img.png");
        win=ImageLoader.LoadImage("/Backgrounds/win.png");
        loose=ImageLoader.LoadImage("/Backgrounds/loose.png");
        about =ImageLoader.LoadImage("/Backgrounds/about.png");


        /////Initializarea managerului
        uiManager=new UIManager(handler);
        handler.GetMouseManager().setUiManager(uiManager);

        /////Adaugarea diverselor butoane specifice starii MenuState managerului de obiecte UI

        ///Adaugare buton trimitere catre starea nivelului curent
        ///la care se afla jucatorul
        uiManager.addObject(new UIImageButton(210, 120, 160, 80, Assets.buttonNewGame, () -> {
            GlobalInit.initGlobals();
            GlobalInit.startNewGame = true;
            Handler.contorGames = GlobalInit.level;
            handler.GetMouseManager().setUiManager(null);
            System.out.println(Handler.contorGames);
            State.SetState(handler.GetGame().getPlayState());
        }));

        ///Adaugare buton trimitere catre starea about
        ///in care se regasesc informatii despre joc:
        ///controlare, povestea jocului si regulile
        uiManager.addObject(new UIImageButton(210, 200, 160, 80, Assets.buttonSave, () -> {
            System.out.println("save");
            Commands commands = new Commands();
            User user = new User("nume_random", GlobalInit.coins, GlobalInit.level, GlobalInit.flames, GlobalInit.lives);
            System.out.println(user);
            commands.inserDatabase(user);
            handler.GetMouseManager().setUiManager(null);
            //AM STERS ABOUT SI AM PUS FUNCTIA DE SALVARE
            //State.SetState(handler.GetGame().getAboutState()); ----------------------

        }));

        uiManager.addObject(new UIImageButton(210, 280, 160, 80, Assets.buttonLoad, () -> {
//            Commands commands = new Commands();
//            User userCopy = commands.lastUser();
//            GlobalInit.coins = userCopy.getScore();
//            GlobalInit.flames = userCopy.getNbObst();
//            GlobalInit.lives = userCopy.getNbLives();
//            GlobalInit.level = userCopy.getLevel();
//            System.out.println(userCopy);
//            Handler.setContorGames(userCopy.getLevel());
//            System.out.println("reload");
//            handler.GetMouseManager().setUiManager(null);
//            if (Handler.contorGames <= 0)
//                State.SetState(handler.GetGame().getPlayState());
//            else State.SetState(handler.GetGame().getPlayState2());
            handler.GetMouseManager().setUiManager(null);
            State.SetState(handler.GetGame().getLoadState());
        }));

        uiManager.addObject(new UIImageButton(210, 360, 160, 80, Assets.buttonAbout, () -> {
            handler.GetMouseManager().setUiManager(null);
            State.SetState(handler.GetGame().getAboutState());
        }));


        ///Adaugare buton care permite utilizatorului sa
        ///paraseasca jocul

        //------------------------------------
        uiManager.addObject(new UIImageButton(210, 440, 160, 80, Assets.buttonQuit, () -> System.exit(0)));


    }

    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniului.
     */
    @Override
    public void Update()
    {
       // if (handler.GetMouseManager().getUiManager() == null)
            handler.GetMouseManager().setUiManager(uiManager);

        uiManager.Update();

    }
    public Image getPlayerImg() {
        Image img = Toolkit.getDefaultToolkit().getImage("res/others/personaj.gif");
        return img;
    }

    /*! \fn public void Draw(Graphics g)
       \brief Deseneaza (randeaza) pe ecran starea curenta a meniului.

       \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
    */
    @Override
    public void Draw(Graphics g)
    {
        g.drawImage(menu,0,0,null);
        g.drawImage(getPlayerImg(), 550, 180, 300, 300, null);
        uiManager.render(g);

    }
}
