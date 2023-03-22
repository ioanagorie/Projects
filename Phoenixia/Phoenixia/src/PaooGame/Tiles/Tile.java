package PaooGame.Tiles;
import java.awt.*;
import java.awt.image.BufferedImage;

/*!\class Tile
   \brief Implementeaza notiunea abstracta de dala din joc.
 */
public class Tile {
    private static final int NO_TILES = 0;
    public static Tile[] tiles = new Tile[NO_TILES];

    public static final int TILE_WIDTH = 100;
    public static final int TILE_HEIGHT = 100;

    protected BufferedImage img;
    protected final int id;
    private int width, height;

    /*! \fn public Tile(BufferedImage image, int idd)
         \brief Constructorul de initializare a clasei Tile

         \param image Referinta catre obiect de tipul BufferedImage
         \param idd numarul de identificare al dalei
      */
    public Tile(BufferedImage image, int idd) {
        img = image;
        id = idd;

        tiles[id] = this;
    }

    /*! \fn public void Update()
        \brief Actalizarea dalei
     */
    public void Update() {

    }

    /*! \fn  public void Draw(Graphics g, int x, int y)
        \brief Desenarea dalei

        \param g referinta catre obiect de tipul graphics
        \param x coordonata pe axa Ox a inceputului desenarii dalei
        \param y coordonata pe axa Oy a inceputului desenarii dalei
     */
    public void Draw(Graphics g, int x, int y) {
        g.drawImage(img, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    public boolean IsSolid() {
        return false;
    }

    public int GetId() {
        return id;
    }


    public void render(Graphics g, int x, int y) {
        g.drawImage(img, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    public boolean isNotTraverseble() {
        return false;
    }
}