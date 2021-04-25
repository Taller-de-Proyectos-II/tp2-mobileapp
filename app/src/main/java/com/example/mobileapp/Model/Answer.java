package com.example.mobileapp.Model;

public class Answer {

    int idAnswer, score;
    Question questionDTO;

    public Answer(int idAnswer, int score, Question questionDTO) {
        this.idAnswer = idAnswer;
        this.score = score;
        this.questionDTO = questionDTO;
    }

    public Answer(){

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

    public Question getQuestionDTO() {
        return questionDTO;
    }

    public void setQuestionDTO(Question questionDTO) {
        this.questionDTO = questionDTO;
    }
}
