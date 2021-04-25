package com.example.mobileapp.Utils.Responses;

import com.example.mobileapp.Model.Test;

import java.util.ArrayList;

public class TestResponse {

    int status;
    String message;
    ArrayList<Test> testsDTO;

    public TestResponse(int status, String message, ArrayList<Test> testsDTO) {
        this.status = status;
        this.message = message;
        this.testsDTO = testsDTO;
    }

    public TestResponse(){

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

    public ArrayList<Test> getTestsDTO() {
        return testsDTO;
    }

    public void setTestsDTO(ArrayList<Test> testsDTO) {
        this.testsDTO = testsDTO;
    }
}
