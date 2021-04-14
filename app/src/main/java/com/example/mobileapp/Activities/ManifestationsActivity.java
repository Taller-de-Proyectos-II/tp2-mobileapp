package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.mobileapp.Model.Symptom;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Adapters.SymptomsAdapter;
import com.example.mobileapp.Utils.Responses.SymptomsResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManifestationsActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener, SymptomsAdapter.ClickedItem {

    ImageView ivPerfil;
    RecyclerView recyclerView;
    SymptomsResponse appResponse = new SymptomsResponse();

    ArrayList<Symptom> appSymptoms = new ArrayList<>();

    RecyclerView rvSymptomList;

    SymptomsAdapter symptomsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manifestations);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });
        getSupportActionBar().setTitle("Síntomas");
        ivPerfil = findViewById(R.id.ivPerfil);
        ivPerfil.setOnClickListener(this);

        recyclerView = findViewById(R.id.rvManifestationsList);

        appResponse.setMessage("");
        appResponse.setStatus(0);
        appResponse.setSypmtoms(appSymptoms);

        rvSymptomList = findViewById(R.id.rvManifestationsList);

        rvSymptomList.setLayoutManager(new LinearLayoutManager(this));
        rvSymptomList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        symptomsAdapter = new SymptomsAdapter(this::ClickedSymptom);

        fullList();


    }

    private void fullList() {
        Call<SymptomsResponse> getSymptoms = RetrofitClient.getApiSymptom().getAll();

        getSymptoms.enqueue(new Callback<SymptomsResponse>() {
            @Override
            public void onResponse(Call<SymptomsResponse> call, Response<SymptomsResponse> response) {
                if(response.body().getStatus() == 1){
                    fillInformation(response.body());
                    String desc = appSymptoms.get(3).getDescription();
                    Log.e("Here", desc);
                    symptomsAdapter.setData(appSymptoms);
                    rvSymptomList.setAdapter(symptomsAdapter);
                }
            }

            @Override
            public void onFailure(Call<SymptomsResponse> call, Throwable t) {

            }
        });
    }

    public void fillInformation(SymptomsResponse symptomsResponse)
    {
        appResponse.setStatus(symptomsResponse.getStatus());
        appResponse.setMessage(symptomsResponse.getMessage());
        appSymptoms.addAll(symptomsResponse.getSypmtoms());
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

    @Override
    public void ClickedSymptom(Symptom symptom) {
        //startActivity(new Intent(this, PsyProfileActivity.class).putExtra("data2", symptom));
    }
}
