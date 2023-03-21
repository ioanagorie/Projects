package PaooGame.Maps;

import PaooGame.Handler;

/*!\class Map
   \brief Clasa care se ocupa de caracteristicile hartii jocului.
 */
public class Map
{
    private Handler handler;
    private int width;
    private int height;
    private int [][] tiles;


    private int Xplayer;
    private int Yplayer;
    private int Xenemy;
    private int Yenemy;

    ////setters si getters pentru setarea/returnarea coordonatelor pe harta a player-ului si inamicului
    public int getXplayer(){
        return Xplayer;
    }
    public int getYplayer(){
        return Yplayer;
    }
    public int getXenemy(){
        return Xenemy;
    }
    public int getYenemy(){
        return Yenemy;
    }
    public void setXplayer(int x){
        Xplayer = x;
    }
    public void setYplayer(int y){
        Yplayer = y;
    }
    public void setXenemy(int x){
        Xenemy = x;
    }
    public void setYenemy(int x){
        Yenemy = x;
    }

    /*! \fn public Map(Handler handler)
         \brief Constructorul de initializare a clasei Map.

         \param handler Referinta catre obiect de tipul short-cut.
      */
    public Map(Handler handler)
    {
        this.handler = handler;

    }
}