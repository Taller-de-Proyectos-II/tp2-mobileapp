package com.example.mobileapp.Utils.Responses;

import com.example.mobileapp.Model.Alert;

import java.util.ArrayList;

public class AlertResponse2 {

    int status;
    String message;
    ArrayList<Alert> alertsDTO;

    public AlertResponse2(int status, String message, ArrayList<Alert> alertsDTO) {
        this.status = status;
        this.message = message;
        this.alertsDTO = alertsDTO;
    }

    public AlertResponse2() {
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

    public ArrayList<Alert> getAlertsDTO() {
        return alertsDTO;
    }

    public void setAlertsDTO(ArrayList<Alert> alertsDTO) {
        this.alertsDTO = alertsDTO;
    }
}
