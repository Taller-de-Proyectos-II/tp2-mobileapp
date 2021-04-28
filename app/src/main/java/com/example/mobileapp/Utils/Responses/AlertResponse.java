package com.example.mobileapp.Utils.Responses;

import com.example.mobileapp.Model.Alert;

import java.util.ArrayList;

public class AlertResponse {

    int status;
    String message;
    Alert alertDTO;

    public AlertResponse(int status, String message, Alert alertsDTO) {
        this.status = status;
        this.message = message;
        this.alertDTO = alertsDTO;
    }

    public AlertResponse() {
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

    public Alert getAlertDTO() {
        return alertDTO;
    }

    public void setAlertsDTO(Alert alertsDTO) {
        this.alertDTO = alertsDTO;
    }
}
