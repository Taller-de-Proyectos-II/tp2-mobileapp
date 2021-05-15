package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.mobileapp.Model.Guardian;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Adapters.GuardiansAdapter;
import com.example.mobileapp.Utils.Responses.GuardianResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuardianListActivty extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, GuardiansAdapter.ClickedItem{

    String passedUser, email, phone;
    RecyclerView rvGuardians;
    GuardiansAdapter guardiansAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_list_activty);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showPopup(v);
            }
        });
        getSupportActionBar().setTitle("Apoderados");

        Intent intent = getIntent();

        if(intent.getExtras() != null){
            passedUser = intent.getStringExtra("DNI");
            email = intent.getStringExtra("email");
            phone = intent.getStringExtra("phone");

        }

        rvGuardians = findViewById(R.id.rvGuardianList);

        rvGuardians.setLayoutManager(new LinearLayoutManager(this));
        rvGuardians.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        guardiansAdapter = new GuardiansAdapter(this::ClickedGuard);

        fullList();

    }

    private void fullList() {
        Call<GuardianResponse> guardList = RetrofitClient.getApiGuardian().getGuardian(passedUser);

        guardList.enqueue(new Callback<GuardianResponse>() {
            @Override
            public void onResponse(Call<GuardianResponse> call, Response<GuardianResponse> response) {
                if(response.body().getStatus() == 1){
                    List<Guardian> guardians = response.body().getGuardiansDTO();
                    guardiansAdapter.setData(guardians);
                    rvGuardians.setAdapter(guardiansAdapter);
                } else{
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GuardianResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void ClickedGuard(Guardian guardian) {
        startActivity(new Intent(this, GuardianProfileActivity.class).putExtra("data", guardian).putExtra("DNI", passedUser).putExtra("phone", phone).putExtra("email", email));
        Log.e("HERE", passedUser);
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
}
