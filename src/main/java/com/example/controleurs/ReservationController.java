package com.example.controleurs;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
//import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;


import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.*;
//import com.itextpdf.kernel.* ;
//import java.time.format.DateTimeFormatter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;


public class ReservationController  implements Initializable{
    private Pane ecranRes;
    private Stage stage;

    @FXML
    private ComboBox<String> comboService ;
@FXML
private ListView<String>  list;

@FXML
private Label msg;

    @FXML
    private Label tstatutCon;
    @FXML
    private DatePicker date_res;

    @FXML
    private TextField heure_res;

    @FXML
    private Button btnReserver;
    @FXML
    private TextField minutes_res;
    @FXML
    protected void reserverAction() {
        //String sMail = tMail.getText();
        //String sMDP = tMDP.getText();
        //choixService;


        //String sqlSelect = "select * from typeservice;";
        String id_pdf="";
        //String sNom = tNom.getText();
        //String sqlValeurs =  sNom+"','"+sMail+"','"+sMDP+"')";
        Connection con_idpdf =  new connectionDB().getConnections();
        //System.out.println(sqlInsertion+sqlValeurs);
        try {
            System.out.println(list.getSelectionModel().getSelectedItems().getFirst());
            System.out.println(Session.id_user);
            System.out.println(date_res.getValue().toString());
            System.out.println(heure_res.getText());
            System.out.println(minutes_res.getText());
            String sqlajoutidpdf = "select max(id_billet)+1 as max_idbill from billet ;";

            Statement stmt_idpdf  = con_idpdf.createStatement();
            ResultSet qr = stmt_idpdf.executeQuery(sqlajoutidpdf);
            if(qr.next()){
                id_pdf= qr.getString("max_idbill");

            }
            // creation du pdf
            String path="billet_"+id_pdf+".pdf";
            PdfWriter pdf = new PdfWriter(path);
            PdfDocument pdfDoc = new PdfDocument(pdf);
            pdfDoc.addNewPage();
            String info ="Vous avez choisi " +comboService.getValue()+" ==> " +
            list.getSelectionModel().getSelectedItems().getFirst()+"\n"+ " Date : "+
                    //Session.id_user+"\n"+
                    date_res.getValue().toString()+"\n"+ "Heure : "+
                    heure_res.getText()+" h :"+minutes_res.getText()+" min" ;
            Paragraph  par1 = new Paragraph(info) ;

            Document Doc = new Document(pdfDoc);
            Doc.add(par1);
            Doc.close();
//---------------------------------------------------
            //System.out.println(id_pdf);

            // insertion dans la table reservation
            String sqlInsert = "insert into reservation (id_utilisateur,id_horaire ,date_reservation) values (?,?,?)";
            connectionDB coDB = new connectionDB();
            Connection con  =  coDB.getConnections();
            //System.out.println(Date.valueOf(date_res.getValue().toString())+ " "+heure_res.getText()+":"+minutes_res.getText()+":00");

           if(coDB.getConnections() != null){
                PreparedStatement stmt  = con.prepareStatement(sqlInsert);
                stmt.setInt(1,Session.id_user);
                stmt.setInt(2,id_horaire);


                LocalDate date = date_res.getValue();         // du DatePicker
               DateTimeFormatter parser = new DateTimeFormatterBuilder()
                       .appendValue(ChronoField.HOUR_OF_DAY)
                       .appendLiteral(':')
                       .appendValue(ChronoField.MINUTE_OF_HOUR)
                       .toFormatter();
               LocalTime heure = LocalTime.parse(heure_res.getText()+":"+minutes_res.getText(), parser);
                //LocalTime heure = LocalTime.parse(heure_res.getText()+":"+minutes_res.getText());     // ou depuis ComboBox
               // Text '7:30' could not be parsed at index 0


                LocalDateTime dateTime = LocalDateTime.of(date, heure);
                stmt.setTimestamp(3,Timestamp.valueOf(dateTime));
                //stmt.setTimestamp(3,Date.valueOf(date_res.getValue().toString())+ " "+heure_res.getText()+":"+minutes_res.getText()+":00");
                //stmt.setDate(3,  Date.valueOf(date_res.getValue().toString()+ " "+heure_res.getText()+":"+minutes_res.getText())) ;
                stmt.executeUpdate();
                //-------------------------------
               //insertion dans la table des billets

               String sqlInsertBillet = "insert into billet (id_reservation,chemin_pdf ,date_generation) values (?,?,?)";
                stmt = con.prepareStatement(sqlInsertBillet);
                stmt.setInt(1,Integer.parseInt(id_pdf));
               stmt.setString(2,"Billet_"+id_pdf+".pdf");
               stmt.setTimestamp(3, Timestamp.from(Instant.now()));
               stmt.executeUpdate();
               //----------------------------------


               //ResultSet qr = stmt.executeQuery(sqlSelect);
                //qr.
               // choixService.setValue(qr.next().getString("nom"));
                //Time.valueOf(heure_res.getText()+":"+minutes_res.getText())
                System.out.println("insertion ok");
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Information");
               alert.setContentText("Inscription réussie");
               alert.showAndWait();

               FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vues/Reservation.fxml"));
               Parent root = loader.load();

               // Assuming 'myPane' is a placeholder in your current scene:
               minutes_res.setText("");
               heure_res.setText("");
               date_res.setValue(null);


           } else {
                tstatutCon.setText("Erreur BD");
            }

        }
        catch (NoSuchElementException e){

            msg.setText("Choisissez un élément dans la liste svp");
        }
        catch (Exception e){

            StringWriter st = new StringWriter();

            e.printStackTrace(new PrintWriter(st));
            String stackTraceStr = st.toString();

            if (stackTraceStr.contains("Duplicate entry" )){
                tstatutCon.setText("Mail existant");}
            else {

                e.printStackTrace();
            }




        }




    }

    private  List <TableHoraire> tHoraire = new ArrayList<>();
    @FXML
    protected void choixTypeService() {
        //String sMail = tMail.getText();
        //String sMDP = tMDP.getText();
        //choixService;


        //String sNom = tNom.getText();
        //String sqlValeurs =  sNom+"','"+sMail+"','"+sMDP+"')";
        connectionDB coDB =  new connectionDB();
        list.getItems().clear();
        //System.out.println(sqlInsertion+sqlValeurs);
        try {

            String  service_selected =  comboService.getValue();
            String sqlSelect = "select ho.id_horaire,type.nom nom ,ho.details  from typeservice " +
                    " type join horaire ho on ho.id_type = type.id_type where" +
                    " type.nom='"+ service_selected+"'";

            Connection con  =  coDB.getConnections();
            if(coDB.getConnections() != null){
                Statement stmt  = con.createStatement();
                ResultSet qr = stmt.executeQuery(sqlSelect);
                while(qr.next()){
                    String detail = qr.getString("details");
                    int id = qr.getInt("id_horaire");
                    TableHoraire ligneHoraire = new TableHoraire(id,detail);

                    tHoraire.add(ligneHoraire);
                    //choixService.getItems().add(type)  ;
                    list.getItems().add(detail) ;
                }


            } else {
                tstatutCon.setText("Erreur BD");
            }


        }

        catch ( NullPointerException e){
            System.out.println("selection vide");

        }
        catch (Exception e){

            StringWriter st = new StringWriter();

            e.printStackTrace(new PrintWriter(st));
            String stackTraceStr = st.toString();

            if (stackTraceStr.contains("Duplicate entry" )){
                tstatutCon.setText("Mail existant");}
            else {

                e.printStackTrace();
            }




        }




    }

    private int id_horaire ;

    @FXML
    protected void verifBtnReserver(){
        try {
            //System.out.println((date_res.getValue().toString()));
            //System.out.println(heure_res.getText());
            //System.out.println(minutes_res.getText());
            //System.out.println(date_res.getValue().toString());
            if (
                    Integer.parseInt(heure_res.getText()) < 0 || Integer.parseInt(heure_res.getText()) > 24 ||
                    Integer.parseInt(minutes_res.getText()) < 0 || Integer.parseInt(minutes_res.getText()) > 60

            ) {
                btnReserver.setDisable(true);
                //list.getSelectionModel().getSelectedItems().getFirst()

            } else {
                btnReserver.setDisable(false);

            }
        } catch (NullPointerException | NoSuchElementException e) {
            btnReserver.setDisable(true);
        }
        catch (NumberFormatException e) { // au cas de les nombres ( minutes et heures ) sont vides
            btnReserver.setDisable(true);
        }
    }
    @FXML
    protected void clicListe() {
        //String sMail = tMail.getText();
        //String sMDP = tMDP.getText();
        //choixService;
        String ligne_select = list.getSelectionModel().getSelectedItems().getFirst();
        for (TableHoraire t: tHoraire){
            if(t.getDetails().equals(ligne_select)){
                id_horaire = t.getId();
                System.out.println(t.getId());
            }

        }
        System.out.println(list.getSelectionModel().getSelectedItems().getFirst());

        //NoSuchElementException
        /*List <TableHoraire> tHoraire = new ArrayList<>() ;
        //String sNom = tNom.getText();
        //String sqlValeurs =  sNom+"','"+sMail+"','"+sMDP+"')";
        connectionDB coDB =  new connectionDB();
        list.getItems().clear();
        //System.out.println(sqlInsertion+sqlValeurs);
        try {

            String  service_selected =  comboService.getValue();
            String sqlSelect = "select ho.id_horaire,type.nom nom ,ho.details  from typeservice " +
                    " type join horaire ho on ho.id_type = type.id_type where" +
                    " type.nom='"+ service_selected+"'";

            Connection con  =  coDB.getConnections();
            if(coDB.getConnections() != null){
                Statement stmt  = con.createStatement();
                ResultSet qr = stmt.executeQuery(sqlSelect);
                while(qr.next()){
                    String detail = qr.getString("details");
                    int id = qr.getInt("id_horaire");
                    TableHoraire ligneHoraire = new TableHoraire(id,detail);

                    tHoraire.add(ligneHoraire);
                    //choixService.getItems().add(type)  ;
                    list.getItems().add(detail) ;
                }


            } else {
                tstatutCon.setText("Erreur BD");
            }


        }

        catch ( NullPointerException e){
            System.out.println("selection vide");

        }
        catch (Exception e){

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
*/

    }

   /* @FXML
    protected void inscriptionScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow() ;
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
}
*/
    public boolean isValidDate(String email) {
        String emailRegex = "^\\d{2}/\\d{2}/\\d{4}$";
        return email.matches(emailRegex);
    }

    @FXML
    protected void verifDate()   {

        if(!isValidDate(date_res.getValue().toString())) {

            btnReserver.setDisable(true);
        }else {

            btnReserver.setDisable(false);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            //String sMail = tMail.getText();
            //String sMDP = tMDP.getText();
            //choixService;

        try {

            if (!isValidDate(date_res.getValue().toString())){
                btnReserver.setDisable(true);
            } else {
                btnReserver.setDisable(false);
                //date_res.setv

            }
        } catch (NullPointerException e){}
        heure_res.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                heure_res.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });// <>

        // <>
        //String regex = "^\\d{2}/\\d{2}/\\d{4}$";

        minutes_res.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                minutes_res.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

//ajouter des contrainte ssur la date

            String sqlSelect = "select * from typeservice;";
            //String sNom = tNom.getText();
            //String sqlValeurs =  sNom+"','"+sMail+"','"+sMDP+"')";
            connectionDB coDB =  new connectionDB();
            //System.out.println(sqlInsertion+sqlValeurs);
            try {
                Connection con  =  coDB.getConnections();
                if(coDB.getConnections() != null){
                    Statement stmt  = con.createStatement();
                    ResultSet qr = stmt.executeQuery(sqlSelect);
                    //qr.
                    // choixService.setValue(qr.next().getString("nom"));
                    while(qr.next()){
                        String type = qr.getString("nom");
                        //choixService.getItems().add(type)  ;
                        comboService.getItems().add(type);
                        //list.getItems().add(type) ;
                        //choixService.
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




        }


    @FXML
    protected void verifHeure() {

        try {
            if(Integer.parseInt(heure_res.getText()) <0  || Integer.parseInt(heure_res.getText()) >24){
                msg.setText("Entrez une heure valide");
                //btnReserver.setDisable(true);
            }
            else {
                msg.setText("");
                btnReserver.setDisable(false);
               /* if (Integer.parseInt(heure_res.getText()) <10){
                    heure_res.setText("0"+heure_res.getText());
                }*/
            }
        }
        catch (NumberFormatException e) {
            System.out.println("heure vide");
        }



    }

    @FXML
    protected void verifMinutes() {

        try {
            if(Integer.parseInt(minutes_res.getText()) <0  || Integer.parseInt(minutes_res.getText()) >60){
                msg.setText("Entrez des minutes valide");
                //btnReserver.setDisable(true);
            } else {
                msg.setText("");
                btnReserver.setDisable(false);

               /* if (Integer.parseInt(heure_res.getText()) <10){
                    minutes_res.setText("0"+minutes_res.getText());
                }*/
            }

        }
        catch (NumberFormatException e) {
            System.out.println("heure vide");
        }



    }

}
