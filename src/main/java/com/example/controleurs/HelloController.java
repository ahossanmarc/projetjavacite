package com.example.controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void clicButton() {

        connectionDB coDB =  new connectionDB();
        if(coDB.getConnections() != null){
            welcomeText.setText("Connexion OK");

        } else {
            welcomeText.setText("Connexion KO");
        }
        ;


    }
}