package PaooGame.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/*! \class KeyManager
 \brief Gestioneaza intrarea (input-ul) de tastatura.

 Clasa citeste daca au fost apasata o tasta, stabiliteste ce tasta a fost actionata si seteaza corespunzator un flag.
 In program trebuie sa se tina cont de flagul aferent tastei de interes. Daca flagul respectiv este true inseamna
 ca tasta respectiva a fost apasata si false nu a fost apasata.
*/
public class KeyManager implements KeyListener
{
    private boolean[] keys;   /*!< Vector de flaguri pentru toate tastele. */
    public boolean up;        /*!< Flag pentru tasta "sus" apasata.*/
    public boolean down;      /*!< Flag pentru tasta "jos" apasata.*/

    /*! \fn public KeyManager()
        \brief Constructorul clasei.
     */
    public KeyManager()
    {
        keys = new boolean[256];
    }

    /*! \fn public void Update()
       \brief metoda care se ocupa cu actualizarea managerului de taste.
    */
    public void Update()
    {
        up    = keys[KeyEvent.VK_UP];
        down  = keys[KeyEvent.VK_DOWN];
    }
    /*! \fn public void keyPressed(KeyEvent e)
       \brief Functie ce va fi apelata atunci cand un un eveniment de tasta apasata este generat.

        \param e obiectul eveniment de tastatura.
    */
    @Override
    public void keyPressed(KeyEvent e)
    {
        keys[e.getKeyCode()] = true; //cand o tasta este apasata retin in vectorul de flaguri acest lucru
    }
    /*! \fn public void keyReleased(KeyEvent e)
        \brief Functie ce va fi apelata atunci cand un un eveniment de tasta eliberata este generat.

         \param e obiectul eveniment de tastatura.
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
        keys[e.getKeyCode()] = false; //cand o tasta este eliberata retin in vectorul de flaguri
    }
    /*! \fn public void keyTyped(KeyEvent e)
        \brief Functie ce va fi apelata atunci cand o tasta a fost apasata si eliberata.
        Momentan aceasta functie nu este utila in program.
     */
    @Override
    public void keyTyped(KeyEvent e)
    {

    }
}
