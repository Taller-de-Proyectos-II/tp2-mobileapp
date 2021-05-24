package com.example.mobileapp.Utils.Responses;

import com.example.mobileapp.Model.DTO.recomendationDTO;

import java.util.ArrayList;

public class RecomendationsResponse {

    int status;
    String message;
    ArrayList<recomendationDTO> recomendationsDTO;

    public RecomendationsResponse(int status, String message, ArrayList<recomendationDTO> recomendationsDTO) {
        this.status = status;
        this.message = message;
        this.recomendationsDTO = recomendationsDTO;
    }

    public RecomendationsResponse() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<recomendationDTO> getRecomendationsDTO() {
        return recomendationsDTO;
    }

    public void setRecomendationsDTO(ArrayList<recomendationDTO> recomendationsDTO) {
        this.recomendationsDTO = recomendationsDTO;
    }
}
