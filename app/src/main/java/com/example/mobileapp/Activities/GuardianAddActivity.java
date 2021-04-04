package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileapp.Model.Guardian;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuardianAddActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etGuardianNames, etGuardianLastNames, etGuardianBirthday, etGuardianDNI;
    String passedDNI, passedEmail, passedPhone, passedPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_add);

        Button btnAddGuardian = findViewById(R.id.btnAddGuardian);
        btnAddGuardian.setOnClickListener(this);

        etGuardianBirthday = findViewById(R.id.etGuardianBirthday);
        etGuardianBirthday.setOnClickListener(this);
        etGuardianDNI = findViewById(R.id.etDNIGuardian);
        etGuardianNames = findViewById(R.id.etGuardianNames);
        etGuardianLastNames = findViewById(R.id.etGuardianLastNames);

        Intent intent = getIntent();

        if(intent.getExtras() != null){
            passedDNI = intent.getStringExtra("DNI");
            passedEmail = intent.getStringExtra("email");
            passedPhone = intent.getStringExtra("phone");
            passedPassword = intent.getStringExtra("password");
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnAddGuardian:
                checkRegister();
                break;
            case R.id.etGuardianBirthday:
                Calendar c = Calendar.getInstance();
                DatePickerDialog dpd;
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(GuardianAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "-" + (month + 1) + "-" + year;
                        etGuardianBirthday.setText(date);
                    }
                }, day, month, year);
                dpd.show();
                break;
        }
    }

    private void checkRegister() {
        Guardian guardian = new Guardian();

        String birthday, dni, email, lastNames, names, patientDni, phone;

        birthday = etGuardianBirthday.getText().toString().trim();
        dni = etGuardianDNI.getText().toString().trim();
        names = etGuardianNames.getText().toString().trim();
        lastNames = etGuardianLastNames.getText().toString().trim();
        patientDni = passedDNI;
        email = passedEmail;
        phone = passedPhone;

        guardian.setDni(dni);
        guardian.setBirthday(birthday);
        guardian.setPatientDni(patientDni);
        guardian.setNames(names);
        guardian.setLastNames(lastNames);
        guardian.setEmail(email);
        guardian.setPhone(phone);

        Call<LoginResponse> registerGuardian = RetrofitClient.getApiGuardian().registerGuardian(guardian);
        registerGuardian.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.body().getStatus() == 1)
                {
                    String message = response.body().getMessage();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(GuardianAddActivity.this, PatientProfileActivity.class).putExtra("DNI", passedDNI).putExtra("password", passedPassword);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }, 1000);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }
}
