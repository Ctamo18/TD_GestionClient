// ------------------------- Le Cinema le plus chere en tarif normal--------------------------------------

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class CinemaQuartier { //com.mysql.cj.jdbc.Driver
    private static String driver = "com.mysql.jdbc.Driver";
    static final String nomBD = "jdbc:mysql://localhost:3308/gescinema?characterEncoding=latin1"; //2 : Définition de la DSN JDBC
    static String nomUser = new String("root");
    static String motP = new String("");

    public static void main(String[] args) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        ResultSet rs2 = null;

        try {
            Class.forName(driver); //1 : chargement du pilote JDBC de MySQL
        } catch (ClassNotFoundException cnfex) {
            System.out.println("Probleme de chargement de MySQL JDBC driver");
            cnfex.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(nomBD, nomUser, motP); //3 : Connexion à la BD
            st = conn.createStatement(); // Creation d'une instruction JDBC
            String selection = "SELECT * FROM Cinema WHERE numQ=1 ORDER BY nomC ";
            rs = st.executeQuery(selection);
            System.out.println("NumQ\tNomQ\tNom\tAdresse\tVille\tCP "); // Traitement du r�sultat
            System.out.println("=================================");
            while(rs.next()) {
                System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" +
                        rs.getString(3) + "\t" + rs.getInt(4));
            }
        }
        catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
            finally {//7 : Fermeture des connexions
            try {
                if (null != conn) {
                    rs.close();
                    st.close();
                    conn.close();
                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }
    }
}
