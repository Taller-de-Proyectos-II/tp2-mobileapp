package com.example.mobileapp.Model;

import java.io.Serializable;

public class SymptomDTO implements Serializable {

    int idSymptom;
    String description;

    public SymptomDTO(int idSymptom, String description) {
        this.idSymptom = idSymptom;
        this.description = description;
    }

    public SymptomDTO(){

    }
    public int getIdSymptom() {
        return idSymptom;
    }

    public void setIdSymptom(int idSymptom) {
        this.idSymptom = idSymptom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
