package com.example.controleurs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController  implements Initializable {
    private Stage stage;
    private Parent root;

    @FXML
    private Button btnCon;

    @FXML
    private PasswordField tMDP;
    @FXML
    private TextField tMail;

    @FXML
    private Label id_connexion;
    @FXML
    private Label tstatutCon;

    @FXML
    protected void connexionButton( ActionEvent event) {
        String sMail = tMail.getText();
        String sMDP = tMDP.getText();

        String sqlSelect = " select * from  utilisateur  where email='"+sMail+"' and mot_de_passe='"+sMDP+"'";
        //String sNom = tNom.getText();
        //String sqlValeurs =  sNom+"','"+sMail+"','"+sMDP+"')";
        connectionDB coDB =  new connectionDB();
        //System.out.println(sqlInsertion+sqlValeurs);
        try {
            Connection con  =  coDB.getConnections();
            if(coDB.getConnections() != null){
                Statement stmt  = con.createStatement();
                ResultSet qr = stmt.executeQuery(sqlSelect);
                if(qr.next()){ // si on retrouve une valeur dans le resultat de la table

                    tstatutCon.setText("Connexion réussie")  ;
                    //id_connexion.setText(qr.getString("id_utilisateur"));
                    
                    Session.id_user = qr.getInt("id_utilisateur");
                    //recuperation de l'id de l'utilisateur connecté

                   /* FXMLLoader loader = FXMLLoader.load(getClass().getResource("Reservation.fxml"));
                    root = loader.load();
                    ReservationController scene2con = loader.getController();*/


                    Parent root = FXMLLoader.load(getClass().getResource("/com/example/vues/Reservation.fxml"));

                    //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
                    stage = (Stage)((Node) event.getSource()).getScene().getWindow() ;
                    Scene scene = new Scene(root, 750, 400);
                    stage.setTitle("Réservation");
                    stage.setScene(scene);
                    stage.show();

                } else{
                    tstatutCon.setText("Connexion échouée") ;
                }


            } else {
                tstatutCon.setText("Erreur BD");
            }

        }catch (Exception e){

            StringWriter st = new StringWriter();

            e.printStackTrace(new PrintWriter(st));
            String stackTraceStr = st.toString();

            if (stackTraceStr.contains("Duplicate entry" )){
                tstatutCon.setText("Mail existant");}
            else {

                e.printStackTrace();
            }




        }

        ;


    }

    @FXML
    protected void inscriptionScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/vues/Register.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow() ;
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
}

public  String getUserConnecte(){
  return   id_connexion.getText();

}
    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
    @FXML
    protected void verifMail()  {
        if (!isValidEmail(tMail.getText())){
            btnCon.setDisable(true);

        } else { btnCon.setDisable(false);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnCon.setDisable(true);
        tMail.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidEmail(newValue)) {
                tMail.setStyle("-fx-border-color: red;");
                //btnCon.setDisable(false);
            } else {
                tMail.setStyle("");
                //btnCon.setDisable(true);
            }
        });

    }
}