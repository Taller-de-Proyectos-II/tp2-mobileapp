package com.example.mobileapp.Utils.Responses;

import com.example.mobileapp.Model.DTO.StudiesDTO;

import java.util.ArrayList;

public class StudiesResponse {

    int status;
    String message;
    ArrayList<StudiesDTO> studiesDTO;

    public StudiesResponse(int status, String message, ArrayList<StudiesDTO> studiesDTO) {
        this.status = status;
        this.message = message;
        this.studiesDTO = studiesDTO;
    }

    public StudiesResponse() {
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

    public ArrayList<StudiesDTO> getStudiesDTO() {
        return studiesDTO;
    }

    public void setStudiesDTO(ArrayList<StudiesDTO> studiesDTO) {
        this.studiesDTO = studiesDTO;
    }
}
