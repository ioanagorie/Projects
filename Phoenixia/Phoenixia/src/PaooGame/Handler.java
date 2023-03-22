package PaooGame;

import PaooGame.Maps.Map;
import PaooGame.Input.KeyManager;
import PaooGame.Input.MouseManager;

import java.awt.*;

/*! \class Handler
    \brief Clasa ce retine o serie de referinte ale unor elemente pentru a fi usor accesibile.

    Altfel ar trebui ca functiile respective sa aiba o serie intreaga de parametri si ar ingreuna programarea.
 */
public class Handler
{
    private Game game;   /*!< Referinta catre obiectul Game.*/
    private Map map;     /*!< Referinta catre harta curenta.*/
    public static int boardUpLimit;
    public static int boardDownLimit;
    public static Graphics g;
    public static int contorCurent;
    public static int contorTotal;
    public static int contorGames = 0;

     /*! \fn public Handler(Game game)
        \brief Constructorul de initializare al clasei.

        \param game Referinta catre obiectul game.
     */

    public Handler(Game game)
    {
        this.game = game;
    }

     /*! \fn public KeyManager GetKeyManager()
        \brief Returneaza referinta catre managerul evenimentelor de tastatura.
     */
    public KeyManager GetKeyManager()
    {
        return game.GetKeyManager();
    }

     /*! \fn public KeyManager GetKeyManager()
        \brief Returneaza referinta catre managerul evenimentelor de mouse.
     */
    public MouseManager GetMouseManager(){
        return game.GetMouseManager();
    }

    /*! \fn public int GetWidth()
       \brief Returneaza latimea ferestrei jocului.
    */
    public int GetWidth()
    {
        return game.GetWidth();
    }

    /*! \fn public int GetHeight()
        \brief Returneaza inaltimea ferestrei jocului
     */

    public int GetHeight()
    {
        return game.GetHeight();
    }

    /*! \fn public Game GetGame()
        \brief Intoarce referinta catre obiectul Game.
     */

    public Game GetGame()
    {
        return game;
    }

    /*! \fn public void SetGame(Game game)
        \brief Seteaza referinta catre un obiect Game.

        \param game Referinta obiectului Game.
     */

    public void SetGame(Game game)
    {
        this.game = game;
    }

    /*! \fn public Map GetMap()
        \brief Intoarce referinta catre harta curenta.
     */
    public Map GetMap()
    {
        return map;
    }
    /*! \fn public void SetMap(Map map)
          \brief Seteaza referinta catre harta curenta.

          \param map Referinta catre harta curenta.
       */
    public void SetMap(Map map)
    {
        this.map = map;
    }

    /*! \fn public int getContorCurent()
       \brief Intoarce referinta catre contorul ce tine evidenta banutilor colectati
               in nivelul curent (1 sau 2).
    */
    public int getContorCurent(){
        return contorCurent;
    }
    /*! \fn public void setContorCurent(int contor)
        \brief Seteaza referinta catre contorul curent.

        \param contor Referinta contorului curent.
     */
    public void setContorCurent(int contor){
        this.contorCurent =contor;
    }
    /*! \fn public int getContorTotal()
       \brief Intoarce referinta catre contorul ce tine evidenta banutilor colectati
              in total pana in momentul curent al jocului.
    */
    public int getContorTotal(){
        return contorTotal;
    }
    /*! \fn public void setContorTotal(int contor)
        \brief Seteaza referinta catre contorul total.

        \param contor Referinta contorului total.
     */
    public void setContorTotal(int contor){
        this.contorTotal =contor;
    }

    /*! \fn public int getContorGames()
           \brief Intoarce referinta catre contorul ce tine evidenta nivelul curent.

        */
    public static int getContorGames() {
        return contorGames;
    }
    /*! \fn public static void setContorGames(int contor)
           \brief Seteaza referinta catre contorul nivelului actual.

           \param contor Referinta contorului nivelului actual.
     */
    public static void setContorGames(int level) {
        Handler.contorGames = level;
    }
}
