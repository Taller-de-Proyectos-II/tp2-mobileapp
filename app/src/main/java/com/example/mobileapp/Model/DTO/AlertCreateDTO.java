package com.example.mobileapp.Model.DTO;

import java.io.Serializable;

public class AlertCreateDTO implements Serializable {
    String patientDni;

    public AlertCreateDTO(String patientDni) {
        this.patientDni = patientDni;
    }

    public AlertCreateDTO(){

    }
    public String getPatientDni() {
        return patientDni;
    }

    public void setPatientDni(String patientDni) {
        this.patientDni = patientDni;
    }
}
