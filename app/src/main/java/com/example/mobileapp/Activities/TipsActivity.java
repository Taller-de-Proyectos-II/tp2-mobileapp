package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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

import com.example.mobileapp.Model.DTO.recomendationDTO;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Adapters.RecomendationsAdapter;
import com.example.mobileapp.Utils.Responses.RecomendationsResponse;
import com.example.mobileapp.Utils.Responses.SymptomsResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TipsActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener{

    String passedUser;
    RecyclerView rvTips;
    RecomendationsAdapter recomendationsAdapter;
    RecomendationsResponse appResponse = new RecomendationsResponse();
    ArrayList<recomendationDTO> appRecomendations = new ArrayList<>();
    Button btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showPopup(v);
            }
        });
        getSupportActionBar().setTitle("Mantenga la calma");

        SharedPreferences preferences = getSharedPreferences("App", Context.MODE_PRIVATE);
        String token = preferences.getString("Token", null);


        btnAccept = findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(this);

        rvTips = findViewById(R.id.rvTips);

        rvTips.setLayoutManager(new LinearLayoutManager(this));
        rvTips.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));


        recomendationsAdapter = new RecomendationsAdapter(this::ClickedRecomendation);


        Intent intent = getIntent();
        if(intent.getExtras() != null){
            passedUser = intent.getStringExtra("DNI");
        }

        fullList();

    }

    private void fullList() {
        SharedPreferences preferences = getSharedPreferences("App", Context.MODE_PRIVATE);
        String token = preferences.getString("Token", null);
        Call<RecomendationsResponse> getRecs = RetrofitClient.getApiRecomendation().getRecomendations(token);

        getRecs.enqueue(new Callback<RecomendationsResponse>() {
            @Override
            public void onResponse(Call<RecomendationsResponse> call, Response<RecomendationsResponse> response) {
                if(response.body().getStatus() == 1){
                    fillInformation(response.body());
                    recomendationsAdapter.setData(appRecomendations);
                    rvTips.setAdapter(recomendationsAdapter);
                } else{
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RecomendationsResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void ClickedRecomendation(recomendationDTO recomendationDTO, int recNumber) {
        switch (recNumber){
            case 1:
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + "SZe_LVS4KyU"));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + "SZe_LVS4KyU"));
                try {
                    startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }
                break;
            case 2:
                Intent appIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + "1zjry52X9yU"));
                Intent webIntent1 = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + "1zjry52X9yU"));
                try {
                    startActivity(appIntent1);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent1);
                }
                break;
            case 3:
                Intent appIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + "VZrUfADraX8"));
                Intent webIntent2 = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + "VZrUfADraX8"));
                try {
                    startActivity(appIntent2);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent2);
                }
                break;
            case 4:
                Intent appIntent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + "UqU19dR0bFE"));
                Intent webIntent3 = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + "UqU19dR0bFE"));
                try {
                    startActivity(appIntent3);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent3);
                }
                break;
            case 5:
                Intent appIntent4 = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + "v9ExP2O09dk"));
                Intent webIntent4 = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + "v9ExP2O09dk"));
                try {
                    startActivity(appIntent4);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent4);
                }
                break;

        }
    }

    public void fillInformation(RecomendationsResponse recomendationsResponse)
    {
        appResponse.setStatus(recomendationsResponse.getStatus());
        appResponse.setMessage(recomendationsResponse.getMessage());
        appRecomendations.addAll(recomendationsResponse.getRecomendationsDTO());
    }

    private void showPopup(View v) {
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
                        Toast.makeText(getApplicationContext(), "Sesi??n cerrada", Toast.LENGTH_SHORT).show();
                        startActivity(mp);
                    }
                }, 1000);
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAccept:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent mp = new Intent(getApplicationContext(),MenuActivity.class).putExtra("DNI", passedUser);
                        startActivity(mp);
                    }
                }, 1000);
                break;
        }
    }
}
