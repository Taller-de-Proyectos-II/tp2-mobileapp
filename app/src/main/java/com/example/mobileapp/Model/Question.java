package com.example.mobileapp.Model;

import java.io.Serializable;

public class Question implements Serializable {
    int idQuestion, idQuestionType;
    String description;

    public Question(int idQuestion, int idQuestionType, String description) {
        this.idQuestion = idQuestion;
        this.idQuestionType = idQuestionType;
        this.description = description;
    }

    public Question(){
        
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public int getIdQuestionType() {
        return idQuestionType;
    }

    public void setIdQuestionType(int idQuestionType) {
        this.idQuestionType = idQuestionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
