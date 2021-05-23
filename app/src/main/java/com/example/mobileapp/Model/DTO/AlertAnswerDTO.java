package com.example.mobileapp.Model.DTO;

import java.io.Serializable;

public class AlertAnswerDTO implements Serializable {

    int idAnswer;
    int score;
    SymptomDTO symptomDTO;

    public AlertAnswerDTO(int idAnswer, int score, SymptomDTO symptomDTO) {
        this.idAnswer = idAnswer;
        this.score = score;
        this.symptomDTO = symptomDTO;
    }

    public AlertAnswerDTO() {
    }

    public int getId() {
        return idAnswer;
    }

    public void setId(int idAnswer) {
        this.idAnswer = idAnswer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public SymptomDTO getSymptomDTO() {
        return symptomDTO;
    }

    public void setSymptomDTO(SymptomDTO symptomDTO) {
        this.symptomDTO = symptomDTO;
    }
}
