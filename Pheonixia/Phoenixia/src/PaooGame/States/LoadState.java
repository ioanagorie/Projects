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
import java.util.ArrayList;
import java.util.List;

/*! \class LoadState
    \brief Clasa ce se ocupa de starea programului in care sunt afisate progresele in joc
    ale utilizatorilor, reprezentand totodata un portal catre momentul de joc la care au ramas in trecut jucatorii.
 */
public class LoadState extends State
{
    private UIManager uiManager;
    private BufferedImage image;
    private List<User> userList = new ArrayList<>();
    private Commands commands = new Commands();
    private boolean firstSquareIsLoaded;
    private boolean secondSquareIsLoaded;
    private boolean thirdSquareIsLoaded;
    private boolean fourthSquareIsLoaded;
      /*! \ fn public LoadState(Handler handler)
        \brief Constructorul de initializare al clasei.

        \param handler o referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */

    public LoadState(Handler handler)
    {
        ///Apel al constructorului clasei de baza.
        super(handler);
        image=ImageLoader.LoadImage("/Backgrounds/img4.png");

        /////Initializarea managerului
        uiManager=new UIManager(handler);
        handler.GetMouseManager().setUiManager(uiManager);

       ///Buton ce permite intoarcerea utilizatorului la meniul jocului.
        uiManager.addObject(new UIImageButton(840, 12, 100, 50, Assets.buttonExit, () -> {
            handler.GetMouseManager().setUiManager(null);
            State.SetState(handler.GetGame().getMenuState());
        }));

        ///Adaugare butoane ce permit "teleportarea" utilizatorilor
        ///catre momentul din joc la care a ramas ultima data
        ///cand a jucat jocul.

        uiManager.addObject(new UIImageButton(180, 100, 200, 100, null, () -> {
            System.out.println("First Square");
            if (firstSquareIsLoaded){
                GlobalInit.isLoaded = true;
                GlobalInit.level = userList.get(0).getLevel();
                GlobalInit.flames = userList.get(0).getNbObst();
                GlobalInit.lives = userList.get(0).getNbLives();
                GlobalInit.coins = userList.get(0).getScore();
                handler.GetMouseManager().setUiManager(null);
                Handler.contorGames = GlobalInit.level;
                if (GlobalInit.level == 0)
                    State.SetState(handler.GetGame().getPlayState());
                else
                    State.SetState(handler.GetGame().getPlayState2());
            }
        }));
        uiManager.addObject(new UIImageButton(600, 100, 200, 100, null, () -> {
            System.out.println("Second Square");
            if (secondSquareIsLoaded){
                GlobalInit.isLoaded = true;
                GlobalInit.level = userList.get(1).getLevel();
                GlobalInit.flames = userList.get(1).getNbObst();
                GlobalInit.lives = userList.get(1).getNbLives();
                GlobalInit.coins = userList.get(1).getScore();
                handler.GetMouseManager().setUiManager(null);
                Handler.contorGames = GlobalInit.level;
                if (GlobalInit.level == 0)
                    State.SetState(handler.GetGame().getPlayState());
                else
                    State.SetState(handler.GetGame().getPlayState2());
            }
        }));
        uiManager.addObject(new UIImageButton(180, 300, 200, 100, null, () -> {
            System.out.println("Third Square");
            if (thirdSquareIsLoaded){
                GlobalInit.isLoaded = true;
                GlobalInit.level = userList.get(2).getLevel();
                GlobalInit.flames = userList.get(2).getNbObst();
                GlobalInit.lives = userList.get(2).getNbLives();
                GlobalInit.coins = userList.get(2).getScore();
                handler.GetMouseManager().setUiManager(null);
                Handler.contorGames = GlobalInit.level;
                if (GlobalInit.level == 0)
                    State.SetState(handler.GetGame().getPlayState());
                else
                    State.SetState(handler.GetGame().getPlayState2());
            }
        }));
        uiManager.addObject(new UIImageButton(600, 300, 200, 100, null, () -> {
            System.out.println("Fourth Square");
            if (fourthSquareIsLoaded){
                GlobalInit.isLoaded = true;
                GlobalInit.level = userList.get(3).getLevel();
                GlobalInit.flames = userList.get(3).getNbObst();
                GlobalInit.lives = userList.get(3).getNbLives();
                GlobalInit.coins = userList.get(3).getScore();
                handler.GetMouseManager().setUiManager(null);
                Handler.contorGames = GlobalInit.level;
                if (GlobalInit.level == 0)
                    State.SetState(handler.GetGame().getPlayState());
                else
                    State.SetState(handler.GetGame().getPlayState2());
            }
        }));



    }

    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a LoadStateului.
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
       \brief Deseneaza (randeaza) pe ecran starea curenta a LoadStateului.

       \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
    */
    @Override
    public void Draw(Graphics g)
    {
        userList = commands.selectLastFourUsers();
        g.drawImage(image,0,0,null);
        g.drawImage(Assets.textbox, 180,100, null);
        g.drawImage(Assets.textbox, 600,100, null);
        g.drawImage(Assets.textbox, 180,300, null);
        g.drawImage(Assets.textbox, 600,300, null);

        for(int i = 0; i < userList.size(); ++i){
            if (i == 0) {
                firstSquareIsLoaded = true;
                if (userList.get(i).getLevel() == 0) {
                    //primul patrat
                    g.drawImage(ImageLoader.LoadImage("/Backgrounds/img2.png"), 180, 100, 200, 100, null);
                    g.setFont(new Font("Arial", 1, 30));
                    g.setColor(Color.yellow);
                    g.drawString(userList.get(i).getUsername(), 230, 225);

                    g.setColor(Color.blue);
                    g.drawImage(Assets.money,  210, 130, 30, 30, null);
                    g.drawString(" "+userList.get(i).getScore(), 240, 155);
                    g.drawImage(Assets.inimafull,  290, 130, 30, 30, null);
                    g.drawString(" "+(3-userList.get(i).getNbLives()), 320, 155);
                    continue;

                }

                    g.drawImage(ImageLoader.LoadImage("/Backgrounds/img3.png"), 180, 100, 200, 100, null);
                    g.setFont(new Font("Arial", 1, 30));
                    g.setColor(Color.yellow);
                    g.drawString(userList.get(i).getUsername(), 230, 225);

                    g.setColor(Color.yellow);
                    g.drawImage(Assets.money,  210, 130, 30, 30, null);
                    g.drawString(" "+userList.get(i).getScore(), 240, 155);
                    g.drawImage(Assets.inimafull,  290, 130, 30, 30, null);
                    g.drawString(" "+(3-userList.get(i).getNbLives()), 320, 155);
                    continue;
            }
            if (i == 1) {
                secondSquareIsLoaded = true;
                if (userList.get(i).getLevel() == 0){
                    g.drawImage(ImageLoader.LoadImage("/Backgrounds/img2.png"), 600,100,200, 100, null);
                    g.setFont(new Font("Arial", 1, 30));
                    g.setColor(Color.YELLOW);
                    g.drawString(userList.get(i).getUsername(), 650, 225);

                    g.setColor(Color.blue);
                    g.drawImage(Assets.money,  630, 130, 30, 30, null);
                    g.drawString(" "+userList.get(i).getScore(), 660, 155);
                    g.drawImage(Assets.inimafull,  710, 130, 30, 30, null);
                    g.drawString(" "+(3-userList.get(i).getNbLives()), 740, 155);

                    continue;
                }
                g.drawImage(ImageLoader.LoadImage("/Backgrounds/img3.png"), 600,100, 200, 100,null);
                g.setFont(new Font("Arial", 1, 30));
                g.setColor(Color.yellow);
                g.drawString(userList.get(i).getUsername(), 650, 225);

                g.setColor(Color.yellow);
                g.drawImage(Assets.money,  630, 130, 30, 30, null);
                g.drawString(" "+userList.get(i).getScore(), 660, 155);
                g.drawImage(Assets.inimafull,  710, 130, 30, 30, null);
                g.drawString(" "+(3-userList.get(i).getNbLives()), 740, 155);
                continue;
            }
            if (i == 2) {
                thirdSquareIsLoaded = true;
                if (userList.get(i).getLevel() == 0){
                    g.drawImage(ImageLoader.LoadImage("/Backgrounds/img2.png"), 180,300,200, 100, null);
                    g.setFont(new Font("Arial", 1, 30));
                    g.setColor(Color.yellow);
                    g.drawString(userList.get(i).getUsername(), 230, 425);

                    g.setColor(Color.blue);
                    g.drawImage(Assets.money,  210, 335, 30, 30, null);
                    g.drawString(" "+userList.get(i).getScore(), 240, 360);
                    g.drawImage(Assets.inimafull,  290, 335, 30, 30, null);
                    g.drawString(" "+(3-userList.get(i).getNbLives()), 320, 360);
                    continue;
                }
                g.drawImage(ImageLoader.LoadImage("/Backgrounds/img3.png"), 180,300, 200, 100,null);
                g.setFont(new Font("Arial", 1, 30));
                g.setColor(Color.yellow);
                g.drawString(userList.get(i).getUsername(), 230, 425);

                g.setColor(Color.yellow);
                g.drawImage(Assets.money,  210, 335, 30, 30, null);
                g.drawString(" "+userList.get(i).getScore(), 240, 360);
                g.drawImage(Assets.inimafull,  290, 335, 30, 30, null);
                g.drawString(" "+(3-userList.get(i).getNbLives()), 320, 360);
                continue;
            }
            if (i == 3) {
                fourthSquareIsLoaded = true;
                if (userList.get(i).getLevel() == 0) {
                    g.drawImage(ImageLoader.LoadImage("/Backgrounds/img2.png"), 600, 300, 200, 100, null);
                    g.setFont(new Font("Arial", 1, 30));
                    g.setColor(Color.yellow);
                    g.drawString(userList.get(i).getUsername(), 650, 425);

                    g.setColor(Color.blue);
                    g.drawImage(Assets.money,  630, 335, 30, 30, null);
                    g.drawString(" "+userList.get(i).getScore(), 660, 360);
                    g.drawImage(Assets.inimafull,  710, 335, 30, 30, null);
                    g.drawString(" "+(3-userList.get(i).getNbLives()), 740, 360);
                    continue;
                }
                g.drawImage(ImageLoader.LoadImage("/Backgrounds/img3.png"), 600,300, 200, 100,null);
                g.setFont(new Font("Arial", 1, 30));
                g.setColor(Color.yellow);
                g.drawString(userList.get(i).getUsername(), 650, 425);

                g.setColor(Color.yellow);
                g.drawImage(Assets.money,  630, 335, 30, 30, null);
                g.drawString(" "+userList.get(i).getScore(), 660, 360);
                g.drawImage(Assets.inimafull,  710, 335, 30, 30, null);
                g.drawString(" "+(3-userList.get(i).getNbLives()), 740, 360);
            }
        }
        uiManager.render(g);

    }
}
