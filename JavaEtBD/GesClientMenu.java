import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; 
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import java.sql.*;

public class GesClientMenu extends Application {
  static String nomUser = new String("root");
  static String motP = new String("");
  Label labelNumCl = new Label("Numéro :");
  TextField textFieldNumCl = new TextField();
  Label labelNom = new Label("Nom :");
  TextField textFieldNom = new TextField();
  Label labelPhoto = new Label("Photo :");
  TextField textFieldPhoto = new TextField();
  
  MenuBar menuBar = new MenuBar();
  Menu menuNavigation = new Menu("Navigation");
	
  MenuItem menuNPremier = new MenuItem("Premier");
  MenuItem menuNPrecedent = new MenuItem("Précédent");
  MenuItem menuNSuivant = new MenuItem("Suivant");
  MenuItem menuNDernier = new MenuItem("Dernier");
	
  Group root = new Group();
	
  public static void main(String[] args) {
    launch(GesClientMenu.class, args);
  }
  
  @Override
  public void start(Stage stage) {
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
	
	labelPhoto.setLayoutX(15);
    labelPhoto.setLayoutY(100);
	textFieldPhoto.setLayoutX(80);
    textFieldPhoto.setLayoutY(100);
 
	menuBar.getMenus().add(menuNavigation);
    
    menuNavigation.getItems().add(menuNPremier);
	menuNavigation.getItems().add(menuNPrecedent);
	menuNavigation.getItems().add(menuNSuivant);
	menuNavigation.getItems().add(menuNDernier);
	
	menuNPremier.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            int numClient = 1;
		    chargerEnreg(numClient, 0);
          }
    });
	menuNPrecedent.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            int numClient = Integer.valueOf(textFieldNumCl.getText()).intValue();
		    chargerEnreg(numClient, -1);
          }
    });
	menuNSuivant.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            int numClient = Integer.valueOf(textFieldNumCl.getText()).intValue();
		    chargerEnreg(numClient, 1);
          }
    });
	menuNDernier.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            int numClient = nbTotalEnreg();
		    chargerEnreg(numClient, 0);
          }
    });
	
    Button boutonPremier = new Button("Premier");
	boutonPremier.setLayoutX(45);
    boutonPremier.setLayoutY(140);
	Button boutonPrecedent = new Button("Précédent");
	boutonPrecedent.setLayoutX(115);
    boutonPrecedent.setLayoutY(140);
	Button boutonSuivant = new Button("Suivant");
	boutonSuivant.setLayoutX(200);
    boutonSuivant.setLayoutY(140);
	Button boutonDernier = new Button("Dernier");
	boutonDernier.setLayoutX(290);
    boutonDernier.setLayoutY(140);
	
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
	root.getChildren().add(labelPhoto);
	root.getChildren().add(textFieldPhoto);
	
	root.getChildren().add(boutonPremier);
	root.getChildren().add(boutonPrecedent);
	root.getChildren().add(boutonSuivant);
	root.getChildren().add(boutonDernier);
	
	root.getChildren().add(menuBar);
	
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
  
  public int ajouter(int numClient, String nom, String adresse, 
    int tel, int fax, String email, String photo) {
    int nb = 0;
	String driver ="com.mysql.jdbc.Driver";
    String nomBD = "jdbc:mysql://localhost:3308/bdgsf?characterEncoding=latin1";
    try {Class.forName(driver);
         Connection conn = DriverManager.getConnection(nomBD,nomUser,motP);
         Statement st = conn.createStatement();  
         String sql = "INSERT INTO Client VALUES(" ;
	     sql = sql + numClient + ",'" + nom + "','" + adresse + "'," + tel + ",";
	     sql = sql + fax + ",'" + email + "','" + photo + "');"; 
         nb = st.executeUpdate(sql);
         st.close();
         conn.close();
    }
    catch(Exception sqlExcptn) {
         System.out.println(sqlExcptn);
    }
   return nb;
  }
  
  public int supprimer(int numClient) {
    int nb = 0;
    String driver ="com.mysql.jdbc.Driver";
    String nomBD = "jdbc:mysql://localhost:3308/bdgsf?characterEncoding=latin1";
    try {Class.forName(driver);
         Connection conn = DriverManager.getConnection(nomBD,nomUser,motP);
         Statement st = conn.createStatement();    
	     String sql = "DELETE FROM Client WHERE numCl = " + numClient;
         nb = st.executeUpdate(sql);
         st.close();
         conn.close();
    }
    catch(Exception sqlExcptn) {
         System.out.println(sqlExcptn);
    }
   return nb;
  }
  
  public int modifier(int numCl, String nom, String adresse, 
   int tel, int fax, String email, String photo) {
    int nb = 0;
    String driver ="com.mysql.jdbc.Driver";
    String nomBD = "jdbc:mysql://localhost:3308/bdgsf?characterEncoding=latin1";
    try {Class.forName(driver);
         Connection conn = DriverManager.getConnection(nomBD,nomUser,motP);
         Statement st = conn.createStatement();  
	     String sql = "UPDATE Client SET " ;
	     sql = sql + "numCl=" + numCl + ", nom='" + nom + "',";
	     sql = sql + " adresse='" + adresse + "', tel=" + tel + ",";
	     sql = sql + " fax=" + fax  + ", email='" + email + "'";
	     sql = sql + ", photo='" + photo  + "'";
	     sql = sql + " WHERE numCl=" + numCl + ";";
         nb = st.executeUpdate(sql);
         st.close();
         conn.close();
    }
    catch(Exception sqlExcptn) {
         System.out.println(sqlExcptn);
    }
   return nb;
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
			  textFieldPhoto.setText(rs.getString(7));
			  String cheminImage = ".//images//";
			  FileInputStream imageURI = null;
			  try {
                  imageURI = new FileInputStream(cheminImage + rs.getString(7));
			  }
              catch (FileNotFoundException e) {	//image par défaut		  
			    imageURI = new FileInputStream(cheminImage + "INP.jpg");
			  }	
              Image imagePhoto = new Image(imageURI);
			  ImageView imageView = new ImageView(imagePhoto);
			  imageView.setX(250); //Positions de l'image
              imageView.setY(40);  
              imageView.setFitHeight(90); //Hauteur et lageur
              imageView.setFitWidth(90);  
              //imageView.setPreserveRatio(true);  //preserve le  ratio de l'image view
			  root.getChildren().add(imageView);
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
  