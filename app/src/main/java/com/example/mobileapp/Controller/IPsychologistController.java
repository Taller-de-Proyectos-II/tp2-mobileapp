package com.example.mobileapp.Controller;

import com.example.mobileapp.Utils.Responses.PsychologistResponse;
import com.example.mobileapp.Utils.Responses.SchedulesResponse;
import com.example.mobileapp.Utils.Responses.StudiesResponse;
import com.example.mobileapp.Utils.Responses.WorkExperienceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface IPsychologistController {
    @GET("psychologist/listAll/")
    Call<PsychologistResponse> getAllPsychologists(@Header("Authorization") String AuthToken);

    @GET("psychologist/listByFilter/")
    Call<PsychologistResponse> gettFilteredPsychologists(@Query("names") String names, @Query("lastNames") String lastNames, @Header("Authorization") String AuthToken);

    @GET("schedule/listSchedulesByPsychologistDniPatientView/")
    Call<SchedulesResponse> getSchedules(@Query("date") String date, @Query("psychologistDni") String dni, @Header("Authorization") String AuthToken);

    @GET("psychologist/workExperiences/")
    Call<WorkExperienceResponse> getExperience(@Query("dni") String dni, @Header("Authorization") String AuthToken);

    @GET("psychologist/studies/")
    Call<StudiesResponse> getStudies(@Query("dni") String dni, @Header("Authorization") String AuthToken);
}
