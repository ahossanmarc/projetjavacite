package com.example.controleurs;

import java.sql.Connection;
import java.sql.DriverManager;


public class connectionDB {

    public  Connection getConnections() {
        String dbName = "reservation";
        String dbUser = "root";
        String dbPass = "toor";
        String url = "jdbc:mysql://localhost/" + dbName;
        Connection dbLink = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            dbLink = DriverManager.getConnection(url, dbUser, dbPass);

        } catch (Exception e) {

            e.printStackTrace();
            e.getCause();
        }
        return dbLink;
    }

}
