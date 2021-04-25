package com.example.mobileapp.Model;

import java.util.ArrayList;

public class Test {
    int idTest;
    String startDate, endDate, diagnostic, testType;
    Boolean finished;
    ArrayList<Answer> answersDTO;


    public Test(int idTest, String startDate, String endDate, String diagnostic, String testType, Boolean finished, ArrayList<Answer> answersDTO) {
        this.idTest = idTest;
        this.startDate = startDate;
        this.endDate = endDate;
        this.diagnostic = diagnostic;
        this.testType = testType;
        this.finished = finished;
        this.answersDTO = answersDTO;
    }

    public Test(){

    }

    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public ArrayList<Answer> getAnswersDTO() {
        return answersDTO;
    }

    public void setAnswersDTO(ArrayList<Answer> answersDTO) {
        this.answersDTO = answersDTO;
    }
}
