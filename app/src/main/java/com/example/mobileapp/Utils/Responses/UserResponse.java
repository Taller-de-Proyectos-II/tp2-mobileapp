package com.example.mobileapp.Utils.Responses;

import com.example.mobileapp.Model.Patient;

public class UserResponse {

    private int status;
    private String message;
    private Patient patientDTO;

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

    public Patient getPatient() {
        return patientDTO;
    }

    public void setPatient(Patient patientDTO) {
        this.patientDTO = patientDTO;
    }
}
