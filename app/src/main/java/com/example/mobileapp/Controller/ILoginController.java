package com.example.mobileapp.Controller;

import com.example.mobileapp.Model.Patient;
import com.example.mobileapp.Model.User;
import com.example.mobileapp.Utils.Responses.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ILoginController {

    @POST("login/")
    Call<LoginResponse> login(@Body User user);

    @POST("patient/")
    Call<LoginResponse> register(@Body Patient patient);

    @POST("/login/restorePasswordPatient/")
    Call<LoginResponse> sendEmail(@Body String email);
}
