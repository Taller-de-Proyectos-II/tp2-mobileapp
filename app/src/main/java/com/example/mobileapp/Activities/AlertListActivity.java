package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.mobileapp.Model.Alert;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Adapters.AlertsAdapter;
import com.example.mobileapp.Utils.Responses.AlertResponse2;
import com.example.mobileapp.Utils.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertListActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener{

    Button btnNewAlert;
    RecyclerView rvAlertsList;
    String passedUser;
    AlertsAdapter alertsAdapter = new AlertsAdapter();

    AlertResponse2 alertResponse2 = new AlertResponse2();
    ArrayList<Alert> appAlerts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showPopup(v);
            }
        });
        getSupportActionBar().setTitle("Alertas");

        btnNewAlert = findViewById(R.id.btnNewAlert);
        btnNewAlert.setOnClickListener(this);

        rvAlertsList = findViewById(R.id.rvAlertsList);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            passedUser = intent.getStringExtra("DNI");
        }

        rvAlertsList.setLayoutManager(new LinearLayoutManager(this));
        rvAlertsList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));



        fullList();

    }

    private void fullList() {
        Call<AlertResponse2> getAlerts = RetrofitClient.getApiAlert().getAlerts(passedUser);

        getAlerts.enqueue(new Callback<AlertResponse2>() {
            @Override
            public void onResponse(Call<AlertResponse2> call, Response<AlertResponse2> response) {
                if(response.body().getStatus() == 1){
                    fillInformation(response.body());
                    alertsAdapter.setData(appAlerts);
                    rvAlertsList.setAdapter(alertsAdapter);
                }
            }

            @Override
            public void onFailure(Call<AlertResponse2> call, Throwable t) {

            }
        });
    }

    private void fillInformation(AlertResponse2 alertResponse) {
        alertResponse2.setStatus(alertResponse.getStatus());
        alertResponse2.setMessage(alertResponse.getMessage());
        appAlerts.addAll(alertResponse.getAlertsDTO());
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
                        Toast.makeText(getApplicationContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show();
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
         case R.id.btnNewAlert:
             new Handler().postDelayed(new Runnable(){
                 @Override
                 public void run(){
                     Intent ma = new Intent(getApplicationContext(),AlertActivity.class).putExtra("DNI", passedUser);
                     startActivity(ma);
                 }
             }, 1000);
             break;
     }
    }
}
