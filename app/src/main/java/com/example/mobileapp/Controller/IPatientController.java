package com.example.mobileapp.Controller;

import android.graphics.Bitmap;

import com.example.mobileapp.Model.Patient;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.Responses.UserResponse;

import java.io.File;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface IPatientController {

    @GET("patient/listByDni/")
    Call<UserResponse> getUser(@Query("dni") String dni);

    @PUT("patient/")
    Call<LoginResponse> updatePatient(@Body Patient patient);

    @Multipart
    @POST("patient/image/")
    Call<LoginResponse> updatePhoto(@Query("dni") String dni, @Part MultipartBody.Part multipartImage);
}
