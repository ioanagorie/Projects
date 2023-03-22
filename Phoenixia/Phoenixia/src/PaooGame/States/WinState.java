package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.ImageLoader;
import PaooGame.Handler;
import PaooGame.UI_Objects.UIImageButton;
import PaooGame.UI_Objects.UIManager;


import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class WinState
    \brief Implementeaza notiunea de finalizare cu succes a misiunii nivelului 1 si
    reprezinta totodata o stare de tranzitie intre cele doua niveluri ale jocului.

      Reprezinta starea care informeaza utilizatorul ca a reusit
      sa reziste atacului inamicului in nivelul 1, ii aduce la cunostinta
      suma de banuti colectata pe parcursul primei etape ale jocului si ii
      ii deschide calea spre urmatorul nivel prin apasarea butonului PLAY regasit
      in subsolul ecranului.
 */
public class WinState extends State
{
    private final UIManager uiManager;
    private final BufferedImage win;

    /*! \fn public WinState(Handler handler)
       \brief Constructorul de initializare a clasei.

       \param handler referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
    */
    public WinState(Handler handler)
    {
        ///Apel al constructorului clasei de baza.
        super(handler);
        ///Initializarea backgroundului starii de WinState.
        win=ImageLoader.LoadImage("/Backgrounds/win1.png");
        ///Se construieste un nou manager de obiecte UI
        uiManager=new UIManager(handler);
        handler.GetMouseManager().setUiManager(uiManager);

        ///Adaugare buton care il trimite pe utilizator catre urmatorul nivel al jocului (in PlayState2).
        uiManager.addObject(new UIImageButton(360, 350, 160, 80, Assets.buttonPlay, () -> {
            Handler.contorGames++;
            handler.GetMouseManager().setUiManager(null);
            State.SetState(handler.GetGame().getPlayState2());
        }));

        ///Adaugare buton care il trimite pe utilizator inapoi catre meniu
        uiManager.addObject(new UIImageButton(840, 12, 100, 50, Assets.buttonExit, () -> {
            handler.GetMouseManager().setUiManager(null);
            State.SetState(handler.GetGame().getMenuState());
        }));


    }

    /*! \fn public void Update()
       \brief Actualizeaza starea curenta a starii WinState.
    */
    @Override
    public void Update()
    {
        handler.GetMouseManager().setUiManager(uiManager);
        uiManager.Update();
    }

    /*! \fn public void Draw(Graphics g)
           \brief Deseneaza (randeaza) pe ecran starea curenta a starii WinState.

           \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
        */
    @Override
    public void Draw(Graphics g)
    {
        g.drawImage(win,0,0,null);

        //g.drawImage(Assets.inimacrapata,  100, 20, 150, 150, null);

        uiManager.render(g);
    }
}
