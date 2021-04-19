package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp.Model.Patient;
import com.example.mobileapp.Model.User;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.Responses.UserResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientProfileActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {

    String passedUser, passedPassword;
    EditText etNames, etPhone, etEmail;
    UserResponse fillUser = new UserResponse();
    Patient fillPatient = new Patient();
    User user = new User();
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showPopup(v);
            }
        });
        getSupportActionBar().setTitle("Mi Perfil");

        fillUser.setMessage("");
        fillUser.setStatus(0);
        fillUser.setPatient(fillPatient);

        etNames = findViewById(R.id.etGuardianNames);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);

        Button btnGuardian = findViewById(R.id.btnGuardian);
        btnGuardian.setOnClickListener(this);
        Button btnUpdatePatient = findViewById(R.id.btnUpdatePatient);
        btnUpdatePatient.setOnClickListener(this);
        Button btnRegisterGuardian = findViewById(R.id.btnRegisterGuardian);
        btnRegisterGuardian.setOnClickListener(this);


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
                    fillInformation(response.body());
                    String textNames = fillPatient.getNames();
                    String textLastNames = fillPatient.getLastNames();
                    String textNamesFull = textNames + " " + textLastNames;
                    String textPhone = fillPatient.getPhone();
                    String textEmail = fillPatient.getEmail();
                    etNames.setText(textNamesFull, TextView.BufferType.EDITABLE);
                    etPhone.setText(textPhone, TextView.BufferType.EDITABLE);
                    etEmail.setText(textEmail, TextView.BufferType.EDITABLE);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });

    }

    private void fillInformation(UserResponse userResponse) {
        fillUser.setMessage(userResponse.getMessage());
        fillUser.setStatus(userResponse.getStatus());
        fillPatient.setPhone(userResponse.getPatient().getPhone());
        fillPatient.setNames(userResponse.getPatient().getNames());
        fillPatient.setLastNames(userResponse.getPatient().getLastNames());
        fillPatient.setEmail(userResponse.getPatient().getEmail());
        fillPatient.setBirthday(userResponse.getPatient().getBirthday());
        fillPatient.setDescription(userResponse.getPatient().getDescription());
    }

    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_main);
        popup.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnGuardian:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent gpa = new Intent(getApplicationContext(), GuardianProfileActivity.class).putExtra("DNI", passedUser).
                                                                                                        putExtra("email", fillPatient.getEmail()).
                                                                                                        putExtra("phone", fillPatient.getPhone());
                        startActivity(gpa);
                    }
                }, 1000);
                break;
            case R.id.btnRegisterGuardian:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent gpa2 = new Intent(getApplicationContext(), GuardianAddActivity.class).putExtra("DNI", passedUser).
                                                                                                        putExtra("email", fillPatient.getEmail()).
                                                                                                        putExtra("password", passedPassword).
                                                                                                        putExtra("phone", fillPatient.getPhone());
                        startActivity(gpa2);
                    }
                }, 1000);
                break;
            case R.id.btnUpdatePatient:
                checkUpdate();
                break;
        }
    }

    private void checkUpdate() {
        user.setDni(passedUser);
        user.setPassword(passedPassword);

        String fullName = etNames.getText().toString();
        int idx = fullName.lastIndexOf(' ') - 1;
        if(idx == -1)
            throw new IllegalArgumentException("Solo hay un nombre: " + fullName);
        String firstName = fullName.substring(0, idx);
        String lastName = fullName.substring(idx+1);

        fillPatient.setPhone(etPhone.getText().toString());
        fillPatient.setNames(firstName);
        fillPatient.setLastNames(lastName);
        fillPatient.setEmail(etEmail.getText().toString());
        fillPatient.setUser(user);
        Call<LoginResponse> updatePatient = RetrofitClient.getApiUser().updatePatient(fillPatient);
        updatePatient.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.body().getStatus() == 1)
                {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {

            case R.id.itMediciones:
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

        }
        return false;
    }
}
