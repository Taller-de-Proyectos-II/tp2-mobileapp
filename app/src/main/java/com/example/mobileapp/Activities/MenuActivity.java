package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;


import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Responses.UserResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MenuActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {

    ImageView ivPerfil;
    String passedUser;
    String passedPassword;
    Button btnContact, btnAlerts, btnPruebas, btnPerfil;
    TextView tvWelcome, tvAbout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showPopup(v);
            }
        });
        getSupportActionBar().setTitle("Menú Principal");

        btnAlerts = findViewById(R.id.btnAlertas);
        btnAlerts.setOnClickListener(this);

        btnContact = findViewById(R.id.btnContacto);
        btnContact.setOnClickListener(this);

        btnPerfil = findViewById(R.id.btnPerfil);
        btnPerfil.setOnClickListener(this);

        btnPruebas = findViewById(R.id.btnPruebas);
        btnPruebas.setOnClickListener(this);

        tvWelcome = findViewById(R.id.tvWelcome);

        tvAbout = findViewById(R.id.tvAbout);
        tvAbout.setOnClickListener(this);

        Intent intent = getIntent();

        if(intent.getExtras() != null){
            passedUser = intent.getStringExtra("DNI");
            passedPassword = intent.getStringExtra("password");
        }

        Call<UserResponse> userResponse = RetrofitClient.getApiUser().getUser(passedUser);

        userResponse.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.body().getStatus() == 1){
                    tvWelcome.setText("Bienvenid@, " + response.body().getPatient().getNames());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){

            case R.id.btnAlertas:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent ppa = new Intent(getApplicationContext(),AlertListActivity.class).putExtra("DNI", passedUser).putExtra("password", passedPassword);
                        startActivity(ppa);
                    }
                }, 1000);
                break;
            case R.id.btnPruebas:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent ppa = new Intent(getApplicationContext(),TestListActivity.class).putExtra("DNI", passedUser).putExtra("password", passedPassword);
                        startActivity(ppa);
                    }
                }, 1000);
                break;
            case R.id.btnContacto:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent ppa = new Intent(getApplicationContext(),ContactPsyActivity.class).putExtra("DNI", passedUser).putExtra("password", passedPassword);
                        startActivity(ppa);
                    }
                }, 1000);
                break;
            case R.id.btnPerfil:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent ppa = new Intent(getApplicationContext(),PatientProfileActivity.class).putExtra("DNI", passedUser).putExtra("password", passedPassword);
                        startActivity(ppa);
                    }
                }, 1000);
                break;
            case R.id.tvAbout:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent ppa = new Intent(getApplicationContext(),AboutActivity.class).putExtra("DNI", passedUser).putExtra("password", passedPassword);
                        startActivity(ppa);
                    }
                }, 1000);
                break;
        }
    }
    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_main);
        popup.show();
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
                        Log.e("HERE", passedUser);
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
