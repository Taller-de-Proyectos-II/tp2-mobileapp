package com.example.mobileapp.Model;

public class AnswerDTO {
    int idAnswer;
    int score;


    public AnswerDTO(int idAnswer, int score) {
        this.idAnswer = idAnswer;
        this.score = score;
    }

    public AnswerDTO(){

    }

    public int getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(int idAnswer) {
        this.idAnswer = idAnswer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
