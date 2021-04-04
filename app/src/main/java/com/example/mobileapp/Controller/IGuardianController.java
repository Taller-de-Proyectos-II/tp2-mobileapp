package com.example.mobileapp.Controller;

import com.example.mobileapp.Model.Guardian;
import com.example.mobileapp.Utils.Responses.GuardianResponse;
import com.example.mobileapp.Utils.Responses.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface IGuardianController {

    @POST("guardian/")
    Call<LoginResponse> registerGuardian(@Body Guardian guardian);

    @PUT("guardian/")
    Call<LoginResponse> updateGuardian(@Body Guardian guardian);

    @GET("guardian/listByPatientDni/")
    Call<GuardianResponse> getGuardian(@Query("dni") String dni);
}
