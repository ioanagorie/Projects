package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.ImageLoader;
import PaooGame.Handler;
import PaooGame.UI_Objects.UIImageButton;
import PaooGame.UI_Objects.UIManager;

import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class GameOverState
    \brief Implementeaza notiunea de joc pierdut.

      Reprezinta starea care informeaza utilizatorul ca si-a pierdut
      toate vietile si prin urmare jocul a luat sfarsit.
 */
public class GameOverState extends State
{
    private UIManager uiManager;
    private BufferedImage loose;

    /*! \fn public GameOverState(Handler handler)
       \brief Constructorul de initializare a clasei.

       \param handler referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
    */
    public GameOverState(Handler handler)
    {
        ///Apel al constructorului clasei de baza.
        super(handler);
        ///Se construieste un nou manager de obiecte UI
        uiManager = new UIManager(handler);
        ///Initializarea backgroundului starii de GameOver.
        loose=ImageLoader.LoadImage("/Backgrounds/loose.png");

        ///Adaugare buton care il trimite pe utilizator inapoi catre meniu
        uiManager.addObject(new UIImageButton(840, 12, 100, 50, Assets.buttonExit, () -> {

            handler.GetMouseManager().setUiManager(null);
            State.SetState(handler.GetGame().getMenuState());
        }));

    }

    /*! \fn public void Update()
       \brief Actualizeaza starea curenta a starii GameOver.
    */
    @Override
    public void Update()
    {

        handler.GetMouseManager().setUiManager(uiManager);
        uiManager.Update();


    }

    /*! \fn public void Draw(Graphics g)
           \brief Deseneaza (randeaza) pe ecran starea curenta a starii de GameOver.

           \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
        */
    @Override
    public void Draw(Graphics g)
    {
        g.drawImage(loose,0,0,null);
        uiManager.render(g);
    }
}

