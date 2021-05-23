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
import android.widget.Toast;

import com.example.mobileapp.Model.DTO.changePasswordDTO;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePwActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener{

    String passedUser;
    changePasswordDTO changePasswordDTO = new changePasswordDTO();
    EditText textOldPassword, textNewPassword, textConfirmNewPassword;
    Button btnConfirmChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showPopup(v);
            }
        });
        getSupportActionBar().setTitle("Cambiar su contraseña");

        textOldPassword = findViewById(R.id.etPassword);
        textNewPassword = findViewById(R.id.etNewPassword);
        textConfirmNewPassword = findViewById(R.id.etConfirmNewPassword);

        btnConfirmChange = findViewById(R.id.btnConfirmChange);
        btnConfirmChange.setOnClickListener(this);


        Intent intent = getIntent();
        if(intent.getExtras() != null){
            passedUser = intent.getStringExtra("DNI");

        }
    }

    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_main);
        popup.show();
    }

    @Override
    public void onClick(View v) {
    switch (v.getId())
    {
        case R.id.btnConfirmChange:
            changePassword();
            break;
    }
    }

    private void changePassword() {
        changePasswordDTO.setDni(passedUser);
        changePasswordDTO.setNewPassword(textNewPassword.getText().toString());
        changePasswordDTO.setPassword(textOldPassword.getText().toString());

        if(textNewPassword.getText().toString().equals(textConfirmNewPassword.getText().toString())){
            String newPassword = textNewPassword.getText().toString().trim();
            Call<LoginResponse> changePw = RetrofitClient.getApiUser().updatePassword(changePasswordDTO);

            changePw.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.body().getStatus() == 1){
                        Toast.makeText(getApplicationContext(), "Se cambió la contraseña satisfactoriamente", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run(){
                                Intent ppa = new Intent(getApplicationContext(),PatientProfileActivity.class).putExtra("DNI", passedUser).putExtra("password", newPassword);
                                startActivity(ppa);
                            }
                        }, 1000);
                    } else{
                        Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                }
            });
        } else{
            Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
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
}
