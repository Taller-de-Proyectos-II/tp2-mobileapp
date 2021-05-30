package com.example.mobileapp.Controller;

import com.example.mobileapp.Model.DTO.testUpdateDTO;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.Responses.TestResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ITestController {

    @GET("test/listUnfinishedByPatientDni/")
    Call<TestResponse> getTests(@Query("patientDni") String dni, @Header("Authorization") String AuthToken);
    @PUT("test/")
    Call<LoginResponse> sendAnswers(@Body testUpdateDTO testUpdateDTO, @Header("Authorization") String AuthToken);
}
