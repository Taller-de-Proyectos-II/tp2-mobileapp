package com.example.mobileapp.Model;

import java.io.Serializable;

public class User implements IUser, Serializable {
    private String dni,password;

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String dni, String password) {
        this.dni = dni;
        this.password = password;
    }

    public User() {

    }
    @Override
    public String getDNI() {
        return dni;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
