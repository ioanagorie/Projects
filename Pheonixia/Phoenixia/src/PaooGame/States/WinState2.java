package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.ImageLoader;
import PaooGame.Handler;
import PaooGame.UI_Objects.UIImageButton;
import PaooGame.UI_Objects.UIManager;


import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class WinState2
    \brief Implementeaza notiunea de finalizare cu succes a nivelului final, 2, si implicit
      a intregului joc.

      Reprezinta starea care informeaza utilizatorul ca a reusit
      sa reziste atacului inamicului in nivelul 2, ii aduce la cunostinta
      suma de banuti colectata pe parcursul cele de a 2-a etape ale jocului , iar in
      final, in functie de valoarea stransa de player in cufarul sau pe parcursul
      celor doua nivele, este stabilit statusul de castigator sau de supercastigator.
 */
public class WinState2 extends State {
    private final UIManager uiManager;
    private final BufferedImage win;
    int contor = 1;
    int timer = 1;

    /*! \fn public WinState(Handler handler)
       \brief Constructorul de initializare a clasei.

       \param handler referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
    */
    public WinState2(Handler handler) {
        ///Apel al constructorului clasei de baza.
        super(handler);
        ///Initializarea backgroundului starii de WinState2.
        win = ImageLoader.LoadImage("/Backgrounds/win2.png");
        ///Se construieste un nou manager de obiecte UI
        uiManager = new UIManager(handler);
        handler.GetMouseManager().setUiManager(uiManager);


    }

    /*! \fn public Image getPlayerImg()
      \brief Returneaza imaginea player-ului care da din aripi.

   */
    public Image getPlayerImg() {
        Image img = Toolkit.getDefaultToolkit().getImage("res/others/personaj.gif");
        return img;
    }

    /*! \fn public Image getWaterfallImg()
       \brief Returneaza imaginea cascadei curgatoare.

    */
    public Image getWaterfallImg() {
        Image img = Toolkit.getDefaultToolkit().getImage("res/others/Cascada.gif");
        return img;
    }

    /*! \fn public Image getTrunkImg()
       \brief Returneaza imaginea cufarului care se deschide/inchide.

    */
    public Image getTrunkImg() {
        Image img = Toolkit.getDefaultToolkit().getImage("res/others/Cufar.gif");
        return img;
    }
    /*! \fn public void Update()
       \brief Actualizeaza starea curenta a lui WinState2.
    */
    @Override
    public void Update() {
        timer++;
        handler.GetMouseManager().setUiManager(uiManager);
        uiManager.Update();
    }

    /*! \fn public void Draw(Graphics g)
           \brief Deseneaza (randeaza) pe ecran starea curenta a starii WinState2.

           \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
        */
    @Override
    public void Draw(Graphics g) {
        if (timer < 300) {
            g.drawImage(win, 0, 0, null);
            g.setColor(Color.ORANGE);
            g.setFont(new Font("Arial", 1, 30));
            g.drawString("is loading . . .", 370, 400);
            }
            if (timer > 300) {
                g.drawImage(ImageLoader.LoadImage("/Backgrounds/slide.png"), 0, 0, null);
                g.setColor(Color.ORANGE);
                g.setFont(new Font("Arial", 1, 20));
                if (timer < 500) g.drawImage(getPlayerImg(), 160 + (timer - 300), 220, 170, 170, null);
                if (timer > 460 && timer < 600) g.drawImage(Assets.princess, 320 + timer - (300), 210, 150, 150, null);
                if (timer >= 600 && timer < 800) {
                    g.drawImage(Assets.princess, 620, 210, 150, 150, null);
                    g.setColor(Color.ORANGE);
                    g.setFont(new Font("Arial", 1, 20));
                    g.drawString("Congratulations! You made PHOENIXIA human again!", 185, 415);
                }
                if (timer < 800) g.drawImage(getWaterfallImg(), 330, 80, 300, 300, null);
                int x = 370, y = 320;
                if (timer >= 800 && timer < 950) {
                    g.drawImage(getTrunkImg(), x, y, 100, 100, null);
                    g.drawImage(Assets.money, x + (timer - 800), y - (timer - 800), 60, 60, null);
                }
                if (timer > 950) {
                    g.setColor(Color.PINK);
                    g.setFont(new Font("Arial", 1, 20));
                    g.drawString("You've reached a total of " + handler.getContorTotal() + " coins !", 160, 200);
                    g.drawImage(getTrunkImg(), x, y, 100, 100, null);
                    g.drawImage(Assets.money, 520, 170, 60, 60, null);
                }
                if (timer > 1100 && timer < 1400) {
                    if (handler.getContorTotal() >= 15) {
                        g.setColor(Color.ORANGE);
                        g.setFont(new Font("Arial", 1, 20));
                        g.drawString("Congratulations! You have more than 15 coins in your trunk !", 170, 250);
                        if (timer > 1250) {
                            g.setColor(Color.PINK);
                            g.drawString("So PHOENIXIA has enough money to get back to her father!", 170, 280);
                        }
                    } else {
                        g.setColor(Color.ORANGE);
                        g.setFont(new Font("Arial", 1, 20));
                        g.drawString("Sadly you don't have at least 15 coin in your trunk !", 170, 250);
                        if (timer > 1250) {
                            g.setColor(Color.PINK);
                            g.drawString("So PHOENIXIA is unable to get back to her father!", 170, 280);
                        }
                    }
                }
                if (timer >= 1450) {
                    if (handler.getContorTotal() >= 15) {
                        g.drawImage(ImageLoader.LoadImage("/Backgrounds/win_superwinner.png"), 0, 0, null);
                        g.drawImage(getWaterfallImg(), 180, 270, 150, 150, null);
                        g.drawImage(Assets.castle, 600, 280, 150, 150, null);
                        if (timer < 1700) {
                            g.drawImage(Assets.princess, 240 + (timer - 1450), 280, 120, 120, null);

                        }

                    } else {
                        g.drawImage(ImageLoader.LoadImage("/Backgrounds/win_winner.png"), 0, 0, null);
                        g.drawImage(getWaterfallImg(), 300, 270, 150, 150, null);
                        g.drawImage(Assets.princess, 500, 284, 120, 120, null);
                    }
                }
                if (timer >= 1700) {
                    if (handler.getContorTotal() >= 15) {
                        g.setColor(Color.ORANGE);
                        g.drawString("Congratulations! PHOENIXIA is back home thanks to you!", 170, 280);
                    } else {
                        g.setColor(Color.ORANGE);
                        g.drawString("You win but PHOENIXIA got stuck on the Azure Island!", 170, 280);
                    }

                }
                if (timer >= 1750) {
                        uiManager.addObject(new UIImageButton(840, 12, 100, 50, Assets.buttonExit, () -> {
                            handler.GetMouseManager().setUiManager(null);
                            State.SetState(handler.GetGame().getMenuState());
                        }));
                }

            }

            uiManager.render(g);
        }
    }
