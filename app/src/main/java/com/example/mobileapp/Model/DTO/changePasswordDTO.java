package com.example.mobileapp.Model.DTO;

public class changePasswordDTO {

    String dni, newPassword, password;

    public changePasswordDTO(String dni, String newPassword, String password) {
        this.dni = dni;
        this.newPassword = newPassword;
        this.password = password;
    }

    public changePasswordDTO() {
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
