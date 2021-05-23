package com.example.mobileapp.Controller;

import android.graphics.Bitmap;

import com.example.mobileapp.Model.Patient;
import com.example.mobileapp.Model.changePasswordDTO;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.Responses.UserResponse;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface IPatientController {

    @GET("patient/listByDni/")
    Call<UserResponse> getUser(@Query("dni") String dni);

    @PUT("patient/")
    Call<LoginResponse> updatePatient(@Body Patient patient);

    @Multipart
    @POST()
    Call<LoginResponse> updatePhoto(@Url String url, @Part("file") MultipartBody file);

    @PUT("patient/updatePassword/")
    Call<LoginResponse> updatePassword(@Body changePasswordDTO changePasswordDTO);
}
