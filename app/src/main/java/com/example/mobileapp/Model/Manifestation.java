package com.example.mobileapp.Model;

import java.util.ArrayList;
import java.util.List;

public class Manifestation {
    int id;
    String name, description;
    ArrayList<Symptom> Symptoms;

    public Manifestation(int id, String name, String description, ArrayList<Symptom> symptoms) {
        this.id = id;
        this.name = name;
        this.description = description;
        Symptoms = symptoms;
    }
    public Manifestation()
    {

    }

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

    public ArrayList<Symptom> getSymptoms() {
        return Symptoms;
    }

    public void setSymptoms(ArrayList<Symptom> symptoms) {
        Symptoms = symptoms;
    }
}
