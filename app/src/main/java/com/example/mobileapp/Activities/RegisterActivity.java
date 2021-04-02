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

import com.example.mobileapp.Model.Patient;
import com.example.mobileapp.Model.User;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etBirthday, etEmail, etLastNames, etNames, etPhone, etDni, etPassword, etPassword2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btnRegistro = findViewById(R.id.btnRegistrar);
        btnRegistro.setOnClickListener(this);

        etBirthday = findViewById(R.id.etFechaNacimiento);
        etBirthday.setOnClickListener(this);
        etEmail = findViewById(R.id.etCorreo);
        etLastNames = findViewById(R.id.etApellidos);
        etNames = findViewById(R.id.etNombres);
        etPhone = findViewById(R.id.etTelefono);
        etDni = findViewById(R.id.etDNI);
        etPassword = findViewById(R.id.etContraseña);
        etPassword2 = findViewById(R.id.etContraseña2);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegistrar:
                checkRegistro();
                //Toast.makeText(getApplicationContext(), "Login satisfactorio",Toast.LENGTH_LONG).show();
                break;
            case R.id.etFechaNacimiento:
                Calendar c = Calendar.getInstance();
                DatePickerDialog dpd;
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "-" + (month + 1) + "-" + year;
                        etBirthday.setText(date);
                    }
                }, day, month, year);
                dpd.show();
                break;
        }
    }

    private void checkRegistro() {
        Patient patient = new Patient();
        String birthday, email, lastNames, names, phone, dni, password, password2;

        User userLoginDTO = new User();

        birthday = etBirthday.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        lastNames = etLastNames.getText().toString().trim();
        names = etNames.getText().toString().trim();
        phone = etPhone.getText().toString().trim();
        dni = etDni.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        password2 = etPassword2.getText().toString().trim();

        userLoginDTO.setDni(dni);
        userLoginDTO.setPassword(password);


        patient.setBirthday(birthday);
        patient.setDescription("Descripcion");
        patient.setEmail(email);

        patient.setLastNames(lastNames);
        patient.setNames(names);
        patient.setPhone(phone);


        patient.setUser(userLoginDTO);

        if(password.equals(password2)){
            Call<LoginResponse> call = RetrofitClient.getApiLogin().register(patient);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.body().getStatus() == 1){
                            String message = response.body().getMessage();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }, 1000);
                    } else{
                        String message = response.body().getMessage();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    String message = t.getMessage();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            });
        } else
        {
            Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
        }


    }
}
