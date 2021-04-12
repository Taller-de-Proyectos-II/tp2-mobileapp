package com.example.mobileapp.Model;

import java.io.Serializable;

public class Psychologist implements Serializable {
    String names, lastNames, phone, email, description, birthday, cpsp;

    public Psychologist(String names, String lastNames, String phone, String email, String description, String birthday, String cpsp) {
        this.names = names;
        this.lastNames = lastNames;
        this.phone = phone;
        this.email = email;
        this.description = description;
        this.birthday = birthday;
        this.cpsp = cpsp;
    }

    public Psychologist(){

    }
    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCpsp() {
        return cpsp;
    }

    public void setCpsp(String cpsp) {
        this.cpsp = cpsp;
    }
}
