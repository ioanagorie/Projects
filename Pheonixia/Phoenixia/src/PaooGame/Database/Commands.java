package PaooGame.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*! \class Commands
    \brief Clasa ce contine o serie de metode ce prelucreaza datele regasite in baza de date

 */
public class Commands {
    /*! \fn  public void inserDatabase(User user)
       \brief adauga in baza de date un nou user.

       \param user  Referinta catre un obiect de tipul User.
    */
    public void inserDatabase(User user){
        try {
            Connection con = Database.getConnection();
            String sqlCommand = "SELECT MAX(id) + 1 as id from users"; //generez id-ul din tabela users
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            int idUser = 0;
            if (rs.next())
                idUser = rs.getInt("id");

            user.setId(idUser);
            int idUserForString = idUser % 4;
            idUserForString++;
            String nameUser = "User " + idUserForString;
            int scoreInsert = user.getScore();
            int levelInsert = user.getLevel();
            int nb_obstInsert = user.getNbObst();
            int nb_lives = user.getNbLives();


            PreparedStatement pstmt = con.prepareStatement(
                    "insert into users values(?, ?, ?, ?, ?, ?)"
            );
            pstmt.setInt(1, idUser);
            pstmt.setString(2, nameUser);
            pstmt.setInt(3, scoreInsert);
            pstmt.setInt(4, levelInsert);
            pstmt.setInt(5, nb_obstInsert);
            pstmt.setInt(6, nb_lives);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /*! \fn  public List<User> selectLastFourUsers()
       \brief se ocupa cu extragerea din baza de date a ultimilor 4 inregistrari in baza de date.
    */
    public List<User> selectLastFourUsers(){
        try {
            List<User> listUsers = new ArrayList<>();
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();
            String sql = "Select id, username, score, level, nb_obst, nb_lives from users order by rowid desc limit 4;";
            ResultSet rs = stmt.executeQuery(sql);
            String userInsert;
            int scoreInsert;
            int levelInsert;
            int nbObstInsert;
            int nbLives;
            while (rs.next()) {
                userInsert = rs.getString("username");
                scoreInsert = rs.getInt("score");
                levelInsert = rs.getInt("level");
                nbObstInsert = rs.getInt("nb_obst");
                nbLives = rs.getInt("nb_lives");
                User userCopy = new User(userInsert, scoreInsert, levelInsert, nbObstInsert, nbLives);
                userCopy.setId(rs.getInt("id"));
                listUsers.add(userCopy);
            }
            return listUsers;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    /*! \fn  public User lastUser()
       \brief se ocupa cu extragerea din baza de date a ultimei inregistrari
    */
    public User lastUser(){
        try {
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();
            String sql = "select username, score, level, nb_obst, nb_lives from users where id = (select max(id) from users)";
            ResultSet rs = stmt.executeQuery(sql);
            String userInsert;
            int scoreInsert;
            int levelInsert;
            int nbObstInsert;
            int nbLives;
            if (rs.next()) {
                userInsert = rs.getString("username");
                scoreInsert = rs.getInt("score");
                levelInsert = rs.getInt("level");
                nbObstInsert = rs.getInt("nb_obst");
                nbLives = rs.getInt("nb_lives");
                return new User(userInsert, scoreInsert, levelInsert, nbObstInsert, nbLives);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
