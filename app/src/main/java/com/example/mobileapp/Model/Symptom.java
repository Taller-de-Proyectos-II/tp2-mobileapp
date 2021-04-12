package com.example.mobileapp.Model;

public class Symptom {
    int id;
    String name, description;
    int idManifestation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdManifestation() {
        return idManifestation;
    }

    public void setIdManifestation(int idManifestation) {
        this.idManifestation = idManifestation;
    }
}
