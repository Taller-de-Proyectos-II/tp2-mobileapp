package com.example.mobileapp.Utils.Responses;

public class LoginResponse {
    private int status;
    private String message;

    public LoginResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
