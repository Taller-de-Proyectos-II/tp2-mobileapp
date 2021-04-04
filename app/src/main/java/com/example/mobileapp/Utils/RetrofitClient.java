package com.example.mobileapp.Utils;

import com.example.mobileapp.Controller.IGuardianController;
import com.example.mobileapp.Controller.ILoginController;
import com.example.mobileapp.Controller.IPatientController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static String API_BASE_URL = "https://app-tp2-api.herokuapp.com/";
    private static Retrofit retrofit;


    private static Retrofit getRetrofit(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API_BASE_URL)
                .client(okHttpClient)
                .build();
        return retrofit;
    }


    public static ILoginController getApiLogin() {
        ILoginController loginController = getRetrofit().create(ILoginController.class);

        return loginController;
    }

    public static IPatientController getApiUser() {
        IPatientController patientController = getRetrofit().create(IPatientController.class);

        return patientController;
    }
    public static IGuardianController getApiGuardian(){
        IGuardianController guardianController = getRetrofit().create(IGuardianController.class);
        return guardianController;
    }
}
