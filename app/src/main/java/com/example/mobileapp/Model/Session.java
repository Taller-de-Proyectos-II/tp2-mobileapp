package com.example.mobileapp.Model;

public class Session {
    String date, patientDni, psychologistDni;
    int hour;

    public Session(String date, String patientDni, String psychologistDni, int hour) {
        this.date = date;
        this.patientDni = patientDni;
        this.psychologistDni = psychologistDni;
        this.hour = hour;
    }

    public Session(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPatientDni() {
        return patientDni;
    }

    public void setPatientDni(String patientDni) {
        this.patientDni = patientDni;
    }

    public String getPsychologistDni() {
        return psychologistDni;
    }

    public void setPsychologistDni(String psychologistDni) {
        this.psychologistDni = psychologistDni;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
