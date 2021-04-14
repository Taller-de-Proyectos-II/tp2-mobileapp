package com.example.mobileapp.Controller;

import com.example.mobileapp.Utils.Responses.SymptomsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ISymptomController {

    @GET("symptom/listAll/")
    Call<SymptomsResponse> getAll();
}
