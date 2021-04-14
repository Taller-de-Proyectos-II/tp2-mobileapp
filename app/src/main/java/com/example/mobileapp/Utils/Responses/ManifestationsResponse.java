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
    ArrayList<Manifestation> manifestationsDTO;
    Manifestation physical, cognitive, conductual, emotional;



    public Manifestation getPhysical() {
        return physical;
    }

    public void setPhysical(Manifestation physical) {
        this.physical = physical;
    }


    //HashMap<String, Manifestation> manifestationsDTO = new HashMap<>();

    public ManifestationsResponse(int status, String message, ArrayList<Manifestation> manifestationsDTO, Manifestation physical,Manifestation cognitive,Manifestation conductual,Manifestation emotional) {
        this.status = status;
        this.message = message;
        this.manifestationsDTO = manifestationsDTO;
        this.physical = physical;
        this.emotional = emotional;
        this.cognitive = cognitive;
        this.conductual = conductual;
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

    public ArrayList<Manifestation>  getManifestationsDTO() {
        return manifestationsDTO;
    }

    public void setManifestationsDTO(ArrayList<Manifestation> manifestationsDTO) {
        this.manifestationsDTO = manifestationsDTO;
    }

    public Manifestation getCognitive() {
        return cognitive;
    }

    public void setCognitive(Manifestation cognitive) {
        this.cognitive = cognitive;
    }

    public Manifestation getConductual() {
        return conductual;
    }

    public void setConductual(Manifestation conductual) {
        this.conductual = conductual;
    }

    public Manifestation getEmotional() {
        return emotional;
    }

    public void setEmotional(Manifestation emotional) {
        this.emotional = emotional;
    }
}
