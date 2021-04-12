package com.example.mobileapp.Utils.Responses;

import com.example.mobileapp.Model.Manifestation;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class ManifestationsResponse {

    private int status;
    private String message;
    HashMap<String, Manifestation> manifestationsDTO = new HashMap<>();

    public ManifestationsResponse(int status, String message, HashMap<String, Manifestation> manifestationsDTO) {
        this.status = status;
        this.message = message;
        this.manifestationsDTO = manifestationsDTO;
    }

    public ManifestationsResponse(){

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

    public HashMap<String, Manifestation>  getManifestationsDTO() {
        return manifestationsDTO;
    }

    public void setManifestationsDTO(HashMap<String, Manifestation> manifestationsDTO) {
        this.manifestationsDTO = manifestationsDTO;
    }
}
