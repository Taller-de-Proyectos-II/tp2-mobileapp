package com.example.mobileapp.Model.DTO;

public class WorkExperienceDTO {

    int idWorkExperience;
    String place, occupation, description, workingDayType, startDate, endDate, psyChologistDni;
    Boolean current;


    public WorkExperienceDTO(int idWorkExperience, String place, String occupation, String description, String workingDayType, String startDate, String endDate, String psyChologistDni, Boolean current) {
        this.idWorkExperience = idWorkExperience;
        this.place = place;
        this.occupation = occupation;
        this.description = description;
        this.workingDayType = workingDayType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.psyChologistDni = psyChologistDni;
        this.current = current;
    }

    public WorkExperienceDTO() {
    }

    public int getIdWorkExperience() {
        return idWorkExperience;
    }

    public void setIdWorkExperience(int idWorkExperience) {
        this.idWorkExperience = idWorkExperience;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWorkingDayType() {
        return workingDayType;
    }

    public void setWorkingDayType(String workingDayType) {
        this.workingDayType = workingDayType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPsyChologistDni() {
        return psyChologistDni;
    }

    public void setPsyChologistDni(String psyChologistDni) {
        this.psyChologistDni = psyChologistDni;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }
}
