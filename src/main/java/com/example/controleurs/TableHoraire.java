package com.example.controleurs;

public class TableHoraire {

    private int id;
    private String details;
    public TableHoraire(int id,String details){
        this.id= id;
        this.details= details;

    }

    public int getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setId(int id) {
        this.id = id;
    }
}

