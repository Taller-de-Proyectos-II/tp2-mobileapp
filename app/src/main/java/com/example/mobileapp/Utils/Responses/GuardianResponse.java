package com.example.mobileapp.Utils.Responses;

import com.example.mobileapp.Model.Guardian;

import java.util.ArrayList;

public class GuardianResponse {
    private int status;
    private String message;
    private ArrayList<Guardian> guardiansDTO;

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

    public ArrayList<Guardian> getGuardiansDTO() {
        return guardiansDTO;
    }

    public void setGuardiansDTO(ArrayList<Guardian> guardiansDTO) {
        this.guardiansDTO = guardiansDTO;
    }
}
