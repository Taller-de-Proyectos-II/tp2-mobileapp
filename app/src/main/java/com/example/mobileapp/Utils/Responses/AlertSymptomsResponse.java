package com.example.mobileapp.Utils.Responses;

import com.example.mobileapp.Model.DTO.SymptomDTO;

import java.util.ArrayList;

public class AlertSymptomsResponse {

    int status;
    String message;
    ArrayList<SymptomDTO> symptomsDTO;


    public AlertSymptomsResponse(int status, String message, ArrayList<SymptomDTO> symptomsDTO) {
        this.status = status;
        this.message = message;
        this.symptomsDTO = symptomsDTO;
    }

    public AlertSymptomsResponse() {
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

    public ArrayList<SymptomDTO> getSymptomsDTO() {
        return symptomsDTO;
    }

    public void setSymptomsDTO(ArrayList<SymptomDTO> symptomsDTO) {
        this.symptomsDTO = symptomsDTO;
    }
}
