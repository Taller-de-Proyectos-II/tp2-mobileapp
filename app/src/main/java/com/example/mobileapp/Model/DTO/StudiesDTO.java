package com.example.mobileapp.Model.DTO;

public class StudiesDTO {
    int idStudy;
    String academicDiscipline, title, startDate, endDate, description, studyCenter, psychologistDni;
    Boolean complete;

    public StudiesDTO(int idStudy, String academicDiscipline, String title, String startDate, String endDate, String description, String studyCenter, String psychologistDni, Boolean complete) {
        this.idStudy = idStudy;
        this.academicDiscipline = academicDiscipline;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.studyCenter = studyCenter;
        this.psychologistDni = psychologistDni;
        this.complete = complete;
    }

    public StudiesDTO() {
    }


    public int getIdStudy() {
        return idStudy;
    }

    public void setIdStudy(int idStudy) {
        this.idStudy = idStudy;
    }

    public String getAcademicDiscipline() {
        return academicDiscipline;
    }

    public void setAcademicDiscipline(String academicDiscipline) {
        this.academicDiscipline = academicDiscipline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStudyCenter() {
        return studyCenter;
    }

    public void setStudyCenter(String studyCenter) {
        this.studyCenter = studyCenter;
    }

    public String getPsychologistDni() {
        return psychologistDni;
    }

    public void setPsychologistDni(String psychologistDni) {
        this.psychologistDni = psychologistDni;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
}
