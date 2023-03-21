package PaooGame;

import PaooGame.Database.GlobalInit;

/*!
 \class Main
 \brief Clasa utilizatorului ce lanseaza jocul in executie.
 */
public class Main
{
    /*! \fn public static void main(String[] args)
        \brief Constructorul de initializare a clasei Main

       \param handler Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).

     */
    public static void main(String[] args)
    {
        GlobalInit.initGlobals(); //initializarea gloabla a elementelor userului
        Game paooGame = new Game("Phoenixia",960 , 540); //crearea unei instante de tipul Game
        paooGame.StartGame();                                             //pornirea jocului
    }
}
