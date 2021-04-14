import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.sql.*;

public class GesClient extends Application {
  static String nomUser = new String("root");
  static String motP = new String("");
  Label labelNumCl = new Label("Numéro :");
  TextField textFieldNumCl = new TextField();
  Label labelNom = new Label("Nom :");
  TextField textFieldNom = new TextField();
	
  public static void main(String[] args) {
    launch(GesClient.class, args);
  }
  
  @Override
  public void start(Stage stage) {
    Group root = new Group();
    Scene scene = new Scene(root, 500, 330, Color.YELLOW);
    stage.setScene(scene);
    stage.setTitle("Gestion des clients");
	
	labelNumCl.setLayoutX(15);
    labelNumCl.setLayoutY(40);
	textFieldNumCl.setLayoutX(80);
    textFieldNumCl.setLayoutY(40);
    
	labelNom.setLayoutX(15);
    labelNom.setLayoutY(70);
	textFieldNom.setLayoutX(80);
    textFieldNom.setLayoutY(70);
 
    Button boutonPremier = new Button("Premier");
	boutonPremier.setLayoutX(45);
    boutonPremier.setLayoutY(120);
	Button boutonPrecedent = new Button("Précédent");
	boutonPrecedent.setLayoutX(115);
    boutonPrecedent.setLayoutY(120);
	Button boutonSuivant = new Button("Suivant");
	boutonSuivant.setLayoutX(200);
    boutonSuivant.setLayoutY(120);
	Button boutonDernier = new Button("Dernier");
	boutonDernier.setLayoutX(290);
    boutonDernier.setLayoutY(120);
	
    boutonPremier.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
		   int numClient = 1;
		   chargerEnreg(numClient, 0);
        }
    });
	boutonPrecedent.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) { 
		  int numClient = Integer.valueOf(textFieldNumCl.getText()).intValue();
		  chargerEnreg(numClient, -1);
        }
    });	
	boutonSuivant.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
		   int numClient = Integer.valueOf(textFieldNumCl.getText()).intValue();
		   chargerEnreg(numClient, 1);
        }
    });	
	boutonDernier.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
		   int numClient = nbTotalEnreg();
		   chargerEnreg(numClient, 0);
        }
    });	
	
    root.getChildren().add(labelNumCl);
    root.getChildren().add(textFieldNumCl);
	root.getChildren().add(labelNom);
    root.getChildren().add(textFieldNom);
	
	root.getChildren().add(boutonPremier);
	root.getChildren().add(boutonPrecedent);
	root.getChildren().add(boutonSuivant);
	root.getChildren().add(boutonDernier);
	
    scene.setRoot(root);
    stage.show();
	int numClient = 1; //on charge le 1er enreg au depart
	chargerEnreg(numClient, 0);
  }
  
  public int numeroEnreg(int numClient) {
	int numEnreg = 0;
	String driver ="com.mysql.jdbc.Driver";
    String nomBD = "jdbc:mysql://localhost:3308/bdgsf?characterEncoding=latin1";
	try {
	     Class.forName(driver);
         Connection conn = DriverManager.getConnection(nomBD,nomUser,motP);
		 Statement st = conn.createStatement();  
         String sql = "SELECT * FROM Client ;";
	     ResultSet rs = st.executeQuery(sql);
	     while (rs.next()) {
	      numEnreg++;
	       int numCl = rs.getInt(1);
           if(numCl == numClient)
            break;		   
         }
	    rs.close(); 
		st.close(); 
		conn.close();
    }
    catch(Exception sqlExcptn) {
       System.out.println(sqlExcptn);
    }
    return numEnreg;
  }

  //Trouve la cle d'un d'un enreg a partir de son numéro logique
  public static int trouverCleEnreg(int numLog) {
	int numEnreg = 0;
	int numCl = 0;
	String driver ="com.mysql.jdbc.Driver";
    String nomBD = "jdbc:mysql://localhost:3308/bdgsf?characterEncoding=latin1";
	try {
	     Class.forName(driver);
         Connection conn = DriverManager.getConnection(nomBD,nomUser,motP);
		 Statement st = conn.createStatement();  
         String sql = "SELECT * FROM Client ;";
	     ResultSet rs = st.executeQuery(sql);
	     while (rs.next()) {
	       numEnreg++;
           if(numEnreg == numLog) {
		     numCl = rs.getInt(1);
             break;	
           }			
         }
	    rs.close();
		st.close();
        conn.close();
    }
    catch(Exception sqlExcptn) {
            System.out.println(sqlExcptn);
    }
    return numCl;
  }
  
  public int nbTotalEnreg() {
	int nbTEnreg = 0;
	String driver ="com.mysql.jdbc.Driver";
    String nomBD = "jdbc:mysql://localhost:3308/bdgsf?characterEncoding=latin1";
	try {
	     Class.forName(driver);
         Connection conn = DriverManager.getConnection(nomBD,nomUser,motP);
		 Statement st = conn.createStatement();   
         String sql = "SELECT count(numCl) FROM Client ;";
	     ResultSet rs = st.executeQuery(sql);
	     if (rs.next()) {
	       nbTEnreg = rs.getInt(1);
         }
        rs.close(); 
		st.close(); 
		conn.close();
    }
    catch(Exception sqlExcptn) {
            System.out.println(sqlExcptn);
    } 
   return nbTEnreg;
  }  
  
  /* sens = -1 precedent
     sens = 0 premier ou dernier
	 sens = 1 suivant
  */
  public void chargerEnreg(int numClient, int sens) {
    int n = numeroEnreg(numClient) + sens;   
	int numCli = trouverCleEnreg(n);
	int numEnreg = 0;
	 String driver ="com.mysql.jdbc.Driver";
     String nomBD = "jdbc:mysql://localhost:3308/bdgsf?characterEncoding=latin1";
     try {
	       Class.forName(driver);
           Connection conn = DriverManager.getConnection(nomBD,nomUser,motP);
           Statement st = conn.createStatement();
           String sql = "SELECT * FROM Client WHERE numCl = " + numCli;
           ResultSet rs = st.executeQuery(sql);
		   if(rs.next()) {
			  textFieldNumCl.setText(String.valueOf(rs.getInt(1))); 
              textFieldNom.setText(rs.getString(2));
		   }
           rs.close(); 
		   st.close(); 
		   conn.close(); 
     }
     catch(Exception sqlExcptn) {
         System.out.println(sqlExcptn);
     }
  }
}  
  