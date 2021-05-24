package com.example.mobileapp.Model.DTO;

public class recomendationDTO {

    int idRecomendation;
    String description;

    public recomendationDTO(int idRecomendation, String description) {
        this.idRecomendation = idRecomendation;
        this.description = description;
    }

    public recomendationDTO() {
    }

    public int getIdRecomendation() {
        return idRecomendation;
    }

    public void setIdRecomendation(int idRecomendation) {
        this.idRecomendation = idRecomendation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
