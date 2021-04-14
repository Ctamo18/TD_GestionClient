import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

//import com.mysql.jdbc.Driver;

public class EtatListeDesClients {

    public static void main(String[] args) {

        // - Param�tres de connexion � la base de donn�es
        //String url = "jdbc:mysql://localhost/jasper_database";
        //String login = "root";
        //String password = "root";
        //Connection connection = null;

        try {
            // - Connexion � la base
            //Driver monDriver = new com.mysql.jdbc.Driver();
            //DriverManager.registerDriver(monDriver);
            //connection = DriverManager.getConnection(url, login, password);
			
			JavaBD jBD = new JavaBD();
			jBD.connexionBD("BDGSF", "brou", "brou");

            // - Chargement et compilation du rapport
            JasperDesign jasperDesign = JRXmlLoader.load("D:\\Cours\\Cours2014\\IHM\\IHM3\\listeDesClients.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            // - Param�tres � envoyer au rapport
            Map parameters = new HashMap();
            parameters.put("Titre", "Titre");

            // - Execution du rapport
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // - Cr�ation du rapport au format PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, "D:\\Cours\\Cours2014\\IHM\\IHM3\\listeDesClients.pdf");
        } catch (JRException e) {

            e.printStackTrace();
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            try {
                 connection.close();
                } catch (SQLException e) {

                        e.printStackTrace();
                }
        }

    }
}