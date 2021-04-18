package com.example.mobileapp.Utils.Responses;

import com.example.mobileapp.Model.Schedule;

import java.util.ArrayList;

public class SchedulesResponse {

    private int status;
    private String message;

    ArrayList<Schedule> schedulesDTO;

    public SchedulesResponse(int status, String message, ArrayList<Schedule> schedulesDTO) {
        this.status = status;
        this.message = message;
        this.schedulesDTO = schedulesDTO;
    }

    public SchedulesResponse(){

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

    public ArrayList<Schedule> getSchedulesDTO() {
        return schedulesDTO;
    }

    public void setSchedulesDTO(ArrayList<Schedule> schedulesDTO) {
        this.schedulesDTO = schedulesDTO;
    }
}
