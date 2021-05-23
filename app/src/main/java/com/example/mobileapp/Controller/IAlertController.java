package com.example.mobileapp.Controller;

import com.example.mobileapp.Model.DTO.AlertCreateDTO;
import com.example.mobileapp.Model.DTO.AlertUpdateDTO;
import com.example.mobileapp.Utils.Responses.AlertResponse;
import com.example.mobileapp.Utils.Responses.AlertResponse2;
import com.example.mobileapp.Utils.Responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface IAlertController {

    @GET("alerts/listByPatientDni/")
    Call<AlertResponse2> getAlerts(@Query("patientDni") String dni);

    @POST("alerts/")
    Call<AlertResponse> createAlert(@Body AlertCreateDTO alertCreateDTO);

    @PUT("alerts/")
    Call<LoginResponse> sendAnswers(@Body AlertUpdateDTO alertUpdateDTO);
}
