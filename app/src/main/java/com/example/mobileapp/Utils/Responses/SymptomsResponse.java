package com.example.mobileapp.Utils.Responses;

import com.example.mobileapp.Model.Symptom;

import java.util.ArrayList;

public class SymptomsResponse {

    int status;
    String message;
    ArrayList<Symptom> symptomsDTO;

    public SymptomsResponse(int status, String message, ArrayList<Symptom> symptomsDTO) {
        this.status = status;
        this.message = message;
        this.symptomsDTO = symptomsDTO;
    }

    public SymptomsResponse(){

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

    public ArrayList<Symptom> getSypmtoms() {
        return symptomsDTO;
    }

    public void setSypmtoms(ArrayList<Symptom> symptomsDTO) {
        this.symptomsDTO = symptomsDTO;
    }
}
