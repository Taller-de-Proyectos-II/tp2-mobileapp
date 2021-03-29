package com.example.mobileapp.Controller;

import com.example.mobileapp.Model.User;
import com.example.mobileapp.Utils.Responses.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ILoginController {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("dni") String user, @Field("password") String password);

}
