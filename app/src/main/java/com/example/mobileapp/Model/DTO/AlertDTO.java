package com.example.mobileapp.Model.DTO;

import java.io.Serializable;

public class AlertDTO implements Serializable {
    int idAlertAnswer, score;


    public AlertDTO(int idAlertAnswer, int score) {
        this.idAlertAnswer = idAlertAnswer;
        this.score = score;
    }

    public AlertDTO() {
    }

    public int getId() {
        return idAlertAnswer;
    }

    public void setId(int idAlertAnswer) {
        this.idAlertAnswer = idAlertAnswer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
