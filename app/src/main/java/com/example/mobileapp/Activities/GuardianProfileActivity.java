package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp.Model.Guardian;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Responses.GuardianResponse;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuardianProfileActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener{

    String passedDNI, passedPhone, passedEmail;
    GuardianResponse guardianResponse2 = new GuardianResponse();
    ArrayList<Guardian> fillGuardians = new ArrayList<>();
    Guardian utilGuardian = new Guardian();
    EditText etGuardianNames, etGuardianBirthday, etGuardianDNI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_guardian);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showPopup(v);
            }
        });
        getSupportActionBar().setTitle("Apoderado");

        guardianResponse2.setMessage("");
        guardianResponse2.setStatus(0);
        guardianResponse2.setGuardiansDTO(fillGuardians);

        etGuardianNames = findViewById(R.id.etGuardianNames);
        etGuardianBirthday = findViewById(R.id.etGuardianBirthday);
        etGuardianBirthday.setOnClickListener(this);
        etGuardianDNI = findViewById(R.id.etDNIGuardian);

        Button btnUpdateGuardian = findViewById(R.id.btnAddGuardian);
        btnUpdateGuardian.setOnClickListener(this);



        Intent intent = getIntent();

        if(intent.getExtras() != null){
            passedDNI = intent.getStringExtra("DNI");
            passedEmail = intent.getStringExtra("email");
            passedPhone = intent.getStringExtra("phone");
        }

        Call<GuardianResponse> guardianResponse = RetrofitClient.getApiGuardian().getGuardian(passedDNI);

        guardianResponse.enqueue(new Callback<GuardianResponse>() {
            @Override
            public void onResponse(Call<GuardianResponse> call, Response<GuardianResponse> response) {
                if(response.isSuccessful()){
                    fillInformation(response.body());
                    String names = fillGuardians.get(0).getNames();
                    String lastNames = fillGuardians.get(0).getLastNames();
                    String fullName = names + " " + lastNames;
                    etGuardianNames.setText(fullName, TextView.BufferType.EDITABLE);
                    etGuardianBirthday.setText(fillGuardians.get(0).getBirthday(), TextView.BufferType.EDITABLE);
                    etGuardianDNI.setText(fillGuardians.get(0).getDni(),TextView.BufferType.EDITABLE);
                }
                Log.e("AQUIIIIIII", response.body().toString());
            }

            @Override
            public void onFailure(Call<GuardianResponse> call, Throwable t) {
                Log.e("AQUIIIIIII", t.getMessage());
            }
        });
    }

    private void fillInformation(GuardianResponse guardianResponse) {
        guardianResponse2.setMessage(guardianResponse.getMessage());
        guardianResponse2.setStatus(guardianResponse.getStatus());
        Guardian utilGuardian = new Guardian();
        utilGuardian.setPhone(guardianResponse.getGuardiansDTO().get(0).getPhone());
        utilGuardian.setNames(guardianResponse.getGuardiansDTO().get(0).getNames());
        utilGuardian.setLastNames(guardianResponse.getGuardiansDTO().get(0).getLastNames());
        utilGuardian.setEmail(guardianResponse.getGuardiansDTO().get(0).getEmail());
        utilGuardian.setBirthday(guardianResponse.getGuardiansDTO().get(0).getBirthday());
        utilGuardian.setDni(guardianResponse.getGuardiansDTO().get(0).getDni());
        utilGuardian.setPatientDni(passedDNI);
        utilGuardian.setEmail(passedEmail);
        utilGuardian.setPhone(passedPhone);
        guardianResponse2.getGuardiansDTO().add(utilGuardian);
    }
    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_main);
        popup.show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnAddGuardian:
                checkUpdate();
                break;
            case R.id.etGuardianBirthday:
                Calendar c = Calendar.getInstance();
                DatePickerDialog dpd;
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(GuardianProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "-" + (month + 1) + "-" + year;
                        etGuardianBirthday.setText(date);
                    }
                }, day, month, year);
                dpd.show();
        }
    }

    private void checkUpdate() {
        utilGuardian.setPatientDni(passedDNI);
        utilGuardian.setPhone(passedPhone);
        utilGuardian.setEmail(passedEmail);

        String fullName = etGuardianNames.getText().toString();
        int idx = fullName.lastIndexOf(' ');
        if(idx == -1)
            throw new IllegalArgumentException("Solo hay un nombre: " + fullName);
        String firstName = fullName.substring(0, idx);
        String lastName = fullName.substring(idx + 1);

        utilGuardian.setNames(firstName);
        utilGuardian.setLastNames(lastName);
        utilGuardian.setBirthday(etGuardianBirthday.getText().toString());
        utilGuardian.setDni(etGuardianDNI.getText().toString());

        Call<LoginResponse> updateGuardian = RetrofitClient.getApiGuardian().updateGuardian(utilGuardian);
        updateGuardian.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.body().getStatus() == 1)
                {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("AQUIIIIIII", t.getMessage());
            }
        });

    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {

            case R.id.itMediciones:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent ma = new Intent(getApplicationContext(),ManifestationsActivity.class).putExtra("DNI", passedDNI);
                        startActivity(ma);
                    }
                }, 1000);
                break;
            case R.id.itMenuPrincipal:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent mp = new Intent(getApplicationContext(),MenuActivity.class).putExtra("DNI", passedDNI);
                        startActivity(mp);
                    }
                }, 1000);
                break;
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
