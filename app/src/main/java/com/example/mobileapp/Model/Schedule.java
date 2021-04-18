package com.example.mobileapp.Model;

import java.io.Serializable;

public class Schedule implements Serializable {

    private int idSchedule, hour, day;

    public Schedule(int idSchedule, int hour, int day) {
        this.idSchedule = idSchedule;
        this.hour = hour;
        this.day = day;
    }

    public Schedule(){

    }
    public int getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(int idSchedule) {
        this.idSchedule = idSchedule;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
