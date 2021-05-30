package com.example.mobileapp.Controller;

import com.example.mobileapp.Utils.Responses.RecomendationsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IRecomendationController {

    @GET("recomendations/listAll/")
    Call<RecomendationsResponse> getRecomendations(@Header("Authorization") String AuthToken);

}