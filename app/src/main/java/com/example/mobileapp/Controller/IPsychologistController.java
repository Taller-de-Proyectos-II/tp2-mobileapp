package com.example.mobileapp.Controller;

import com.example.mobileapp.Utils.Responses.PsychologistResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IPsychologistController {
    @GET("psychologist/listAll/")
    Call<PsychologistResponse> getAllPsychologists();

    @GET("psychologist/listByFilter/")
    Call<PsychologistResponse> gettFilteredPsychologists(@Query("names") String names, @Query("lastNames") String lastNames);
}
