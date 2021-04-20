package com.example.mobileapp.Activities;

import android.content.Intent;
import android.os.Bundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.mobileapp.Model.User;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUser;
    EditText etPassword;
    TextView forgotPassword;
    String user, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonLogin = findViewById(R.id.btnLogin);
        buttonLogin.setOnClickListener(this);

        Button buttonRegister = findViewById(R.id.btnRegistrar);
        buttonRegister.setOnClickListener(this);


        forgotPassword = findViewById(R.id.tvForgotpassword);
        forgotPassword.setOnClickListener(this);

        etUser = findViewById(R.id.etDNI);
        etPassword = findViewById(R.id.etContraseña);
    }

    private void checkLogin() {

        User user = new User();
        user.setDni(etUser.getText().toString().trim());
        user.setPassword(etPassword.getText().toString().trim());

        if(etPassword.getText().toString().trim().length() > 0) {
            Call<LoginResponse> call = RetrofitClient.getApiLogin().login(user);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        if (response.body().getStatus() == 1) {
                            String dniUser = user.getDNI();
                            String passwordUser = user.getPassword();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(MainActivity.this, MenuActivity.class).putExtra("DNI", dniUser).putExtra("password", passwordUser);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }, 1000);
                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.e("TAG", t.getMessage());
                    t.printStackTrace();
                }
            });
        } else
        {
            Toast.makeText(getApplicationContext(), "Ingrese sus credenciales", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                checkLogin();
                break;
            case R.id.btnRegistrar:
                Intent ra = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(ra);
                break;
            case R.id.tvForgotpassword:
                Intent fp = new Intent(getApplicationContext(),ForgotPwActivity.class);
                startActivity(fp);
                break;
        }

    }
}
