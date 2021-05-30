package com.example.mobileapp.Controller;

import com.example.mobileapp.Model.Session;
import com.example.mobileapp.Utils.Responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ISessionController {
    @POST("session/")
    Call<LoginResponse> setSession(@Body Session session, @Header("Authorization") String AuthToken);
}
