package com.example.mobileapp.Controller;

import com.example.mobileapp.Model.Guardian;
import com.example.mobileapp.Utils.Responses.GuardianResponse;
import com.example.mobileapp.Utils.Responses.LoginResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface IGuardianController {

    @POST("guardian/")
    Call<LoginResponse> registerGuardian(@Body Guardian guardian, @Header("Authorization") String AuthToken);

    @PUT("guardian/")
    Call<LoginResponse> updateGuardian(@Body Guardian guardian, @Header("Authorization") String AuthToken);

    @GET("guardian/listByPatientDni/")
    Call<GuardianResponse> getGuardian(@Query("dni") String dni, @Header("Authorization") String AuthToken);

    @DELETE("guardian/")
    Call<LoginResponse> deleteGuardian(@Query("dni") String dni, @Query("patientDni") String patientDni, @Header("Authorization") String AuthToken);

    @Multipart
    @POST()
    Call<LoginResponse> updatePhoto(@Url String url, @Part("file") MultipartBody file, @Header("Authorization") String AuthToken);
}
