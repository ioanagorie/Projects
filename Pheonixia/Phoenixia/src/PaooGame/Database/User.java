package PaooGame.Database;

/*! \class User
    \brief Clasa ce implementeaza notiunea de user/utilizator al aplicatiei.

 */
public class User {
    private int id;                /*!< id-ul utilizatorului, numarul unic prin care este identificat.*/
    private final String username; /*!< Numele de utilizator, sirul unic prin care este identificat.*/
    private final int score;       /*!< Numarul de banuti stransi de utilizator pana in momentul actual.*/
    private final int level;       /*!< Nivelul actual la care se afla utilizatorul.*/
    private final int nbObst;      /*!< Numarul de obstacole ramas utilizatorului la nivelul curent.*/
    private final int nbLives;     /*!< Numarul de vieti pierdute de utilizator in nivelul curent.*/
    /*! \fn  public User(String username, int score, int level, int nbObst, int nbLives)
          \brief Constructor de initializare al clasei User.

          \param username username-ul utilizatorului
          \param score    scorul utilizatorului pana la momentul salvarii.
          \param level    nivelul la care se afla utilizatorul
          \param nbObst   numarul de obstacole ramase in nivelul curent.
          \param nbLives  numarul de vieti pierdute in nivelul curent.
       */
    public User(String username, int score, int level, int nbObst, int nbLives) {
        this.username = username;
        this.score = score;
        this.level = level;
        this.nbObst = nbObst;
        this.nbLives = nbLives;
    }
    ///Setters si getters care se ocupa cu setarea si returnarea valorilor membrilor clasei User.
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public int getScore() {
        return score;
    }
    public int getLevel() {
        return level;
    }
    public int getNbObst() {
        return nbObst;
    }
    public int getNbLives() {
        return nbLives;
    }

    /*! \fn  public String toString()
    \brief  Functie ce returneaza datele userului sub forma unui sir.
  */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", score=" + score +
                ", level=" + level +
                ", nbObst=" + nbObst +
                '}';
    }
}
