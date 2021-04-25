package com.example.mobileapp.Controller;

import com.example.mobileapp.Utils.Responses.TestResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ITestController {

    @GET("test/listUnfinishedByPatientDni/")
    Call<TestResponse> getTests(@Query("patientDni") String dni);
}
