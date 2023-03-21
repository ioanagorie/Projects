package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.ImageLoader;
import PaooGame.Handler;
import PaooGame.States.State;
import PaooGame.UI_Objects.ClickListener;
import PaooGame.UI_Objects.UIImageButton;
import PaooGame.UI_Objects.UIManager;

import java.awt.*;

/*! \class AboutState
    \brief Implementeaza notiunea de manual de instructiuni/despre joc.

    Contine informatii despre controllers, povestea jocului pentru intelegerea
    contextului jocului si informatii despre regulament.
 */
public class AboutState extends State
{
    private UIManager uiManager;
    int contor=1;

    /*! \fn public AboutState(Handler handler)
        \brief Constructorul de initializare al clasei.

        \param handler referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public AboutState(Handler handler)
    {

        ///Apel al constructorului clasei de baza.
        super(handler);
        ///Se construieste un nou manager de obiecte UI
        uiManager = new UIManager(handler);

        /////Adaugarea diverselor butoane ce permit
        /////utilizatorului sa navigheze in starea About
        ///// pentru a putea accesa toate informatiile
        /////care ii sunt puse la indemana de catre
        /////dezvoltatorul aplicatiei

        ///Adaugare buton care il trimite pe utilizator inapoi catre meniu
        uiManager.addObject(new UIImageButton(840, 12, 100, 50, Assets.buttonExit, () -> {
            contor=1;
            handler.GetMouseManager().setUiManager(null);
            State.SetState(handler.GetGame().getMenuState());
        }));

        ///Adaugare buton care ii permite utilizatorului sa acceseze
        ///urmatorul "slide" cu informatii ce ii sunt puse la dispozitie
        ///de catre dezvoltatorul plicatiei
        uiManager.addObject(new UIImageButton(840, 480, 100, 50, Assets.buttonNext, () -> {
            if(contor+1 ==15) contor=1;
            else
                contor++;
            }));

        ///Adaugare buton care ii permite utilizatorului sa acceseze
        ///"slide-ul" anterior cu informatii ce ii sunt puse la dispozitie
        ///de catre dezvoltatorul plicatiei
        uiManager.addObject(new UIImageButton(30, 480, 100, 50, Assets.buttonBack, () -> {
            if(contor-1==0) contor=14;
            else contor--;
            }));



    }
    /*! \fn public Image GetPlayerImg()
       \brief Returneaza imaginea player-ului care da din aripi.

    */
    public Image getPlayerImg() {
        Image img = Toolkit.getDefaultToolkit().getImage("res/others/personaj.gif");
        return img;
    }
    /*! \fn public Image GetWaterfallImg()
       \brief Returneaza imaginea cascadei curgatoare.

    */
    public Image getWaterfallImg() {
        Image img = Toolkit.getDefaultToolkit().getImage("res/others/Cascada.gif");
        return img;
    }

    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniului.
     */
    @Override
    public void Update()
    {
        handler.GetMouseManager().setUiManager(uiManager);
        uiManager.Update();
    }


    /*! \fn public void Draw(Graphics g)
           \brief Deseneaza (randeaza) pe ecran starea curenta a meniului.

           \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
        */
    @Override
    public void Draw(Graphics g)
    {
       if(contor==1)
       {
           g.drawImage(ImageLoader.LoadImage("/Backgrounds/slide1.png"),0,0,null);

       }
       if(contor==2) {
           g.drawImage(ImageLoader.LoadImage("/Backgrounds/slide2.png"),0,0,null);
           g.drawImage(Assets.princess,  500, 135, 150, 150, null);
           g.drawImage(Assets.king,  500, 285, 150, 150, null);
       }
       if(contor==3){
           g.drawImage(ImageLoader.LoadImage("/Backgrounds/slide3.png"),0,0,null);
           g.drawImage(Assets.witch,  500, 200, 150, 150, null);
       }
       if(contor==4)
       {
           g.drawImage(ImageLoader.LoadImage("/Backgrounds/slide4.png"),0,0,null);
           g.drawImage(getPlayerImg(), 500, 220, 170, 170, null);
       }
        if(contor==5)
        {
            g.drawImage(ImageLoader.LoadImage("/Backgrounds/slide5.png"),0,0,null);
            g.drawImage(getWaterfallImg(), 480, 160, 200, 200, null);
        }
        if(contor==6)
        {
            g.drawImage(ImageLoader.LoadImage("/Backgrounds/slide6.png"),0,0,null);
        }
        if(contor==7)
        {
            g.drawImage(ImageLoader.LoadImage("/Backgrounds/slide7.png"),0,0,null);
        }
        if(contor==8)
        {
            g.drawImage(ImageLoader.LoadImage("/Backgrounds/slide8.png"),0,0,null);
        }
        if(contor==9)
        {
            g.drawImage(ImageLoader.LoadImage("/Backgrounds/slide9.png"),0,0,null);
            g.drawImage(Assets.inimafull,  333, 320, 60, 60, null);
            g.drawImage(Assets.inimafull,  433, 320, 60, 60, null);
            g.drawImage(Assets.inimafull,  533, 320, 60, 60, null);
        }
        if(contor==10)
        {
            g.drawImage(ImageLoader.LoadImage("/Backgrounds/slide10.png"),0,0,null);
            g.drawImage(Assets.inimafull,  333, 320, 60, 60, null);
            g.drawImage(Assets.inimafull,  433, 320, 60, 60, null);
            g.drawImage(Assets.inimacrapata,  533, 320, 60, 60, null);
        }
        if(contor==11)
        {
            g.drawImage(ImageLoader.LoadImage("/Backgrounds/slide11.png"),0,0,null);
        }
        if(contor==12)
        {
            g.drawImage(ImageLoader.LoadImage("/Backgrounds/slide12.png"),0,0,null);
        }
        if(contor==13)
        {
            g.drawImage(ImageLoader.LoadImage("/Backgrounds/slide13.png"),0,0,null);
        }
        if(contor==14)
        {
            g.drawImage(ImageLoader.LoadImage("/Backgrounds/slide14.png"),0,0,null);
        }
        uiManager.render(g);
    }
}
