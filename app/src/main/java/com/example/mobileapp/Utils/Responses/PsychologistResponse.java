package com.example.mobileapp.Utils.Responses;

import com.example.mobileapp.Model.Psychologist;

import java.util.ArrayList;

public class PsychologistResponse {
    int status;
    String message;
    ArrayList<Psychologist> psychologistsDTO;

    public PsychologistResponse(int status, String message, ArrayList<Psychologist> psychologistsDTO) {
        this.status = status;
        this.message = message;
        this.psychologistsDTO = psychologistsDTO;
    }

    public PsychologistResponse()
    {

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

    public ArrayList<Psychologist> getPsychologistsDTO() {
        return psychologistsDTO;
    }

    public void setPsychologistsDTO(ArrayList<Psychologist> psychologistsDTO) {
        this.psychologistsDTO = psychologistsDTO;
    }
}
