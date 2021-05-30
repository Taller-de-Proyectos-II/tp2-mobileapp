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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.mobileapp.Model.Test;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Adapters.TestsAdapter;
import com.example.mobileapp.Utils.Responses.TestResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestListActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener, TestsAdapter.ClickedItem{

    TestResponse testResponse = new TestResponse();
    ImageView ivPerfil;
    String passedUser;
    RecyclerView rvTestList;
    TestsAdapter testsAdapter;

    ArrayList<Test> appTests = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });
        getSupportActionBar().setTitle("Pruebas disponibles");




        Intent intent = getIntent();
        if(intent.getExtras() != null){
            passedUser = intent.getStringExtra("DNI");

        }

        rvTestList = findViewById(R.id.rvTestList);

        rvTestList.setLayoutManager(new LinearLayoutManager(this));
        rvTestList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        testsAdapter = new TestsAdapter(this::ClickedTest);

        fullList(passedUser);
    }

    @Override
    public void ClickedTest(Test test) {
        startActivity(new Intent(this, TestActivity.class).putExtra("data", test).putExtra("DNI", passedUser));
    }

    private void fullList(String passedUser) {
        SharedPreferences preferences = getSharedPreferences("App", Context.MODE_PRIVATE);
        String token = preferences.getString("Token", null);
        Call<TestResponse> getTests = RetrofitClient.getApiTest().getTests(passedUser, token);

        getTests.enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                if(response.body().getStatus() == 1){
                    fillInformation(response.body());
                    testsAdapter.setData(appTests);
                    rvTestList.setAdapter(testsAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), "No tiene pruebas disponibles", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {

            }
        });
    }

    private void fillInformation(TestResponse testsResponse) {
        testResponse.setStatus(testsResponse.getStatus());
        testResponse.setMessage(testsResponse.getMessage());
        appTests.addAll(testsResponse.getTestsDTO());
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

    }
}
