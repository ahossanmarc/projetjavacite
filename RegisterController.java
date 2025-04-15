package com.example.controleurs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;


import java.io.IOException;
import java.io.PrintWriter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.StringWriter;
import java.net.URL;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    private Stage stage;
   // private Scene scene ;
    //private FXMLLoader fxmlLoader;

    @FXML
    private Button btnIns;
    @FXML
    private Label welcomeText;
    @FXML
    private TextField tNom;
    @FXML
    private PasswordField tMDP;
    @FXML
    private TextField tMailIns;
    @FXML
    private Label tstatutIns;

    @FXML
    protected void inscriptionButton(ActionEvent event) {
        String sqlInsertion = "insert into utilisateur (nom,email,mot_de_passe) values('";//','','')";
        String sNom = tNom.getText();
        String sMail = tMailIns.getText();
        String sMDP = tMDP.getText();
        String sqlValeurs =  sNom+"','"+sMail+"','"+sMDP+"')";
        connectionDB coDB =  new connectionDB();
        //System.out.println(sqlInsertion+sqlValeurs);
        try {
            Connection con  =  coDB.getConnections();
            if(coDB.getConnections() != null){
                Statement stmt  = con.createStatement();
                stmt.executeUpdate(sqlInsertion+sqlValeurs);

                tstatutIns.setText("Inscription réussie");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText("Inscription réussie");
                alert.showAndWait();
                Parent root = FXMLLoader.load(getClass().getResource("/com/example/vues/Login.fxml"));
                //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
                stage = (Stage)((Node) event.getSource()).getScene().getWindow() ;
                Scene scene = new Scene(root, 600, 400);
                stage.setTitle("Hello!");
                stage.setScene(scene);
                stage.show();



            } else {
                tstatutIns.setText("Inscription échouée");

            }

        }catch (Exception e){

            StringWriter st = new StringWriter();

            e.printStackTrace(new PrintWriter(st));
            String stackTraceStr = st.toString();

            if (stackTraceStr.contains("Duplicate entry" )){

                tstatutIns.setText("Mail existant");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Attention");
                alert.setContentText("Mail existant");
                alert.showAndWait();


            }
            else {

                e.printStackTrace();
            }




        }

        ;


    }

    @FXML
    protected void verifMail()  {
        if (!isValidEmail(tMailIns.getText())){
            btnIns.setDisable(true);

        } else { btnIns.setDisable(false);
        }
    }
    @FXML
    protected void connexionScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/vues/Login.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow() ;
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();



    }


    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnIns.setDisable(true) ;
        tMailIns.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidEmail(newValue)) {
                tMailIns.setStyle("-fx-border-color: red;");
                //btnIns.setDisable(false);
            } else {
                tMailIns.setStyle(""); // reset style
                //btnIns.setDisable(true);
            }
        });

    }

}
