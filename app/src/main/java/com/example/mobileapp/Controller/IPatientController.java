package com.example.mobileapp.Controller;

import com.example.mobileapp.Model.Patient;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.Responses.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface IPatientController {

    @GET("patient/listByDni/")
    Call<UserResponse> getUser(@Query("dni") String dni);

    @PUT("patient/")
    Call<LoginResponse> updatePatient(@Body Patient patient);
}
