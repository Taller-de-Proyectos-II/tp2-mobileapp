package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.mobileapp.Model.Manifestation;
import com.example.mobileapp.Model.Symptom;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Responses.ManifestationsResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManifestationsActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    ImageView ivPerfil;
    RecyclerView recyclerView;
    ManifestationsResponse manifestationsResponse2 = new ManifestationsResponse();
    List<Symptom> listPhysicalSymptoms;
    ArrayList<Symptom> physicalSymptoms = new ArrayList<>();
    ArrayList<Symptom> cognitiveSymptoms = new ArrayList<>();
    ArrayList<Symptom> emotionalSymptoms = new ArrayList<>();
    ArrayList<Symptom> conductualSymptoms = new ArrayList<>();
    Symptom[] arrSymptoms = new Symptom[10];
    Manifestation mockManifestation = new Manifestation();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manifestations);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showPopup(v);
            }
        });
        getSupportActionBar().setTitle("Síntomas");
        ivPerfil = findViewById(R.id.ivPerfil);
        ivPerfil.setOnClickListener(this);

        recyclerView = findViewById(R.id.rvManifestationsList);

        Call<ManifestationsResponse> manifestationsList = RetrofitClient.getApiManifestation().getManifestations();

        manifestationsList.enqueue(new Callback<ManifestationsResponse>() {
            @Override
            public void onResponse(Call<ManifestationsResponse> call, Response<ManifestationsResponse> response) {
                if(response.body().getStatus() == 1){
                    //fillInformation(response.body());
                    Log.e("PASÓ", response.body().toString());
                    HashMap<String, Manifestation> map = response.body().getManifestationsDTO();

                    for(Map.Entry<String, Manifestation> entry : map.entrySet()){

                        String key = entry.getKey();
                        mockManifestation = entry.getValue();
                        String ultimo = String.valueOf(response.body().getManifestationsDTO().get(key).getSymptoms());
                        String ultimoDesc = String.valueOf(response.body().getManifestationsDTO().get(key).getDescription());
                        //Log.e("HEREEEEE", manifestationsResponse2.getManifestationsDTO().get(key).getDescription());
                        /*for(Symptom symptom : entry.getValue().getSymptoms())
                        {
                            listPhysicalSymptoms.add(symptom);
                        }
                        */
                        //String test = String.valueOf(entry.getValue().getSymptoms().get(1).getDescription());
                        mockManifestation.setSymptoms(entry.getValue().getSymptoms());
                        //String desc = mockManifestation.getSymptoms().get(0).getDescription();
                        ManifestationsResponse mockResponse = new ManifestationsResponse(response.body().getStatus(), response.body().getMessage(), response.body().getManifestationsDTO());
                        mockResponse.getManifestationsDTO().get(key).setSymptoms(response.body().getManifestationsDTO().get(key).getSymptoms());

                        //String desc = sintoma.getDescription();;
                        if(key.matches("physical")) {
                            //physicalSymptoms.addAll(value.getSymptoms());
                        }
                        Log.e("HERE", ultimo);
                        Log.e("HERE", ultimoDesc);
                        //String desc2 = value.getSymptoms().get(0).getDescription();
                        //String desc = physicalSymptoms.get(0).getDescription();

                        /*map.get(key).setSymptoms(physicalSymptoms);
                        int size = value.getSymptoms().size();
                        for(int i = 0; i < size;i++){
                            if(key.matches("phyisical")){
                                physicalSymptoms.add(value.getSymptoms().get(i));
                            }
                        }*/
                    }

                }
            }

            @Override
            public void onFailure(Call<ManifestationsResponse> call, Throwable t) {
                Log.e("NO PASÓ", t.getLocalizedMessage());
            }
        });
    }

    public void fillInformation(ManifestationsResponse manifestationsResponse)
    {
        manifestationsResponse2.setStatus(manifestationsResponse.getStatus());
        manifestationsResponse2.setMessage(manifestationsResponse.getMessage());
        manifestationsResponse2.setManifestationsDTO(manifestationsResponse.getManifestationsDTO());
        Manifestation manifestationPhysical = new Manifestation();
        manifestationPhysical.setDescription(manifestationsResponse.getManifestationsDTO().get(1).getDescription());
        manifestationPhysical.setId(manifestationsResponse.getManifestationsDTO().get(1).getId());
        manifestationPhysical.setName(manifestationsResponse.getManifestationsDTO().get(1).getName());
        ArrayList<Symptom> physSymptoms = new ArrayList<>();
        physSymptoms.add(manifestationsResponse.getManifestationsDTO().get(1).getSymptoms().get(0));
        manifestationPhysical.setSymptoms(physSymptoms);
        HashMap<String, Manifestation> mockHash = new HashMap<String, Manifestation>(){{
            put("physical", manifestationPhysical);
        }};
        manifestationsResponse2.setManifestationsDTO(mockHash);
    }

    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_main);
        popup.show();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
