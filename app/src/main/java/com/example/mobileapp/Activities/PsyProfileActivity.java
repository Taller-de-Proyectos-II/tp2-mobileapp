package com.example.mobileapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mobileapp.Model.Psychologist;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Adapters.ExperienceAdapter;
import com.example.mobileapp.Utils.Adapters.StudiesAdapter;
import com.example.mobileapp.Utils.Responses.StudiesResponse;
import com.example.mobileapp.Utils.Responses.WorkExperienceResponse;
import com.example.mobileapp.Utils.RetrofitClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PsyProfileActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener{

    TextView nombre, apellido;
    Psychologist psychologist;
    Button btnContact;
    AlertDialog alert1;
    ImageView ivToolbar, ivPhoto;
    String passedUser, psyDNI;
    RecyclerView rvExperience, rvNotExperience;
    ExperienceAdapter experienceAdapter = new ExperienceAdapter();
    StudiesAdapter studiesAdapter = new StudiesAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_psy);

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




        rvExperience = findViewById(R.id.rvExperience);
        rvNotExperience = findViewById(R.id.rvNotExperience);


        nombre = findViewById(R.id.tvPsyName);
        apellido = findViewById(R.id.tvPsyLastName);

        ivPhoto = findViewById(R.id.ivPhoto);

        btnContact = findViewById(R.id.btnContact);
        btnContact.setOnClickListener(this);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            psychologist = (Psychologist) intent.getSerializableExtra("data");
            String name = psychologist.getNames();
            String lastName = psychologist.getLastNames();
            nombre.setText(name);
            apellido.setText(lastName);
            psyDNI = psychologist.getUserLoginDTO().getDNI();
            passedUser = intent.getStringExtra("DNI");
        }

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Contacto del psicologo " + psychologist.getPhone());
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "CONFIRMAR",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );


        builder1.setNegativeButton(
                "CANCELAR",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );
        alert1 = builder1.create();

        rvExperience.setLayoutManager(new LinearLayoutManager(this));
        rvExperience.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        rvNotExperience.setLayoutManager(new LinearLayoutManager(this));
        rvNotExperience.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                fullListExperience();
            }
        }, 1500);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                fullListStudies();
            }
        }, 1500);






        String urlPhoto =  getString(R.string.baseURLMock) + "/psychologist/image/?dni=" + psyDNI;

        Glide.with(this)
                .load(urlPhoto)
                .apply(new RequestOptions().override(600,600))
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(ivPhoto);
        //Picasso.with(this).load(url).into(ivPhoto);

    }

    private void setPhoto() {
    }

    private void fullListExperience() {
        SharedPreferences preferences = getSharedPreferences("App", Context.MODE_PRIVATE);
        String token = preferences.getString("Token", null);
        Call<WorkExperienceResponse> getExperiences = RetrofitClient.getApiPsychologist().getExperience(psyDNI, token);

        getExperiences.enqueue(new Callback<WorkExperienceResponse>() {
            @Override
            public void onResponse(Call<WorkExperienceResponse> call, Response<WorkExperienceResponse> response) {
                if(response.body().getStatus() == 1){
                    experienceAdapter.setData(response.body().getWorkExperiencesDTO());
                    rvExperience.setAdapter(experienceAdapter);
                }
            }

            @Override
            public void onFailure(Call<WorkExperienceResponse> call, Throwable t) {

            }
        });
    }

    private void fullListStudies() {
        SharedPreferences preferences = getSharedPreferences("App", Context.MODE_PRIVATE);
        String token = preferences.getString("Token", null);
        Call<StudiesResponse> getStudies = RetrofitClient.getApiPsychologist().getStudies(psyDNI, token);

        getStudies.enqueue(new Callback<StudiesResponse>() {
            @Override
            public void onResponse(Call<StudiesResponse> call, Response<StudiesResponse> response) {
                if(response.body().getStatus() == 1){
                    studiesAdapter.setData(response.body().getStudiesDTO());
                    rvNotExperience.setAdapter(studiesAdapter);
                }
            }

            @Override
            public void onFailure(Call<StudiesResponse> call, Throwable t) {

            }
        });
    }
    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_main);
        popup.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnContact:
                startActivity(new Intent(this, PsySchedulesActivity.class).putExtra("DNI", passedUser).putExtra("PSY", psyDNI));
                //alert1.show();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.itAlertas:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent ma = new Intent(getApplicationContext(),AlertListActivity.class).putExtra("DNI", passedUser);
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
}
