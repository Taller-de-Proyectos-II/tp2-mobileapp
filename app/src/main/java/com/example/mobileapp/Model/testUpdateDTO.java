package com.example.mobileapp.Model;

import java.util.ArrayList;

public class testUpdateDTO {
    ArrayList<AnswerDTO> answersDTO;
    int idTest;

    public testUpdateDTO(ArrayList<AnswerDTO> answersDTO, int idTest) {
        this.answersDTO = answersDTO;
        this.idTest = idTest;
    }

    public testUpdateDTO(){

    }

    public ArrayList<AnswerDTO> getAnswersDTO() {
        return answersDTO;
    }

    public void setAnswersDTO(ArrayList<AnswerDTO> answersDTO) {
        this.answersDTO = answersDTO;
    }

    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }
}
