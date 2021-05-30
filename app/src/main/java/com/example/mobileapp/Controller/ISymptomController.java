package com.example.mobileapp.Controller;

import com.example.mobileapp.Utils.Responses.AlertSymptomsResponse;
import com.example.mobileapp.Utils.Responses.SymptomsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ISymptomController {

    @GET("symptom/listAll/")
    Call<SymptomsResponse> getAll(@Header("Authorization") String AuthToken);

    @GET("symptoms/listAll/")
    Call<AlertSymptomsResponse> getSymptoms(@Header("Authorization") String AuthToken);
}
