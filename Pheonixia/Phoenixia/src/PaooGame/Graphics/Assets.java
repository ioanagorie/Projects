package PaooGame.Graphics;

import java.awt.image.BufferedImage;

/*! \class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets
{
    /// Referinte catre elementele grafice (dale) utilizate in joc.
    private static final int tileWidth = 100, tileHight = 100;
    public static BufferedImage element;
    public static BufferedImage pasare;
    public static BufferedImage elicopter;
    public static BufferedImage inimafull;
    public static BufferedImage inimacrapata;
    public static BufferedImage flame;
    public static BufferedImage money;
    public static BufferedImage copac;
    public static BufferedImage brad;
    public static BufferedImage floare;
    public static BufferedImage pamant;
    public static BufferedImage buttonNewGame;
    public static BufferedImage buttonSave;
    public static BufferedImage textbox;
    public static BufferedImage buttonLoad;
    public static BufferedImage buttonMoney;
    public static BufferedImage buttonPlay;
    public static BufferedImage buttonAbout;
    public static BufferedImage buttonQuit;
    public static BufferedImage buttonExit;
    public static BufferedImage buttonNext;
    public static BufferedImage buttonBack;
    public static BufferedImage castle;
    public static BufferedImage princess;
    public static BufferedImage king;
    public static BufferedImage witch;
    public static BufferedImage destroyed;

    /*! \fn public static void Init()
           \brief Functia initializaza referintele catre elementele grafice utilizate.

           Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
           sa fie parametrizate. Din acest motiv referintele nu sunt finale.
        */

    public static void Init() {
        /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        SpriteSheet sheetlevel = new SpriteSheet(ImageLoader.LoadImage("/others/spritesheet_nivel1.png"));

        /// Se obtin subimaginile corespunzatoare elementelor necesare în joc.
        pasare=sheetlevel.crop(0,0);
        elicopter=sheetlevel.crop(2,0);
        flame=sheetlevel.crop(4,0);
        money=sheetlevel.crop(5,0);
        inimacrapata=sheetlevel.crop(6,0);
        inimafull=sheetlevel.crop(7,0);
        buttonMoney =sheetlevel.crop(8,0);
        element = sheetlevel.crop(0, 0);

        /// Se obtin subimaginile corespunzatoare elementelor necesare nivelului 2.
        SpriteSheet sheetlevel2 = new SpriteSheet(ImageLoader.LoadImage("/others/spritesheet_nivel2.png"));
        brad=sheetlevel2.crop(0,0);
        floare=sheetlevel2.crop(1,0);
        pamant=sheetlevel2.crop(2,0);
        copac=sheetlevel2.crop(3,0);

        /// Se obtin subimaginile corespunzatoare butoanelor necesare.
        SpriteSheet sheetButtons =new SpriteSheet((ImageLoader.LoadImage("/others/butoanemeniu.png")));

        buttonNewGame= sheetButtons.crop2(0,0);
        buttonSave = sheetButtons.crop2(1,0);
        buttonLoad = sheetButtons.crop2(2,0);
        buttonAbout = sheetButtons.crop2(3,0);
        buttonPlay = sheetButtons.crop2(4,0);
        buttonQuit = sheetButtons.crop2(5,0);
        textbox    =sheetButtons.crop2(6,0);
        buttonExit = sheetButtons.crop2(7,0);
        buttonNext = sheetButtons.crop2(8,0);
        buttonBack = sheetButtons.crop2(9,0);

        /// Se obtin subimaginile corespunzatoare animațiilor regăsite în state-urile ABOUT, WINSTATE2.
        SpriteSheet sheetstatics = new SpriteSheet(ImageLoader.LoadImage("/others/statics.png"));

        castle=sheetstatics.crop(0,0);
        princess=sheetstatics.crop(1,0);
        king=sheetstatics.crop(2,0);
        witch=sheetstatics.crop(3,0);
    }


}