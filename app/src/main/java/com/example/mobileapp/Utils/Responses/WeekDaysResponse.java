package com.example.mobileapp.Utils.Responses;

import java.util.ArrayList;

public class WeekDaysResponse {

    int status;
    String message;

    ArrayList<String> days;

    public WeekDaysResponse(int status, String message, ArrayList<String> days) {
        this.status = status;
        this.message = message;
        this.days = days;
    }

    public WeekDaysResponse() {
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

    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }
}
