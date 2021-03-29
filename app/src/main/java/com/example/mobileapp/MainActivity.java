package com.example.mobileapp;

import android.content.Intent;
import android.os.Bundle;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.example.mobileapp.Model.User;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.RetrofitClient;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.lang.reflect.Type;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUser;
    EditText etPassword;
    TextView aqui, forgotPassword;
    String user, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonLogin = findViewById(R.id.btnLogin);
        buttonLogin.setOnClickListener(this);

        Button buttonRegister = findViewById(R.id.btnRegistrar);
        buttonRegister.setOnClickListener(this);

        aqui = findViewById(R.id.tvAqui);
        aqui.setOnClickListener(this);

        forgotPassword = findViewById(R.id.tvForgotpassword);
        forgotPassword.setOnClickListener(this);

        etUser = findViewById(R.id.etDNI);
        etPassword = findViewById(R.id.etContraseña);

        /*btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(TextUtils.isEmpty(etUser.getText().toString())||TextUtils.isEmpty(etPassword.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Debe ingresar sus credenciales",Toast.LENGTH_LONG);
                }else {
                    checkLogin();
                }
            }
        });*/
    }

    private void checkLogin() {

        User user = new User();
        user.setUser(etUser.getText().toString().trim());
        user.setPassword(etPassword.getText().toString().trim());


        Call<LoginResponse> call = RetrofitClient.getApi().login(user);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if(response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Login satisfactorio",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("TAG",t.getMessage());
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                checkLogin();
                //Toast.makeText(getApplicationContext(), "Login satisfactorio",Toast.LENGTH_LONG).show();
                break;
            case R.id.btnRegistrar:
                Intent ra = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(ra);
                break;
            case R.id.tvForgotpassword:
                Intent fp = new Intent(getApplicationContext(),ForgotPwActivity.class);
                startActivity(fp);
                break;
            case R.id.tvAqui:
                Intent cp = new Intent(getApplicationContext(),ContactPsyActivity.class);
                startActivity(cp);
                break;
        }

    }
}
