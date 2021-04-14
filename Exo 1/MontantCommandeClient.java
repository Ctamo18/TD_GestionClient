import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MontantCommandeClient {
    private static String driver = "com.mysql.jdbc.Driver";
    static final String nomBD = "jdbc:mysql://localhost:3308/bdgsf?characterEncoding=latin1";
    static String nomUser = new String("root");
    static String motP = new String("");

     public static void Lafonction(String nom, int numer0)
    {
        Connection conn = null;
        Statement st = null;
        ResultSet rs1 = null;
        try {
            Class.forName(driver); //1 : chargement du pilote JDBC de MySQL
        } catch (ClassNotFoundException cnfex) {
            System.out.println("Problme de chargement de MySQL JDBC driver");
            cnfex.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(nomBD, "root", ""); //3 : Connexion à la BD
            st = conn.createStatement(); //4 : Creation d'une instruction JDBC
            String sql = "SELECT detailcom.qteCom , produit.prixUnit FROM detailcom,client,produit,commande WHERE" +
                    " detailcom.numCom = " + numer0 + " AND client.nom = '" +nom+ "' AND client.numcl=commande.numcl AND " +
                    "commande.numcom = detailcom.numcom AND  produit.numprod = detailcom.numprod"; //5 : Exécution de la requête
            rs1 = st.executeQuery(sql);
            System.out.println("=================================");
            int total =0;
            while(rs1.next()) {
                total += rs1.getInt(1) * rs1.getInt(2);
                System.out.println(rs1.getInt(1) + "\t" + rs1.getInt(2) + "\t" + rs1.getInt(1) * rs1.getInt(2));// "Numcl\tNom\tAdresse\t   tel"); //6 : Traitement du résultat
            }
            System.out.println("Le montant de la commande 1 de Toto est : " + total);// "Numcl\tNom\tAdresse\t   tel"); //6 : Traitement du résultat

            //  System.out.println("=================================");
            //  System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" +
            //          rs.getString(3) + "\t" + rs.getInt(4));
            // }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {//7 : Fermeture des connexions
            try {
                if (null != conn) {
                    rs1.close();
                    st.close();
                    conn.close();
                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
    Lafonction("Toto Alidou", 1);

}
}
