package PaooGame.States;

import java.awt.*;
import PaooGame.Handler;

/*! \class State
    \brief Implementeaza notiunea abstracta de stare a jocului/programului.

    Programul este structurat sub forma unui "pachet" cu mai multe stari, a caror scop este
    de a-l ghida pe utilizator de la meniul de baza spre jocul propriu-zis sau spre o
    "pagina" cu informatii despre functionalitatea si regulile jocului. Aceste stari
    ajuta la organizarea jocului in mai multe etape ( niveluri ) dar si la creearea
    slide-urilor intermediare dintre nivelul 1 si 2, care ii ofera informatii
    utilizatorului despre suma acumulata de banuti, cat si a scenariilor de finalitate
    a jocului, fie ea cu un status de castigator sau pierzator (WinState2, GameOverState).

 */
public abstract class State
{
    ///Urmatoarele atribute sunt statice pentru a evita dealocarea spatiului de memorie la trecerea dintr-o stare in alta.
    private static State previousState  = null; /*!< Referinta catre starea anterioara a jocului.*/
    private static State currentState   = null; /*!< Referinta catre starea curenta a jocului: game, meniu, settings, about etc.*/
    protected Handler handler;
    public State(Handler handler)
    {
        this.handler = handler;
    }

    /*! \fn public static void SetState(State state)
       \brief Seteaza starea curenta a jocului.

       \param state Noua stare a programului (jocului).
    */
    public static void SetState(State state)
    {
        previousState = currentState;
        currentState = state;
    }

    /*! \fn public static State GetState()
       \brief Returneaza starea curenta a jocului.

    */
    public static State GetState()
    {
        return currentState;
    }

    ///Metoda abstracta destinata actualizarii starii curente.
    public abstract void Update();

    ///Metoda abstracta destinata desenarii starii curente
    public abstract void Draw(Graphics g);


}
