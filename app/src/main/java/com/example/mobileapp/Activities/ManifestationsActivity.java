package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

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
    String passedUser;
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

        SharedPreferences preferences = getSharedPreferences("App", Context.MODE_PRIVATE);
        String token = preferences.getString("Token", null);


        recyclerView = findViewById(R.id.rvManifestationsList);

        appResponse.setMessage("");
        appResponse.setStatus(0);
        appResponse.setSypmtoms(appSymptoms);

        rvSymptomList = findViewById(R.id.rvManifestationsList);

        rvSymptomList.setLayoutManager(new LinearLayoutManager(this));
        rvSymptomList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        symptomsAdapter = new SymptomsAdapter(this::ClickedSymptom);

        Intent intent = getIntent();

        if(intent.getExtras() != null){
            passedUser = intent.getStringExtra("DNI");
        }

        fullList();


    }

    private void fullList() {
        SharedPreferences preferences = getSharedPreferences("App", Context.MODE_PRIVATE);
        String token = preferences.getString("Token", null);
        Call<SymptomsResponse> getSymptoms = RetrofitClient.getApiSymptom().getAll(token);

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
        switch (item.getItemId())
        {
            case R.id.itAlertas:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent ma = new Intent(getApplicationContext(),ManifestationsActivity.class).putExtra("DNI", passedUser);
                        startActivity(ma);
                    }
                }, 1000);
                break;
            case R.id.itMenuPrincipal:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent mp = new Intent(getApplicationContext(),MenuActivity.class).putExtra("DNI", passedUser);
                        startActivity(mp);
                    }
                }, 1000);
                break;
            case R.id.itContacto:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent mp = new Intent(getApplicationContext(),ContactPsyActivity.class).putExtra("DNI", passedUser);
                        startActivity(mp);
                    }
                }, 1000);
                break;
            case R.id.itTests:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent mp = new Intent(getApplicationContext(),TestListActivity.class).putExtra("DNI", passedUser);
                        startActivity(mp);
                    }
                }, 1000);
                break;
            case R.id.itLogout:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent mp = new Intent(getApplicationContext(),MainActivity.class);
                        mp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        Toast.makeText(getApplicationContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show();
                        startActivity(mp);
                    }
                }, 1000);
                break;

        }
        return false;
    }

    @Override
    public void ClickedSymptom(Symptom symptom) {
        startActivity(new Intent(this, SymptomDetails.class).putExtra("data", symptom).putExtra("DNI", passedUser));
    }
}
