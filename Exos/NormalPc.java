// ------------------------- Le Cinema le plus chere en tarif normal--------------------------------------

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class NormalPc { //com.mysql.cj.jdbc.Driver
    private static String driver = "com.mysql.jdbc.Driver";
    static final String nomBD = "jdbc:mysql://localhost:3306/gescinema?characterEncoding=latin1"; //2 : Définition de la DSN JDBC
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
            st = conn.createStatement(); //4 : Creation d'une instruction JDBC
            String selection = "SELECT tarifnormal FROM cinema";
            rs = st.executeQuery(selection);
            // ___________________ EXECUTION DE LA PREMIERE REQUETTE POUR GERER LES CAS D'EGALITE ___________________
            int pluscher = 0;
            int count = 1;
            while (rs.next()) {

                if (rs.getInt(1) > pluscher)
                {
                    count = 1;
                    pluscher = rs.getInt(1);
                }
                else if (rs.getInt(1) == pluscher)
                    count ++;

            }

            String Affichage = "SELECT nomC FROM cinema WHERE tarifnormal =" + pluscher ;
            rs2 = st.executeQuery(Affichage);
            //_______________ VERIFICATION DU NOMBRE DE CINEMA AYANT LE PLUS GROS TARIF NORMAL ___________________
            if (count == 1)
                System.out.println("\n Le cinema le plus chere en tarif normal est ");
            else
                System.out.println("Les cinemas les plus cheres en tarif normal sont ");

            // ______________ AFFICHAGE DES DONNEES ____________________________
            System.out.println("\n | \t Cinema \t Tarif ");
            System.out.println(" | \t ------- \t ------- ");
            while (rs2.next()) {
                System.out.println(" | \t" + rs2.getString(1) + "\t" + pluscher + " Fcfa"); //6 : Traitement du résultat
            }




        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {//7 : Fermeture des connexions
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
