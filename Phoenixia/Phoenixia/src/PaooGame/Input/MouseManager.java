package PaooGame.Input;

import PaooGame.UI_Objects.UIManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*! \class MouseManager
    \brief Gestioneaza intrarea (input-ul) de la mouse.

    Clasa citeste daca au fost apasat un click si seteaza corespunzator un flag.
    In program trebuie sa se tina cont de flagul aferent click-ului de interes. Daca flagul respectiv este true inseamna
    ca click-ul respectiv a fost apasat, iar daca este false, nu a fost apasat.
 */
public class MouseManager extends MouseAdapter {
    private boolean leftPressed;  /*!< Flag pentru contorizarea apasarii clickului stang.*/
    private boolean rightPressed; /*!< Flag pentru contorizarea apasarii clickului drept.*/
    private int mouseX;           /*!< Locatia pe axa X a mouse-ului.*/
    private int mouseY;           /*!< Locatia pe axa Y a mouse-ului.*/
    private UIManager uiManager;  /*!< Referinta catre manager */

    /*! \fn public MouseManager
        \brief constructor nefolosit in stagiul curent al proiectului.
     */
    public MouseManager() {
        //constructor null
    }

    /*! \fn public UIManager
        \brief returneaza referinta la managerul curent
     */

    public UIManager getUiManager() {
        return uiManager;
    }

     /*! \fn public void setUIManager(UIManager uiManager)
        \brief Seteaza referinta catre managerul curent

        \param uiManager Referinta catre managerul curent
     */

    public void setUiManager(UIManager uiManager) {
        this.uiManager = uiManager;
    }

    /*! \fn public void mousePressed(MouseEvent e)
        \brief Functie ce va fi apelata atunci cand este apasat un click pe mouse. (Nu conteaza ce click)
               De aceea gestionam apasarea click-ului stang sau drept in interiorul functiei.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = true;
        }
        if (e.getButton() == MouseEvent.BUTTON3) //right click = BUTTON3
            rightPressed = true;
    }

    /*! \fn public void mouseReleased(MouseEvent e)
             \brief Functie ce va fi apelata atunci cand click-ul nu mai este apasat. (Nu conteaza ce click)
                    De aceea gestionam oprirea apasarii click-ului stang sau drept in interiorul functiei.
      */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = false;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = false;
        }
        if (uiManager != null)
            uiManager.onMouseRelease(e);
    }

    /*! \fn public void mouseMoved(MouseEvent e)
        \brief Functie ce memoreza locatia efectiva a cursorului pe ecran.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if (uiManager != null)
            uiManager.onMouseMove(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}


