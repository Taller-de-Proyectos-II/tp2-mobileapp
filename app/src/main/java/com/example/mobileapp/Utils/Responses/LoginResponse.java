package com.example.mobileapp.Utils.Responses;

public class LoginResponse {
    private int codigo;
    private String message;

    public LoginResponse(int codigo, String message) {
        this.codigo = codigo;
        this.message = message;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getMessage() {
        return message;
    }
}
