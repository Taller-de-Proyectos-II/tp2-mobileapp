package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.mobileapp.Model.Psychologist;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Adapters.PsychologistsAdapter;
import com.example.mobileapp.Utils.Responses.PsychologistResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactPsyActivity extends AppCompatActivity implements View.OnClickListener, PsychologistsAdapter.ClickedItem, PopupMenu.OnMenuItemClickListener {

    ImageView ivToolbar;
    ImageView ivSearch;
    EditText etFilter;
    RecyclerView rvList;
    String passedUser;
    PsychologistsAdapter psychologistsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_psy);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showPopup(v);
            }
        });
        getSupportActionBar().setTitle("Contacta a tu psicólogo");

        ivToolbar = findViewById(R.id.ivPerfil);
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            passedUser = intent.getStringExtra("DNI");
        }

        rvList = findViewById(R.id.rvPsy);

        ivSearch = findViewById(R.id.ivFilter);
        ivSearch.setOnClickListener(this);

        etFilter =findViewById(R.id.etFiltro);

        rvList.setLayoutManager(new LinearLayoutManager(this));

        rvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        psychologistsAdapter = new PsychologistsAdapter(this::ClickedPsy);

        fullList();

    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_main);
        popup.show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ivFilter:
                if(etFilter.getText() != null && etFilter.getText().length() > 0)
                    filteredList(etFilter.getText().toString());
                else
                    fullList();
                break;

        }
    }

    private void fullList() {
        Call<PsychologistResponse> psyList = RetrofitClient.getApiPsychologist().getAllPsychologists();

        psyList.enqueue(new Callback<PsychologistResponse>() {
            @Override
            public void onResponse(Call<PsychologistResponse> call, Response<PsychologistResponse> response) {
                if(response.isSuccessful()){
                    List<Psychologist> psychologists = response.body().getPsychologistsDTO();
                    psychologistsAdapter.setData(psychologists);
                    Log.e("HERE", psychologists.get(0).getUserLoginDTO().getDNI());
                    rvList.setAdapter(psychologistsAdapter);

                    Log.e("HERE", psychologists.get(0).getNames());

                }

            }

            @Override
            public void onFailure(Call<PsychologistResponse> call, Throwable t) {

            }
        });
    }

    private void filteredList(String filter) {
        Call<PsychologistResponse> psyList = RetrofitClient.getApiPsychologist().gettFilteredPsychologists(" ", etFilter.getText().toString());

        psyList.enqueue(new Callback<PsychologistResponse>() {
            @Override
            public void onResponse(Call<PsychologistResponse> call, Response<PsychologistResponse> response) {
                if(response.isSuccessful()){
                    List<Psychologist> psychologists = response.body().getPsychologistsDTO();
                    psychologistsAdapter.setData(psychologists);

                    rvList.setAdapter(psychologistsAdapter);

                }

            }

            @Override
            public void onFailure(Call<PsychologistResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void ClickedPsy(Psychologist psychologist) {
        startActivity(new Intent(this, PsyProfileActivity.class).putExtra("data", psychologist).putExtra("USER", passedUser));
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {

            case R.id.itContacto:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent mp = new Intent(getApplicationContext(),ContactPsyActivity.class);
                        startActivity(mp);
                    }
                }, 1000);
                break;

        }
        return false;
    }
}
