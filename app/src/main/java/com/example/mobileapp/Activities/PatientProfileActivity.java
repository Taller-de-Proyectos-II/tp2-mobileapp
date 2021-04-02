package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp.Model.Patient;
import com.example.mobileapp.Model.User;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Responses.UserResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientProfileActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {

    String passedUser;
    EditText etNames, etPhone, etEmail;
    UserResponse fillUser = new UserResponse();
    Patient fillPatient = new Patient();
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

        etNames = findViewById(R.id.etNames);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);


        Intent intent = getIntent();

        if(intent.getExtras() != null){
            passedUser = intent.getStringExtra("DNI");
        }

        Call<UserResponse> userResponse = RetrofitClient.getApiUser().getUser(passedUser);

        userResponse.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.body().getStatus() == 1){
                    fillInformation(response.body());
                    String textNames = fillPatient.getNames() + " " +fillPatient.getLastNames();
                    String textPhone = fillPatient.getPhone();
                    String textEmail = fillPatient.getEmail();
                    Toast.makeText(getApplicationContext(), textNames, Toast.LENGTH_LONG).show();
                    etNames.setText(textNames, TextView.BufferType.EDITABLE);
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

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
