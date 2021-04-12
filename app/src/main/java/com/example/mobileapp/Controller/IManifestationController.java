package com.example.mobileapp.Controller;

import com.example.mobileapp.Utils.Responses.ManifestationsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IManifestationController {

    @GET("manifestation/listManifestations/")
    Call<ManifestationsResponse> getManifestations();

}
