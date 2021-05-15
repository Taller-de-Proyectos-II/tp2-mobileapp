package com.example.mobileapp.Utils;

import com.example.mobileapp.Controller.IAlertController;
import com.example.mobileapp.Controller.IGuardianController;
import com.example.mobileapp.Controller.ILoginController;
import com.example.mobileapp.Controller.IManifestationController;
import com.example.mobileapp.Controller.IPatientController;
import com.example.mobileapp.Controller.IPsychologistController;
import com.example.mobileapp.Controller.ISessionController;
import com.example.mobileapp.Controller.ISymptomController;
import com.example.mobileapp.Controller.ITestController;
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
    //private static String API_BASE_URL = "http://ec2-54-144-123-136.compute-1.amazonaws.com/";
    private static String API_BASE_URL = "http://phychomonitoringappapi-env.eba-ewmhpcqz.us-east-1.elasticbeanstalk.com";
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



    private static Retrofit getRetrofitJSON(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
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

    public static IManifestationController getApiManifestation(){
        IManifestationController manifestationController = getRetrofit().create(IManifestationController.class);
        return manifestationController;
    }

    public static IPsychologistController getApiPsychologist(){
        IPsychologistController psychologistController = getRetrofit().create(IPsychologistController.class);
        return psychologistController;
    }

    public static ISymptomController getApiSymptom(){
        ISymptomController symptomController = getRetrofit().create(ISymptomController.class);
        return symptomController;
    }

    public static ISessionController getApiSession(){
        ISessionController sessionController = getRetrofit().create(ISessionController.class);
        return sessionController;
    }

    public static ITestController getApiTest(){
        ITestController testController = getRetrofit().create(ITestController.class);
        return testController;
    }

    public static IAlertController getApiAlert(){
        IAlertController alertController = getRetrofit().create(IAlertController.class);
        return alertController;
    }
}
