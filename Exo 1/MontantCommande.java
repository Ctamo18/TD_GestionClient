import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Exo1MontantCommandeClient {
    private static String driver ="com.mysql.jdbc.Driver";
    static final String nomBD = "jdbc:mysql://localhost:3306/bdgsf?characterEncoding=latin1"; //2 : Définition de la DSN JDBC
	static String nomUser = new String("root");
    static String motP = new String("");
    static Connection conn = null;
    static Statement st = null;
    static ResultSet rs = null;
    static String nomClient = "";
    static int numCom = 0;

	public static void main(String[] args) {
		try {
			Class.forName(driver); //1 : chargement du pilote JDBC de MySQL
		}
		catch(ClassNotFoundException cnfex) {
			System.out.println("Probleme de chargement de MySQL JDBC driver");
			cnfex.printStackTrace();
		}
        //EXO 1
		try {
            conn = DriverManager.getConnection(nomBD,nomUser,motP); //3 : Connexion à la BD
			st = conn.createStatement(); //4 : Creation d'une instruction JDBC
			String sql = "SELECT designation, prixUnit, qteCom FROM client, commande, detailcom, produit WHERE client.numcl = 1 AND client.numcl = commande.numCl AND commande.numCom = 1 AND commande.numCom = detailcom.numCom AND detailCom.numProd = produit.numProd "; //5 : Exécution de la requête
			rs = st.executeQuery(sql);
			System.out.println("Designation\tPrixUnit\tQteCom\t\tMontant"); //6 : Traitement du résultat
            System.out.println("=====================================================");
            int montantTotal = 0;
            while(rs.next()) {
               System.out.println(rs.getString(1) + "\t\t" + rs.getInt(2) + "\t\t" + rs.getInt(3) + "\t\t" + (rs.getInt(2) * rs.getInt(3)) );
               montantTotal += rs.getInt(2) * rs.getInt(3);
            }
            System.out.println("\nMontant total = "+ montantTotal);
		}
		catch(SQLException sqlex){
			sqlex.printStackTrace();
		}
		finally {//7 : Fermeture des connexions
			try {
				if(null != conn) {
					rs.close();
					st.close();
					conn.close();
				}
			}
			catch (SQLException sqlex) {
				sqlex.printStackTrace();
			}
		}
	}
