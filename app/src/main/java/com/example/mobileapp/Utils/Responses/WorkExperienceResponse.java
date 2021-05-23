package com.example.mobileapp.Utils.Responses;

import com.example.mobileapp.Model.DTO.WorkExperienceDTO;

import java.util.ArrayList;

public class WorkExperienceResponse {

    int status;
    String message;
    ArrayList<WorkExperienceDTO> workExperiencesDTO;

    public WorkExperienceResponse(int status, String message, ArrayList<WorkExperienceDTO> workExperiencesDTO) {
        this.status = status;
        this.message = message;
        this.workExperiencesDTO = workExperiencesDTO;
    }

    public WorkExperienceResponse() {
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

    public ArrayList<WorkExperienceDTO> getWorkExperiencesDTO() {
        return workExperiencesDTO;
    }

    public void setWorkExperiencesDTO(ArrayList<WorkExperienceDTO> workExperiencesDTO) {
        this.workExperiencesDTO = workExperiencesDTO;
    }
}
