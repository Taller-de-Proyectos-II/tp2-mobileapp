package com.example.mobileapp.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class AlertUpdateDTO implements Serializable {
    ArrayList<AlertDTO> alertAnswersDTO;

    int idAlert;

    public AlertUpdateDTO(ArrayList<AlertDTO> alerts, int idAlert) {
        this.alertAnswersDTO = alerts;
        this.idAlert = idAlert;
    }

    public AlertUpdateDTO() {
    }


    public ArrayList<AlertDTO> getAlertAnswersDTO() {
        return alertAnswersDTO;
    }

    public void setAlertAnswersDTO(ArrayList<AlertDTO> alertAnswersDTO) {
        this.alertAnswersDTO = alertAnswersDTO;
    }

    public int getIdAlert() {
        return idAlert;
    }

    public void setIdAlert(int idAlert) {
        this.idAlert = idAlert;
    }
}
