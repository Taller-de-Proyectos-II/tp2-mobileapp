package com.example.mobileapp.Model;

import com.example.mobileapp.Model.DTO.AlertAnswerDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class Alert implements Serializable {

    int idAlert;
    String date;
    Boolean important;
    ArrayList<AlertAnswerDTO> alertAnswersDTO;


    public Alert(int idAlert, String date, Boolean important, ArrayList<AlertAnswerDTO> alertAnswersDTO) {
        this.idAlert = idAlert;
        this.date = date;
        this.important = important;
        this.alertAnswersDTO = alertAnswersDTO;
    }

    public Alert() {
    }

    public int getId() {
        return idAlert;
    }

    public void setId(int idAlert) {
        this.idAlert = idAlert;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getImportant() {
        return important;
    }

    public void setImportant(Boolean important) {
        this.important = important;
    }

    public ArrayList<AlertAnswerDTO> getAlertAnswersDTO() {
        return alertAnswersDTO;
    }

    public void setAlertAnswersDTO(ArrayList<AlertAnswerDTO> alertAnswersDTO) {
        this.alertAnswersDTO = alertAnswersDTO;
    }
}
