package PaooGame.Database;


/*! \class  GlobalInit
    \brief Clasa ce initializeaza global elementele user-ului din baza de date.

 */
public class GlobalInit {
    public static int coins;
    public static int flames;
    public static int level;
    public static int lives;
    public static boolean startNewGame = false;
    public static boolean isLoaded = false;
    /*! \fn  private GlobalInit()
     \brief  Constructorul de initializare a clasei.
   */
    private GlobalInit(){}
    /*! \fn  public static void initGlobals()
     \brief  Initializarea golabala a atributelor banuti, gloante si nivel.
   */
    public static void initGlobals(){
        GlobalInit.coins = 0;
        GlobalInit.flames = 30;
        GlobalInit.level = 0;
    }
}
