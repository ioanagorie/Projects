package PaooGame.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*! \class Database
    \brief Clasa ce implementeaza notiunea de baza de date a jocului.

    Prin intermediul bazei de date care se va conecta la program, userul
    va avea posibilitatea sa isi reia progresul in joc de la momentul
    in care l-a salvat.
    Design patternul folosit pentru implementarea conexiunii programului cu baza de date este Singleton.
 */
public class Database {
    private static final String URL = "jdbc:sqlite:users.db";
    private static Connection connection = null;

    /*! \fn  private Database()
      \brief  Constructorul de initializare a clasei.
    */
    private Database(){}

    /*! \fn  public static Connection getConnection()
      \brief  Daca exista o conexiune, functia realizeaza returnarea ei,
      iar in caz contrat creeaza o conexiune prin intermediul functiei
      getConnection().
   */
    public static Connection getConnection(){
        if (connection == null)
            createConnection();
        return connection;
    }
    /*! \fn  private static void createConnection()
      \brief  Creeaza o conexiune.
   */
    private static void createConnection(){
        try {
            connection = DriverManager.getConnection(URL);
            connection.setAutoCommit(false); //daca da eroare nu se realizeaza updatarea informatiilor
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*! \fn  public static void closeConnection()
      \brief  Incheie o conexiune.
   */
    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
